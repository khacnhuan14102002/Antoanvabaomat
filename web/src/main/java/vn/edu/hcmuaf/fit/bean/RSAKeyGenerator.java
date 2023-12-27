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
        String privateKeyString=  Base64.getEncoder().encodeToString(privateBytes);
        // Thêm log để kiểm tra giá trị của private key

        return privateKeyString;
    }
    public static void main(String[] args) {
        try {
            // Tạo đối tượng RSAKeyGenerator
            RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator();

            // Tạo cặp khóa
            rsaKeyGenerator.createKeys();

            // Lấy chuỗi Base64 của khóa riêng tư
            String privateKey = rsaKeyGenerator.getPrivateKey();

            // In ra console
            System.out.println("Private Key: " + privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // Xử lý exception nếu cần
        }
    }
}