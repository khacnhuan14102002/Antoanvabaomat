package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.DetailInvoice;
import vn.edu.hcmuaf.fit.bean.Invoice;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.bean.products;
import vn.edu.hcmuaf.fit.service.DetailInvoiceService;
import vn.edu.hcmuaf.fit.service.ManagerService;
import vn.edu.hcmuaf.fit.service.OrderService;
import vn.edu.hcmuaf.fit.service.StoreService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "detailinvoice", value = "/detailinvoice")
public class DetailInvoiveControll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DetailInvoiceService detail = new DetailInvoiceService();
        OrderService or = new OrderService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ManagerService mn = new ManagerService();
        String idinvoice = request.getParameter("Idinvoice");
        int invoiceid =0;
        try{
            invoiceid = Integer.parseInt(idinvoice);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        StringBuilder randomCode = new StringBuilder("SHM");

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int randomDigit = random.nextInt(10);
            randomCode.append(randomDigit);
        }

       String code = randomCode.toString();
        ArrayList<DetailInvoice> listde = detail.getAllIn(invoiceid);
        List<products> p = mn.getAllProduct();
        Invoice invoice = new Invoice();
        invoice = or.getIn(user.getIdUser());
        request.setAttribute("invoice",invoice);
        request.setAttribute("code",code);
        request.setAttribute("listp",p);
        request.setAttribute("listde",listde);
       request.getRequestDispatcher("DetailHistory.jsp").forward(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
