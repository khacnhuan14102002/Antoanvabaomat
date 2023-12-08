package vn.edu.hcmuaf.fit.bean;
import java.security.*;
import java.util.Base64;

public class Key {
    public KeyPair createKey() {
    try {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null;
    }
}
    public static String convertPublicKeyToString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
    public static String convertPrivateKeyToString(PrivateKey privateKey) {
        byte[] publicKeyBytes =privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
}
