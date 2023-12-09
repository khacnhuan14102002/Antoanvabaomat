package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.bean.Invoice;
import vn.edu.hcmuaf.fit.bean.products;
import vn.edu.hcmuaf.fit.db.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    static Connection conn = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;
    public static List<Invoice> getAllInvoice() {
        List<Invoice> list = new ArrayList<>();
        String query = "select * from invoices";
        try {
            conn = new connect().getconConnection(); //mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Invoice in_voice = new Invoice(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getTimestamp(7),
                        rs.getString(8),
                        rs.getInt(9));
                list.add(in_voice);
            }
        } catch (Exception e) {
            System.out.println("fail");
        }
        return list;
    }
    public static Invoice getIn(int idU){
        Invoice in = new Invoice();
        String query = "SELECT * \n" +
                "FROM invoices\n" +
                "WHERE idUs ='" +idU+ "' ORDER BY Exportdate DESC\n" +
                "LIMIT 1";
        try {
            conn = new connect().getconConnection(); //mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                in.setIdIn(rs.getInt(1));
                        in.setNameuser(rs.getString(2));
                       in.setAddress(rs.getString(3));
                        in.setType(rs.getString(4));
                        in.setStatusIn(rs.getString(5));
                        in.setTotal(rs.getDouble(6));
                        in.setDatecreate(rs.getTimestamp(7));
                        in.setPhone(rs.getString(8));
                        in.setIdUs(rs.getInt(9));

            }
        } catch (Exception e) {
            System.out.println("fail");
        }
        return in;

    }

    public static void main(String[] args) {
        OrderService order = new OrderService();
        System.out.println(order.getIn(3));
    }
}
