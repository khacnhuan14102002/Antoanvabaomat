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

    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Retrieve date and time parameters from the request
        String reportDate = req.getParameter("reportDate");
        String reportTime = req.getParameter("reportTime");

        // Combine date and time strings and convert to Timestamp
        String dateTimeString = reportDate + " " + reportTime + ":00"; // Adding seconds part
        Timestamp time = Timestamp.valueOf(dateTimeString);

        KeyService keys = new KeyService();
        String publicKey = keys.getPublic(user.getIdUser());
        keys.reportKey(publicKey, time);
        response.sendRedirect("/successAccount");
    }

}
