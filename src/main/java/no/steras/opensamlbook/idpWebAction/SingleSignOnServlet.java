package no.steras.opensamlbook.idpWebAction;

import com.tone.dao.ServiceHelper;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import no.steras.opensamlbook.idpService.SingleSignOnService;
import no.steras.opensamlbook.util.UUIDFactory;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPRedirectDeflateDecoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPRedirectDeflateEncoder;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SingleSignOnServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(SingleSignOnServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("AuthnRequest recieved");
        HTTPRedirectDeflateDecoder decoder = new HTTPRedirectDeflateDecoder();
        decoder.setHttpServletRequest(req);
        HTTPRedirectDeflateEncoder encoder =new HTTPRedirectDeflateEncoder();
        
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

        MessageContext context  =  decoder.getMessageContext();
        AuthnRequest  authnRequest = (AuthnRequest) context.getMessage();
        String destination  =  authnRequest.getDestination();
        System.out.println("-----目标地址--------"+destination);
        String consumerServiceURL=authnRequest.getAssertionConsumerServiceURL();
        System.out.println("-----sp地址--------"+consumerServiceURL);
        Issuer issuer= authnRequest.getIssuer();
        String spId=issuer.getValue();
        System.out.println("-----服务ID------"+spId);


        HttpSession session= req.getSession();
        System.out.println("----sessionId----"+session.getId());

        String consent= authnRequest.getConsent();
        SingleSignOnService service = (SingleSignOnService) ServiceHelper.getService(SingleSignOnService.class);
        List list= service.getUserAssociation(consent);
         if(list!=null&&list.size()>0)
         {
             String artifactId = UUIDFactory.INSTANCE.getUUID();
             resp.sendRedirect(consumerServiceURL + "?SAMLart="+artifactId);
         }else{
             resp.sendRedirect(consumerServiceURL);
         }
//        AuthnRequest authnRequest = OpenSAMLUtils.buildSAMLObject(AuthnRequest.class);
//        //请求时间：该对象创建的时间，以判断其时效性
//        authnRequest.setIssueInstant(new DateTime());
//        //目标URL：目标地址，IDP地址
//        authnRequest.setDestination(getIPDSSODestination());
//        //传输SAML断言所需要的绑定：也就是用何种协议使用Artifact来取回真正的认证信息，
//        authnRequest.setProtocolBinding(SAMLConstants.SAML2_ARTIFACT_BINDING_URI);
//        //SP地址： 也就是SAML断言返回的地址
//        authnRequest.setAssertionConsumerServiceURL(getAssertionConsumerEndpoint());
//        //请求的ID：为当前请求设置ID，一般为随机数
//        authnRequest.setID(OpenSAMLUtils.generateSecureRandomId());
//        //Issuer： 发行人信息，也就是SP的ID，一般是SP的URL
//        authnRequest.setIssuer(buildIssuer());
//        //NameID：IDP对于用户身份的标识；NameID policy是SP关于NameID是如何创建的说明
//        authnRequest.setNameIDPolicy(buildNameIdPolicy());
//        // 请求认证上下文（requested Authentication Context）:
//        // SP对于认证的要求，包含SP希望IDP如何验证用户，也就是IDP要依据什么来验证用户身份。
//        authnRequest.setRequestedAuthnContext(buildRequestedAuthnContext());

//        String artifactId = UUIDFactory.INSTANCE.getUUID();
//        String samlRequest = GZipUtil.gzip(artifactId);
//        String artifactBase64=Base64.encodeBytes(samlRequest.getBytes(), Base64.DONT_BREAK_LINES);
//        resp.sendRedirect(consumerServiceURL + "?SAMLart=AAQAAMFbLinlXaCM%2BFIxiDwGOLAy2T71gbpO7ZhNzAgEANlB90ECfpNEVLg%3D");
    }
}
