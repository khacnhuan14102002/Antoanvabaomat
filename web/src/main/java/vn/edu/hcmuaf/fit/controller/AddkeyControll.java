package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.Key;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.service.KeyService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "addkey", value = "/addkey")
public class AddkeyControll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String publicKey = req.getParameter("key");
        String idUser = req.getParameter("idUser");

        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        Key key = new Key(user.getIdUser(),publicKey,null,time,0);

        KeyService keys = new KeyService();
        List<Key> keyOld= new ArrayList<>();
        keyOld = keys.getListKeyByIdUser(Integer.parseInt(idUser));
        for (Key k : keyOld) {
            keys.updateOldKey(k.getPubkey());

        }
        keys.addKey(key);
        response.sendRedirect("/successAccount");
    }

    public static void main(String[] args) {

    }
}
