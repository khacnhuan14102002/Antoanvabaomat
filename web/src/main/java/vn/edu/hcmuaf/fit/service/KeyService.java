package vn.edu.hcmuaf.fit.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static void main(String[] args) {

    }

}
