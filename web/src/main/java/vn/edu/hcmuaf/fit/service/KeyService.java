package vn.edu.hcmuaf.fit.service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.client.util.DateTime;
import org.apache.poi.util.SystemOutLogger;
import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.bean.category;
import vn.edu.hcmuaf.fit.db.connect;

public class KeyService {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static void addKey(Key key) {
        String query = "INSERT INTO  userkeys(UserId, PublicKey, ReportDate, DayCreate,isblock)\n" +
                "VALUES (?,?,?,?,?);";
        try {
            conn = new connect().getconConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, key.getIdU());
            ps.setString(2, key.getPubkey());
            ps.setTimestamp(3,key.getReportdate());
            ps.setTimestamp(4,key.getAddedDate());
            ps.setInt(5, 0);
            System.out.println("thành cong add key");
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("fail");
        }
    }
    public static String getPublic(int id) {
        String query = "select PublicKey from userkeys where UserId= ?";
        try {
            conn = new connect().getconConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String publicKey = rs.getString(1);
                System.out.println("Public Key from Database: " + publicKey);
                return publicKey;

            }
        } catch (Exception e) {
        }
        return null;
    }
    public static void reportKey(String pubkey, Timestamp time) {
        String query = "UPDATE userkeys set  ReportDate= ?,isblock=1  where PublicKey=?";
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

    public static void updateOldKey(String pubkey) {
        String query = "UPDATE userkeys set  isblock = 1  where PublicKey = ?";
        try {
            conn = new connect().getconConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, pubkey);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("fail");
        }
    }
    public List<Key> getListKeyByIdUser(int idUser) {
        List<Key> listKey = new ArrayList<>();
        String query = "SELECT UserId, PublicKey, ReportDate, DayCreate, isblock FROM userkeys WHERE UserId = ?";
        try {
            conn = new connect().getconConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idUser);
            rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("UserId");
                String publicKey = rs.getString("PublicKey");
                Timestamp reportDate = rs.getTimestamp("ReportDate");
                Timestamp addedDate = rs.getTimestamp("DayCreate");
                int isblock = rs.getInt("isblock");

                Key key = new Key(userId, publicKey, reportDate, addedDate, isblock);
                listKey.add(key);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("fail");
        }
        return listKey;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        Date date = new Date();
//        Timestamp time = new Timestamp(date.getTime());
////        KeyService kyes = new KeyService();
////        kyes.reportKey("asdaxacscascaczx", time);
        KeyService keyService = new KeyService();

        Key key = new Key(16, "hrruuurrrrrrrrrrrrrrrrrrrrrrrrr",null,null,0);


//        KeyPair keyPair = new RSAKeyGenerator().createKey();
//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//
//        // Convert key pair to strings
//        String publicKeyString = RSAKeyGenerator.convertPublicKeyToString(publicKey);
//        String privateKeyString = RSAKeyGenerator.convertPrivateKeyToString(privateKey);
//
//      Key keys = new Key(3,publicKeyString,null,time);
//        addKey(keys);

//        // In thông tin key
//        System.out.println("Public Key: " + publicKeyString);
//        System.out.println("Private Key: " + privateKeyString);
        System.out.println(getPublic(16));
        List<Key> keys= new ArrayList<>();
        keys = keyService.getListKeyByIdUser(16);
        for (Key k: keys) {
            keyService.updateOldKey(k.getPubkey());
            System.out.println(k.getPubkey());

        }

        keyService.addKey(key);
    }

}
