package com.smal.sso.test;

import com.smal.sso.util.OpenSAMLUtils;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.saml.saml2.core.Response;

public class ss {


    public static void main(String[] args) {
        try {
            InitializationService.initialize();
        } catch (InitializationException e) {
            e.printStackTrace();
        }
//        AuthnRequest authnRequest = OpenSAMLUtils.buildSAMLObject(AuthnRequest.class);
//        //请求时间：该对象创建的时间，以判断其时效性
//        authnRequest.setIssueInstant(new DateTime());
//        //目标URL：目标地址，IDP地址
//        authnRequest.setDestination("dkjfkdjfkdjfkj");
//        //传输SAML断言所需要的绑定：也就是用何种协议使用Artifact来取回真正的认证信息，
//        authnRequest.setProtocolBinding(SAMLConstants.SAML2_ARTIFACT_BINDING_URI);
//        //SP地址： 也就是SAML断言返回的地址
//        authnRequest.setAssertionConsumerServiceURL("skdfjkjsdflsdjf");
//        //请求的ID：为当前请求设置ID，一般为随机数
//        authnRequest.setID(OpenSAMLUtils.generateSecureRandomId());
//        //Issuer： 发行人信息，也就是SP的ID，一般是SP的URL
//        Issuer issuer = OpenSAMLUtils.buildSAMLObject(Issuer.class);
//        issuer.setValue("kdjfkldsjfkldjfklsdjfkdjf");
//        authnRequest.setIssuer(issuer);
//        //NameID：IDP对于用户身份的标识；NameID policy是SP关于NameID是如何创建的说明
//        NameIDPolicy nameIDPolicy = OpenSAMLUtils.buildSAMLObject(NameIDPolicy.class);
//        nameIDPolicy.setAllowCreate(true);
//        nameIDPolicy.setFormat(NameIDType.TRANSIENT);
//        authnRequest.setNameIDPolicy(nameIDPolicy);
//
//        String authnRequeststr=   OpenSAMLUtils.buildXMLObjectToString(authnRequest);
//        System.out.println("------------"+authnRequeststr);
//
//
//
//
//        AuthnRequest authnRequest1 =(AuthnRequest)OpenSAMLUtils.buildStringToXMLObject(authnRequeststr);
//        System.out.println("------------"+authnRequest1.getDestination());

//        Response response = OpenSAMLUtils.buildSAMLObject(Response.class);
//        response.setDestination(SPConstants.ASSERTION_CONSUMER_SERVICE);
//        response.setIssueInstant(new DateTime());
//        response.setID(OpenSAMLUtils.generateSecureRandomId());
//        Issuer issuer2 = OpenSAMLUtils.buildSAMLObject(Issuer.class);
//        issuer2.setValue(IDPConstants.IDP_ENTITY_ID);
//        response.setIssuer(issuer2);
//
//        Status status2 = OpenSAMLUtils.buildSAMLObject(Status.class);
//        StatusCode statusCode2 = OpenSAMLUtils.buildSAMLObject(StatusCode.class);
//        statusCode2.setValue(StatusCode.SUCCESS);
//        status2.setStatusCode(statusCode2);
//        response.setStatus(status2);
//
//
//
//
//        Assertion assertion = OpenSAMLUtils.buildSAMLObject(Assertion.class);
//        Issuer issuer = OpenSAMLUtils.buildSAMLObject(Issuer.class);
//        issuer.setValue(IDPConstants.IDP_ENTITY_ID);
//        assertion.setIssuer(issuer);
//        assertion.setIssueInstant(new DateTime());
//        assertion.setID(OpenSAMLUtils.generateSecureRandomId());
//        Subject subject = OpenSAMLUtils.buildSAMLObject(Subject.class);
//        assertion.setSubject(subject);
//
//        NameID nameID = OpenSAMLUtils.buildSAMLObject(NameID.class);
//        nameID.setFormat(NameIDType.TRANSIENT);
//        nameID.setValue("Some NameID value");
//        nameID.setSPNameQualifier("SP name qualifier");
//        nameID.setNameQualifier("Name qualifier");
//
//        subject.setNameID(nameID);
//
//        Signature signature = OpenSAMLUtils.buildSAMLObject(Signature.class);
//        signature.setSigningCredential(IDPCredentials.getCredential());
//        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
//        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
//        assertion.setSignature(signature);
//
//        try {
//            //noinspection ConstantConditions =》marshall 要求输入Nonnull且输出为Nonull；
//            XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(assertion).marshall(assertion);
//        } catch (MarshallingException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            Signer.signObject(signature);
//        } catch (SignatureException e) {
//            throw new RuntimeException(e);
//        }
//
//        DataEncryptionParameters encryptionParameters = new DataEncryptionParameters();
//        encryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_BLOCKCIPHER_AES128);
//
//        KeyEncryptionParameters keyEncryptionParameters = new KeyEncryptionParameters();
//        keyEncryptionParameters.setEncryptionCredential(SPCredentials.getCredential());
//        keyEncryptionParameters.setAlgorithm(EncryptionConstants.ALGO_ID_KEYTRANSPORT_RSAOAEP);
//
//        Encrypter encrypter = new Encrypter(encryptionParameters, keyEncryptionParameters);
//        encrypter.setKeyPlacement(Encrypter.KeyPlacement.INLINE);
//
//
//        EncryptedAssertion encryptedAssertion = null;
//        try {
//            encryptedAssertion = encrypter.encrypt(assertion);
//        } catch (EncryptionException e) {
//            e.printStackTrace();
//        }
//
//        response.getEncryptedAssertions().add(encryptedAssertion);


//        String authnRequeststr=   OpenSAMLUtils.buildXMLObjectToString(response);
//        System.out.println("------------"+authnRequeststr);

        String authnRequeststr="<?xml version=\"1.0\" encoding=\"UTF-8\"?><saml2p:Response xmlns:saml2p=\"urn:oasis:names:tc:SAML:2.0:protocol\" Destination=\"http://localhost:8070/hoffice/consumerService\" ID=\"_5dff4f8360b127a849a81b722aa5343c\" IssueInstant=\"2018-02-09T07:22:41.420Z\" Version=\"2.0\">\n" +
                "<saml2:Issuer xmlns:saml2=\"urn:oasis:names:tc:SAML:2.0:assertion\">samlIDP</saml2:Issuer>\n" +
                "<saml2p:Status>\n" +
                "<saml2p:StatusCode Value=\"urn:oasis:names:tc:SAML:2.0:status:Success\"/>\n" +
                "</saml2p:Status>\n" +
                "<saml2:EncryptedAssertion xmlns:saml2=\"urn:oasis:names:tc:SAML:2.0:assertion\">\n" +
                "<xenc:EncryptedData xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\" Id=\"_51420c286b5f80487265eaea24207085\" Type=\"http://www.w3.org/2001/04/xmlenc#Element\">\n" +
                "<xenc:EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#aes128-cbc\"/>\n" +
                "<ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "<xenc:EncryptedKey Id=\"_a23502460e8d62a4f47b0051cd17d125\">\n" +
                "<xenc:EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p\">\n" +
                "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>\n" +
                "</xenc:EncryptionMethod>\n" +
                "<xenc:CipherData>\n" +
                "<xenc:CipherValue>IJR83DEqEBQXHe5vqqomrE74tF7gZ7LH1raaiCxBiFsrE7U2gei/0udBmWvyPo9dlw7k1p7WtobR\n" +
                "3kQjyyfM7yImUg8ONuSybjWA0k803jaf14Lqk5Pu7qLnp6D3GYFnYox+mgzylm8pZiKrHM7NdZw+\n" +
                "dJtS5FH7TvEDEsA+HqCKpERVUVlrYecVhDkedc++yHqQUXFtIcN1iO1KY8Xw09vgaMF+oi0u+VML\n" +
                "A6lXGMZPz+2sL5DiJFfkKRl/BqVGDRbNjUG9IB8bGA/uuqtV/FtlIMIXKkusbihFaXa459tmnr4U\n" +
                "WiyHuSA6qZ6d/pwP6Ie03QKYOtMm91P90LM8Eg==</xenc:CipherValue>\n" +
                "</xenc:CipherData>\n" +
                "</xenc:EncryptedKey>\n" +
                "</ds:KeyInfo>\n" +
                "<xenc:CipherData>\n" +
                "<xenc:CipherValue>TYgURKJPLblG8idzmv6quUHLtFh1znpDdzkFU9mabm+XlQ4v3S4vXgugti/aZAIHrxxhQsRbgLVR\n" +
                "+Qn2J2QGcY7ynhaAhGLNew8zzhMGH8aIroErXnQknIE61X0JIOqpH9t/++MbMM51kg/PxcJTPuCm\n" +
                "bK/+qJCnrWAPYyRBxx7UHVzgGOtyQ/gb1pZPXfuUFTMRu7VI867Bwiy1dCMRb9g6ZX9jDjOemYME\n" +
                "SsC79a8QpbKs2cgNFXvwRnjjbgwNTuIwHG1/fRLDynzN/hQaJgqgm0PtS/+qUZbd1YK3/eqWMyMY\n" +
                "JyI3EZfFLdTLjxp2bCo/IKy4mvmCUxbfoWnWyjn6ZmPA4G6vhFbZxmZMkJ0rHHxaLpAkp+VdCtsM\n" +
                "5U68MHqkU8nH5mHhldFdjfhxNkj9rGKAOX6Ug6AjYfuMLLlav8Sl+YDunFhKi2gUiyzSm2hjH6/H\n" +
                "HcbRE1k9PSKQMLNm3fl/Ey+wDnUZdP/fvokZxBynkZmPawfxl+c9v5g3tU3U8+vqGAADVN3RUJJO\n" +
                "v9eJ5xWLKPp7pNHdEsONuSBtDUo1bTmZf3vcpYDJp13NTMahJ2QP6uJ8nOlU9PYEaIZRYpgc6IIG\n" +
                "J4VClbucWNroXgcbhDUuMEwCrZYKOkikd2cMgS+fGmJzEPKeNLX/bSjYKhsaLNc0sY5c148zO9SI\n" +
                "YhHWzjMAUuxv28NXwTppSEMH4ZXnhdQQnZEbU1L70ESfkowG9X6mlya8XNiFw/tWfFWEhx5yzeDy\n" +
                "QgZsQRyQwqw/LFKrN7AcWnIGKrMczqDZcF+xI08RiQ7ANhvbmgdW1BC+7Fh54Uqs5unGzFaTFwRR\n" +
                "7IJIg0xtUSL76ockSwqvd0TaDPQvbvLOAK7hStcKe41ZYpfWKPEOZ03zgOz592w+/D3FQMcddMY8\n" +
                "oXPK1m9tE6c4BJleyKXxFcjEYUlRWedS9pmtt1PP1o9o04sxr15AdZ74gT/gYnoLh7HvZbV0lB+k\n" +
                "rGQwKnJ0V5Amt7OhqeiilMIOtDTlJ7YCe/vR3E+YRof7WXpJYR5GWe5WHTws6TD/cEvhaGJeLUDA\n" +
                "F/HPcDqA3UpXyhFP2a+PlPFO53+lQG6wzDkV8VqM1sOnW5ctOhNvcavgraqC4esW+7l9/RZGn6bT\n" +
                "8BrQG36l/mMv4lT86MnEiXIQkBXH0gffpZ7kxO0W94ixPY2PuTSVePXpsEhNKlMaZlGxlRHaTjXd\n" +
                "ixek6/cDuYh39R4Bkq69u0ZoQ16HA6KKYOUpdUCwr1+H36/pschDIb5rKgudrkMVzZr21peniy5R\n" +
                "oRrZCCLJoyD7+u0pXh4goH2jbD1MrkPfv3e0jic1X3cuKZr+LwssbDk7FY60qiWw9w0rQ7b+TSzj\n" +
                "Z25Px3YXRIZwLQUbU2Vf6wyoq/pkz1HXeL22NXnODu6k1qvDdFS16FosUul/V6OXbI5g1KpQ2p86\n" +
                "M8zom4EMmha4Lv061fcQnD+A2FYCHW8FZRKp6QDKpWZF1V8zS9Fn+2EX2rrQ3WiMhhpL7pt0p11T\n" +
                "gn2Jgbpx2u5N6mNfojRAq8TXxtLkgY55BR96LvgZw7t4WZVBwOxrB8IMGhJbp31E69hyPSKiPvQ/\n" +
                "e9P6QDaNXCkGc/MrLWVSEMCMts5r5rq/6llphQO1spGEBe5zz/jeh0IdzOLvQ+cE7up2EWHd9fR0\n" +
                "rrNJRJZ8EnZ96nV1fdjKh5sGGDXnDxLgM+O0q7HHZ5mjOTlRdAEtwjSFZCVW/Am9gh6zTtyrjNyE\n" +
                "VFvEDqGNwu2vM91uDtiFqQOvZAax4GMsOwB79EsEaw+UVZAnD21XG+ZvNhg2YtH8tIg8BeE4Vra1\n" +
                "2YVMohC/ivsFMiERMSsp2uvlQGXq2z9ORrdIp+/4QwTfyIsjWsAeJac3q7PMkUfq/9n6YTaUqC8=</xenc:CipherValue>\n" +
                "</xenc:CipherData>\n" +
                "</xenc:EncryptedData>\n" +
                "</saml2:EncryptedAssertion>\n" +
                "</saml2p:Response>";
        Response authnRequest1 =(Response) OpenSAMLUtils.buildStringToXMLObject(authnRequeststr);
        System.out.println("------------"+authnRequest1.getID());

    }
}
