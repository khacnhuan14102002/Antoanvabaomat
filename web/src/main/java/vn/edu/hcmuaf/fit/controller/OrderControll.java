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
        request.setAttribute("invoice",invoice);
        DetailInvoiceService detail = new DetailInvoiceService();
        ManagerService mn = new ManagerService();
        int invoiceid = invoice.getIdIn();

        ArrayList<DetailInvoice> listde = detail.getAllIn(invoiceid);
        List<products> p = mn.getAllProduct();
        request.setAttribute("listp",p);
        request.setAttribute("listde",listde);

        request.getRequestDispatcher("Bill.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        // Tạo key pair
        KeyPair keyPair = new Key().createKey();
        PrivateKey privateKey = keyPair.getPrivate();

        // Lấy các thông tin từ request (giả sử bạn có các trường dữ liệu từ form)
        String nameuser = request.getParameter("nameuser");
            if (nameuser == null) {
                throw new Exception("Tham số 'nameuser' không tồn tại trong yêu cầu.");
            }
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String statusIn = request.getParameter("statusIn");

        double total = Double.parseDouble(request.getParameter("total"));
        Timestamp datecreate = new Timestamp(System.currentTimeMillis());
        String phone = request.getParameter("phone");
        int idUs = Integer.parseInt(request.getParameter("idUs"));

        // Tạo đối tượng Invoice
        Invoice invoice = new Invoice(nameuser, address, type, statusIn, total, datecreate, phone, idUs);

        // Tạo và lưu ký số cho hóa đơn
        invoice.signInvoice(privateKey);

        // Lưu thông tin hóa đơn vào request để hiển thị trên trang JSP
        request.setAttribute("invoice", invoice);
        request.setAttribute("signatureAdded", true);

        request.setAttribute("successMessage", "Hóa đơn đã ký!");
        } catch (Exception e) {
            request.setAttribute("signatureAdded", false);
            request.setAttribute("errorMessage", e.getMessage());

        }
        //         Chuyển hướng đến trang Bill.jsp
//       request.getRequestDispatcher("/Bill.jsp").forward(request, response);
    }

}
