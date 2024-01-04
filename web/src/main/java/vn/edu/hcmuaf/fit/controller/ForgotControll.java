package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.Key;
import vn.edu.hcmuaf.fit.bean.RSAKeyGenerator;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.service.KeyService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "forgot", value = "/forgot")
public class ForgotControll extends HttpServlet {
    @Override
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
        String pub = keyse.getOldPublicKeyFromDatabase(user.getIdUser());
        keyse.reportKey(pub,time);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
