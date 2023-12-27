package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.service.DetailInvoiceService;
import vn.edu.hcmuaf.fit.service.ManagerService;
import vn.edu.hcmuaf.fit.service.OrderService;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "order", value = "/order")
public class OrderControll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService or = new OrderService();


        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Invoice invoice = new Invoice();
        invoice = or.getIn(user.getIdUser());
        session.setAttribute("invoice", invoice);
        DetailInvoiceService detail = new DetailInvoiceService();
        ManagerService mn = new ManagerService();
        int invoiceid = invoice.getIdIn();

        ArrayList<DetailInvoice> listde = detail.getAllIn(invoiceid);
        List<products> p = mn.getAllProduct();

        request.setAttribute("listp", p);
        request.setAttribute("listde", listde);
        session.setAttribute("listde", listde);
        session.setAttribute("listp", p);
        request.getRequestDispatcher("Bill.jsp").forward(request, response);
    }

}
