package vn.edu.hcmuaf.fit.controller;

import java.io.PrintWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
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
        try {
            rsa = new RSAKeyGenerator();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        KeyService keyse = new KeyService();
        KeyPair key = rsa.createKey();
        String publicKey = rsa.convertPublicKeyToString(key.getPublic());
        String priKey = rsa.convertPrivateKeyToString(key.getPrivate());
        Key uk= new Key(user.getIdUser(),publicKey,null,time,0);
        keyse.addKey(uk);session.setAttribute("priKey", priKey);
        PrintWriter out = resp.getWriter();
        out.print(priKey);  // Send only the private key string in the response
        out.close();
        System.out.println("private key " + priKey);
        resp.sendRedirect("/successAccount");

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
