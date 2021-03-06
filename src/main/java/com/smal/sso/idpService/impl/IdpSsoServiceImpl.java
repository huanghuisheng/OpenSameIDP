package com.smal.sso.idpService.impl;

import com.smal.sso.idpDao.IdpResourceDAO;
import com.smal.sso.idpDao.IdpSsoDAO;
import com.smal.sso.idpDto.ArtifactDTO;
import com.smal.sso.idpDto.PermissionDTO;
import com.smal.sso.idpPojo.DsAssociationPermission;
import com.smal.sso.idpPojo.DsUser;
import com.smal.sso.idpService.IdpSsoService;
import com.smal.sso.idpWebAction.IdpSsoController;
import com.smal.sso.util.*;
import com.tone.waf.dao.DaoFactory;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import org.apache.xml.security.utils.EncryptionConstants;
import org.joda.time.DateTime;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPRedirectDeflateDecoder;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPSOAP11Decoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPSOAP11Encoder;
import org.opensaml.saml.saml2.core.*;
import org.opensaml.saml.saml2.encryption.Encrypter;
import org.opensaml.xmlsec.encryption.support.DataEncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.opensaml.xmlsec.encryption.support.KeyEncryptionParameters;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.Signer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdpSsoServiceImpl implements IdpSsoService {
    private static Logger logger = (Logger) LoggerFactory.getLogger(IdpSsoController.class);
    private static Map<String, ArtifactDTO> artifactList = new HashMap<String, ArtifactDTO>();

    private IdpResourceDAO idpResourceDAO = (IdpResourceDAO) DaoFactory.getDao(IdpResourceDAO.class);


    public void authnRequestResolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DsUser user = (DsUser) req.getSession().getAttribute(IDPConstants.SESSION_USER_OBJ);
        if (user == null || ("").equals(user)) {
            logger.info("AuthnRequest recieved");
            HTTPRedirectDeflateDecoder decoder = new HTTPRedirectDeflateDecoder();
            decoder.setHttpServletRequest(req);
            try {
                decoder.initialize();
            } catch (ComponentInitializationException e) {
                e.printStackTrace();
            }
            try {
                decoder.decode();
            } catch (MessageDecodingException e) {
                e.printStackTrace();
            }
            MessageContext context = decoder.getMessageContext();
            AuthnRequest authnRequest = (AuthnRequest) context.getMessage();
            req.getSession().setAttribute(IDPConstants.IDP_AUTHNREQUEST, authnRequest);
            resp.sendRedirect(IDPConstants.IDP_LOGIN_URL);
        } else {
            AuthnRequest authnRequest = (AuthnRequest) req.getSession().getAttribute(IDPConstants.IDP_AUTHNREQUEST);
            String consumerServiceURL = authnRequest.getAssertionConsumerServiceURL();
            String artifactId = Base64.encode(UUIDFactory.INSTANCE.getUUID());
            Map<String, Object> map = new HashMap<String, Object>();
            Issuer issuer = authnRequest.getIssuer();
            //资源系统id
            String spIssuerId = issuer.getValue();
            ArtifactDTO artifactDTO = new ArtifactDTO();
            artifactDTO.setUserId(user.getId());
            artifactDTO.setSpIssuerId(Integer.valueOf(spIssuerId));
            artifactDTO.setName(user.getLoginName());
            artifactList.put(artifactId, artifactDTO);
            resp.sendRedirect(consumerServiceURL + "?SAMLart=" + artifactId);

        }
    }


    public void artifactResolve(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        logger.debug("recieved artifactResolve:");
        HTTPSOAP11Decoder decoder = new HTTPSOAP11Decoder();
        decoder.setHttpServletRequest(req);
        try {
            BasicParserPool parserPool = new BasicParserPool();
            parserPool.initialize();
            decoder.setParserPool(parserPool);
            decoder.initialize();
            decoder.decode();
        } catch (MessageDecodingException e) {
            throw new RuntimeException(e);
        } catch (ComponentInitializationException e) {
            throw new RuntimeException(e);
        }
        OpenSAMLUtils.logSAMLObject(decoder.getMessageContext().getMessage());

        ArtifactResolve artifactResolve = (ArtifactResolve) decoder.getMessageContext().getMessage();
        Artifact artifact = artifactResolve.getArtifact();

//        String artifactId = Base64.decode(artifact.getArtifact());
        String artifactId = artifact.getArtifact();
        ArtifactDTO artifactDTO = artifactList.get(artifactId);

        if (artifactDTO != null) {
            List<DsAssociationPermission> permissionList = idpResourceDAO.getDsAssociationPermission(artifactDTO);
            //构建
            ArtifactResponse artifactResponse = buildArtifactResponse(permissionList, artifactDTO);
            MessageContext<SAMLObject> context = new MessageContext<SAMLObject>();
            context.setMessage(artifactResponse);
            OpenSAMLUtils.logSAMLObject(artifactResponse);
            HTTPSOAP11Encoder encoder = new HTTPSOAP11Encoder();
            encoder.setMessageContext(context);
            encoder.setHttpServletResponse(resp);
            try {
                encoder.prepareContext();
                encoder.initialize();
                encoder.encode();
            } catch (MessageEncodingException e) {
                throw new RuntimeException(e);
            } catch (ComponentInitializationException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info(" artifactDTO is null ");
        }
    }



    public String getAssertionsResolve(DsUser user, PermissionDTO permissionDTO) {

        Response response = OpenSAMLUtils.buildSAMLObject(Response.class);
        Issuer issuer = OpenSAMLUtils.buildSAMLObject(Issuer.class);
        issuer.setValue(IDPConstants.IDP_ENTITY_ID);
        response.setIssuer(issuer);
        response.setIssueInstant(new DateTime());
        response.setDestination(SPConstants.ASSERTION_CONSUMER_SERVICE);
        response.setID(OpenSAMLUtils.generateSecureRandomId());
        Status status = OpenSAMLUtils.buildSAMLObject(Status.class);
        StatusCode statusCode = OpenSAMLUtils.buildSAMLObject(StatusCode.class);
        statusCode.setValue(StatusCode.SUCCESS);
        status.setStatusCode(statusCode);
        response.setStatus(status);
        Assertion assertion = buildAssertion(user, permissionDTO);
        signAssertion(assertion);
        EncryptedAssertion encryptedAssertion = encryptAssertion(assertion);
        response.getEncryptedAssertions().add(encryptedAssertion);
        response.getAssertions().add(assertion);
        return OpenSAMLUtils.buildXMLObjectToString(response);
    }




    private Assertion buildAssertion(DsUser user, PermissionDTO permissionDTO) {
        Assertion assertion = OpenSAMLUtils.buildSAMLObject(Assertion.class);
        Issuer issuer = OpenSAMLUtils.buildSAMLObject(Issuer.class);
        issuer.setValue(IDPConstants.IDP_ENTITY_ID);
        assertion.setIssuer(issuer);
        assertion.setIssueInstant(new DateTime());
        assertion.setID(OpenSAMLUtils.generateSecureRandomId());
        Subject subject = OpenSAMLUtils.buildSAMLObject(Subject.class);
        assertion.setSubject(subject);

        NameID nameID = OpenSAMLUtils.buildSAMLObject(NameID.class);
        nameID.setFormat(NameIDType.TRANSIENT);
        nameID.setValue("Some NameID value");
        nameID.setSPNameQualifier("SP name qualifier");
        nameID.setNameQualifier("Name qualifier");

        subject.setNameID(nameID);

        subject.getSubjectConfirmations().add(buildSubjectConfirmation());

        assertion.setConditions(buildConditions());

        assertion.getAttributeStatements().add(buildAttributeStatement(user, permissionDTO));

        assertion.getAuthnStatements().add(buildAuthnStatement());

        assertion.getAuthzDecisionStatements().add(buildAuthzDecisionStatement());

        return assertion;
    }


    private ArtifactResponse buildArtifactResponse(List<DsAssociationPermission> permissionList, ArtifactDTO artifactDTO) {
        ArtifactResponse artifactResponse = OpenSAMLUtils.buildSAMLObject(ArtifactResponse.class);
        Issuer issuer = OpenSAMLUtils.buildSAMLObject(Issuer.class);
        issuer.setValue(IDPConstants.IDP_ENTITY_ID);
        artifactResponse.setIssuer(issuer);
        artifactResponse.setIssueInstant(new DateTime());

        artifactResponse.setDestination(SPConstants.ASSERTION_CONSUMER_SERVICE);
        artifactResponse.setID(OpenSAMLUtils.generateSecureRandomId());

        Status status = OpenSAMLUtils.buildSAMLObject(Status.class);
        StatusCode statusCode = OpenSAMLUtils.buildSAMLObject(StatusCode.class);
        statusCode.setValue(StatusCode.SUCCESS);
        status.setStatusCode(statusCode);
        artifactResponse.setStatus(status);


        Response response = OpenSAMLUtils.buildSAMLObject(Response.class);
        response.setDestination(SPConstants.ASSERTION_CONSUMER_SERVICE);
        response.setIssueInstant(new DateTime());
        response.setID(OpenSAMLUtils.generateSecureRandomId());
        Issuer issuer2 = OpenSAMLUtils.buildSAMLObject(Issuer.class);
        issuer2.setValue(IDPConstants.IDP_ENTITY_ID);
        response.setIssuer(issuer2);

        Status status2 = OpenSAMLUtils.buildSAMLObject(Status.class);
        StatusCode statusCode2 = OpenSAMLUtils.buildSAMLObject(StatusCode.class);
        statusCode2.setValue(StatusCode.SUCCESS);
        status2.setStatusCode(statusCode2);
        response.setStatus(status2);
        artifactResponse.setMessage(response);

        Assertion assertion = buildAssertion(permissionList, artifactDTO);

        signAssertion(assertion);
        EncryptedAssertion encryptedAssertion = encryptAssertion(assertion);

        response.getEncryptedAssertions().add(encryptedAssertion);
        return artifactResponse;
    }


    private Assertion buildAssertion(List<DsAssociationPermission> permissionList, ArtifactDTO artifactDTO) {
        Assertion assertion = OpenSAMLUtils.buildSAMLObject(Assertion.class);
        //IDP的唯一标识符
        Issuer issuer = OpenSAMLUtils.buildSAMLObject(Issuer.class);
        issuer.setValue(IDPConstants.IDP_ENTITY_ID);
        assertion.setIssuer(issuer);
        assertion.setIssueInstant(new DateTime());
        assertion.setID(OpenSAMLUtils.generateSecureRandomId());

        Subject subject = OpenSAMLUtils.buildSAMLObject(Subject.class);
        assertion.setSubject(subject);

        //用户主题标识id
        NameID nameID = OpenSAMLUtils.buildSAMLObject(NameID.class);
        nameID.setFormat(NameIDType.TRANSIENT);
        nameID.setValue("Some NameID value");
        subject.setNameID(nameID);

        //主题被确认信息
        subject.getSubjectConfirmations().add(buildSubjectConfirmation());


        //断言被认为有效的条件
        assertion.setConditions(buildConditions());

        //描述IDP如何身份验证的行为
        assertion.getAuthnStatements().add(buildAuthnStatement());

        //授权决策
        assertion.getAuthzDecisionStatements().add(buildAuthzDecisionStatement());

        //主题的特定标识属性
        assertion.getAttributeStatements().add(buildAttributeStatement(permissionList, artifactDTO));


        return assertion;
    }


    /**
     * 加密断言
     */
    private EncryptedAssertion encryptAssertion(Assertion assertion) {
        DataEncryptionParameters encryptionParameters = new DataEncryptionParameters();
        encryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);

        KeyEncryptionParameters keyEncryptionParameters = new KeyEncryptionParameters();
        keyEncryptionParameters.setEncryptionCredential(SPCredentials.getCredential());
        keyEncryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSAOAEP);

        Encrypter encrypter = new Encrypter(encryptionParameters, keyEncryptionParameters);
        encrypter.setKeyPlacement(Encrypter.KeyPlacement.INLINE);

        try {
            return encrypter.encrypt(assertion);
        } catch (EncryptionException e) {
            throw new RuntimeException(e);
        }
    }

    //数字签名断言
    private void signAssertion(Assertion assertion) {
        Signature signature = OpenSAMLUtils.buildSAMLObject(Signature.class);
        signature.setSigningCredential(IDPCredentials.getCredential());
        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        assertion.setSignature(signature);
        try {
            //noinspection ConstantConditions =》marshall 要求输入Nonnull且输出为Nonull；
            XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(assertion).marshall(assertion);
        } catch (MarshallingException e) {
            throw new RuntimeException(e);
        }
        try {
            Signer.signObject(signature);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }


    private SubjectConfirmation buildSubjectConfirmation() {
        SubjectConfirmation subjectConfirmation = OpenSAMLUtils.buildSAMLObject(SubjectConfirmation.class);
        subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);

        SubjectConfirmationData subjectConfirmationData = OpenSAMLUtils.buildSAMLObject(SubjectConfirmationData.class);
        subjectConfirmationData.setInResponseTo("Made up ID");
        subjectConfirmationData.setNotBefore(new DateTime().minusDays(2));
        subjectConfirmationData.setNotOnOrAfter(new DateTime().plusDays(2));
        subjectConfirmationData.setRecipient(SPConstants.ASSERTION_CONSUMER_SERVICE);
        subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);
        return subjectConfirmation;
    }

    private AuthnStatement buildAuthnStatement() {
        AuthnStatement authnStatement = OpenSAMLUtils.buildSAMLObject(AuthnStatement.class);
        AuthnContext authnContext = OpenSAMLUtils.buildSAMLObject(AuthnContext.class);
        AuthnContextClassRef authnContextClassRef = OpenSAMLUtils.buildSAMLObject(AuthnContextClassRef.class);
        authnContextClassRef.setAuthnContextClassRef(AuthnContext.SMARTCARD_AUTHN_CTX);
        authnContext.setAuthnContextClassRef(authnContextClassRef);
        authnStatement.setAuthnContext(authnContext);
        authnStatement.setAuthnInstant(new DateTime());
        return authnStatement;
    }

    private Conditions buildConditions() {
        Conditions conditions = OpenSAMLUtils.buildSAMLObject(Conditions.class);
        conditions.setNotBefore(new DateTime().minusDays(2));
        conditions.setNotOnOrAfter(new DateTime().plusDays(2));
        AudienceRestriction audienceRestriction = OpenSAMLUtils.buildSAMLObject(AudienceRestriction.class);
        Audience audience = OpenSAMLUtils.buildSAMLObject(Audience.class);
        audience.setAudienceURI(SPConstants.ASSERTION_CONSUMER_SERVICE);
        audienceRestriction.getAudiences().add(audience);
        conditions.getAudienceRestrictions().add(audienceRestriction);
        return conditions;
    }

    private AttributeStatement buildAttributeStatement(DsUser user, PermissionDTO permissionDTO) {
        AttributeStatement attributeStatement = OpenSAMLUtils.buildSAMLObject(AttributeStatement.class);
        Attribute attributeUserName = OpenSAMLUtils.buildSAMLObject(Attribute.class);
        XSStringBuilder stringBuilder = (XSStringBuilder) XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(XSString.TYPE_NAME);
        assert stringBuilder != null;

        XSString userNameValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        userNameValue.setValue(user.getLoginName());
        attributeUserName.getAttributeValues().add(userNameValue);
        attributeUserName.setName("username");

        XSString passwordValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        passwordValue.setValue(user.getPassword());
        attributeUserName.getAttributeValues().add(passwordValue);
        attributeUserName.setName("password");

        return attributeStatement;
    }

    private AuthzDecisionStatement buildAuthzDecisionStatement(PermissionDTO permissionDTO) {
        AuthzDecisionStatement authzDecisionStatement = OpenSAMLUtils.buildSAMLObject(AuthzDecisionStatement.class);
//        authzDecisionStatement.setResource("http://localhost:8070/hoffice/smsdoctor/referredreservations.do?method=enter&tagrandom=0.5669605692950603");
        authzDecisionStatement.setDecision(DecisionTypeEnumeration.PERMIT);
        authzDecisionStatement.setResource(permissionDTO.getUrl());
        return authzDecisionStatement;
    }


    private AttributeStatement buildAttributeStatement(List<DsAssociationPermission> permissionList, ArtifactDTO artifactDTO) {
        AttributeStatement attributeStatement = OpenSAMLUtils.buildSAMLObject(AttributeStatement.class);
        Attribute attributeUserName = OpenSAMLUtils.buildSAMLObject(Attribute.class);
        XSStringBuilder stringBuilder = (XSStringBuilder) XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(XSString.TYPE_NAME);
        assert stringBuilder != null;
        XSString userNameValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        userNameValue.setValue(artifactDTO.getName());
        attributeUserName.getAttributeValues().add(userNameValue);
        attributeUserName.setName("username");

        Attribute attributeLevel = OpenSAMLUtils.buildSAMLObject(Attribute.class);

        for (DsAssociationPermission dsAssociationPermission : permissionList) {
            XSString levelValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
            levelValue.setValue(dsAssociationPermission.getUrl());
            attributeLevel.getAttributeValues().add(levelValue);
        }
        attributeLevel.setName("spUrl");
        attributeStatement.getAttributes().add(attributeUserName);
        attributeStatement.getAttributes().add(attributeLevel);
        return attributeStatement;
    }

    private AuthzDecisionStatement buildAuthzDecisionStatement() {
        AuthzDecisionStatement authzDecisionStatement = OpenSAMLUtils.buildSAMLObject(AuthzDecisionStatement.class);
        authzDecisionStatement.setResource("http://localhost:8090/hoffice/smsdoctor/referredreservations.do?method=enter&tagrandom=0.5669605692950603");
        authzDecisionStatement.setDecision(DecisionTypeEnumeration.PERMIT);

        return authzDecisionStatement;
    }


}