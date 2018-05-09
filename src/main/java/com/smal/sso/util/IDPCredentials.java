package com.smal.sso.util;

import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.crypto.KeySupport;
import org.springframework.util.Base64Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class IDPCredentials {
    private static Credential credential;

    static {
        try {
            credential = generateCredential();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Credential generateCredential() throws Exception {
        try {
            KeyPair keyPair = KeySupport.generateKeyPair("RSA", 1024, null);
            String clientPrivateKey = "C:\\Program Files (x86)\\Java\\jdk1.8.0_91\\bin\\acosta.keystore";
            String clientPublicKey = "C:\\Program Files (x86)\\Java\\jdk1.8.0_91\\bin\\acosta.cer";
            String clientAlias = "acosta";
            String clientPassword = "password";
            //1- 客户端公钥
            RSAPublicKey cltPubKey = (RSAPublicKey) IDPCredentials.loadPublicKeyByFile(clientPublicKey);
            //2- 客户端私钥
            RSAPrivateKey cltPriKey = (RSAPrivateKey) IDPCredentials.loadPrivateKeyByFile(clientPrivateKey, clientAlias, clientPassword);
            System.out.println("\nclientPublicString:\n" + Base64Utils.encode(cltPubKey.getEncoded()) + "\n");
            System.out.println("\nclientPrivateString:\n" + Base64Utils.encode(cltPriKey.getEncoded()) + "\n");

            return CredentialSupport.getSimpleCredential(cltPubKey, cltPriKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Credential getCredential() {
        return credential;
    }


    public static PublicKey loadPublicKeyByFile(String filepath) throws Exception {
        try {
            //通过证书,获取公钥
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate c = cf.generateCertificate(new FileInputStream(filepath));
            PublicKey publicKey = c.getPublicKey();
            return publicKey;
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    public static PrivateKey loadPrivateKeyByFile(String filepath, String alias, String password) throws Exception {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(filepath), password.toCharArray());

            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());
            return privateKey;
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }


}
