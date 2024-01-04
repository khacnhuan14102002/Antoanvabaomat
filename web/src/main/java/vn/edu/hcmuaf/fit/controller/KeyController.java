package vn.edu.hcmuaf.fit.controller;

import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.Date;
//import jdk.internal.org.jline.keymap.KeyMap;
import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.service.*;

@WebServlet(name = "KeyController", value = "/KeyController")
public class KeyController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        User user = (User) session.getAttribute("user");
        RSAKeyGenerator rsa;
        rsa = new RSAKeyGenerator();
        KeyService keyse = new KeyService();
        KeyPair key = null;
        try {
            key = rsa.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        PublicKey publicKey = rsa.genPublicKey(key);
        PrivateKey priKey = rsa.genPrivateKey(key);
        String pri = rsa.privateKeyToString(priKey);
        String publicString = rsa.publicKeyToString(publicKey);
        Key uk= new Key(user.getIdUser(),publicString,null,time,0);
        keyse.addKey(uk);
        session.setAttribute("priKey", pri);

        PrintWriter out = resp.getWriter();
        out.print(pri);  // Send only the private key string in the response
        out.close();
//        System.out.println("private key " + priKey);
        resp.sendRedirect("/successAccount");

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public static void main(String[] args) {
        KeyPair key = null;
        RSAKeyGenerator rsa;
        rsa = new RSAKeyGenerator();
        try {
            key = rsa.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        PublicKey publicKey = rsa.genPublicKey(key);
        PrivateKey priKey = rsa.genPrivateKey(key);
        String pri = rsa.privateKeyToString(priKey);
        String publicString = rsa.publicKeyToString(publicKey);
        System.out.println(pri);
    }
}
