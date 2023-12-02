package vn.edu.hcmuaf.fit.bean;

import java.security.*;
import java.util.Base64;

public class RSAKeyGenerator {

    private KeyPairGenerator keyGen;
    private KeyPair pair;

    public RSAKeyGenerator() throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(2048);
    }

    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
    }

    public String getPublicKey() {
        PublicKey publicKey = pair.getPublic();
        byte[] publicBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicBytes);
    }

    public String getPrivateKey() {
        PrivateKey privateKey = pair.getPrivate();
        byte[] privateBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateBytes);
    }

}