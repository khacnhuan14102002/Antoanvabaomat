package vn.edu.hcmuaf.fit.controller;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
//import jdk.internal.org.jline.keymap.KeyMap;
import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.service.*;

@WebServlet(name = "KeyController", value = "/KeyController")
public class KeyController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra xem checkbox "Chưa có key" có được chọn hay không
        String keyStatus = req.getParameter("fav_language");
        boolean generateKey = keyStatus != null && keyStatus.equals("JavaScript");

        // Create an instance of UserKey
        Key userKey = new Key();

        // Generate key pair nếu checkbox "Chưa có key" được chọn
        KeyPair keyPair = generateKey ? userKey.createKey() : null;

        if (generateKey && keyPair != null) {
            // Nếu checkbox được chọn và đã tạo key pair, lưu key vào cơ sở dữ liệu
            KeyService se = new KeyService();
            String pub = userKey.convertPublicKeyToString(keyPair.getPublic());
            String pri = userKey.convertPrivateKeyToString(keyPair.getPrivate());
            se.addKey(user.getIdUser(), pub, pri);
            session.setAttribute("userKeyPair", keyPair);
        }

        // Forward to a JSP page to display the result or perform additional actions
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
