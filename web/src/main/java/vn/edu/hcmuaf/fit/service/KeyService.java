package vn.edu.hcmuaf.fit.service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.bean.category;
import vn.edu.hcmuaf.fit.db.connect;

public class KeyService {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static void addKey(int idu, String pub, String pri) {
        String query = "INSERT INTO  userkeys(UserId, PublicKey, PrivateKey)\n" +
                "VALUES (?,?,?);";
        try {
            conn = new connect().getconConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idu);
            ps.setString(2, pub);
            ps.setString(3, pri);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("fail");
        }
    }

    public static void reportKey(String pubkey, Timestamp time) {
        String query = "UPDATE userkeys set ReportDate= ? where PublicKey=?";
        try {
            conn = new connect().getconConnection();
            ps = conn.prepareStatement(query);
            ps.setTimestamp(1, time);
            ps.setString(2, pubkey);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("fail");
        }
    }


    public static void main(String[] args) {
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        KeyService kyes = new KeyService();
        kyes.reportKey("asdaxacscascaczx", time);
        KeyService keyService = new KeyService();

        // Tạo key pair
        KeyPair keyPair = new Key().createKey();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Convert key pair to strings
        String publicKeyString = Key.convertPublicKeyToString(publicKey);
        String privateKeyString = Key.convertPrivateKeyToString(privateKey);

        // Thêm key vào cơ sở dữ liệu
        addKey(29, publicKeyString, privateKeyString);

        // In thông tin key
        System.out.println("Public Key: " + publicKeyString);
        System.out.println("Private Key: " + privateKeyString);
    }

}
