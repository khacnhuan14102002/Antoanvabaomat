package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.Key;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.service.KeyService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "reportkey", value = "/reportkey")
public class ReportKeyControll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());


        KeyService keys = new KeyService();
        String publicKey = keys.getPublic(user.getIdUser());
        keys.reportKey(publicKey,time);
        response.sendRedirect("/successAccount");

    }
}
