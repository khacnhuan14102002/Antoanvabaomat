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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        // Lấy chữ ký số từ request
//        String signature = request.getParameter("signature");
//
        // Ví dụ: lấy dữ liệu từ form
        String signature = request.getParameter("signatureResult");
        // Ví dụ: in thông tin chữ ký ra console
        System.out.println("Chữ ký số: " + signature);


//        // Thực hiện xác minh chữ ký số, lưu ý: cần có logic xác minh chữ ký thực tế ở đây
//        boolean isSignatureValid = verifySignature(signature);
//
//        // Lấy thông tin hóa đơn và sản phẩm từ session hoặc request
//        HttpSession session = request.getSession();
//        Invoice invoice = (Invoice) session.getAttribute("invoice");
//        List<DetailInvoice> listde = (List<DetailInvoice>) session.getAttribute("listde");
//        List<products> listp = (List<products>) session.getAttribute("listp");
//        // Set thông tin vào session để giữ lại sau khi ấn xác nhận
//        session.setAttribute("invoice", invoice);
//        session.setAttribute("listde", listde);
//        session.setAttribute("listp", listp);
//
//        System.out.println("Invoice from session: " + invoice);
//        System.out.println("List of DetailInvoice from session: " + listde);
//        System.out.println("List of products from session: " + listp);
//
//        // Tạo đối tượng RSAKeyGenerator và tạo khóa cặp
//        RSAKeyGenerator rsaKeyGenerator = null;
//        try {
//            rsaKeyGenerator = new RSAKeyGenerator();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            throw new ServletException("Failed to initialize RSAKeyGenerator", e);
//        }
//        rsaKeyGenerator.createKeys();
//// Lưu khóa công khai và khóa riêng tư vào session
//        String publicKey = rsaKeyGenerator.getPublicKey();
//        String privateKey = rsaKeyGenerator.getPrivateKey();
//        session.setAttribute("publicKey", publicKey);
//        session.setAttribute("privateKey", privateKey);
//
//        // Gán thông báo thành công hoặc thất bại vào thuộc tính để hiển thị trên trang JSP
//        session.setAttribute("signatureAdded", isSignatureValid);
//        if (isSignatureValid) {
//            session.setAttribute("signature", signature);
//            System.out.println("Signature value: " + signature);
//        }
//
//        System.out.println("Signature value in doPost: " + request.getSession().getAttribute("signature"));
//
//        // Chuyển hướng hoặc render lại trang JSP
        request.getRequestDispatcher("Bill.jsp").forward(request, response);
//    }
//
//    private boolean verifySignature(String signature) {
//        // Thực hiện xác minh chữ ký số ở đây, có thể cần sử dụng thư viện mã hóa chữ ký số
//        // Trả về true nếu chữ ký hợp lệ, ngược lại trả về false
//        // Ví dụ: sử dụng thư viện Bouncy Castle để xác minh chữ ký
//        // Đây chỉ là một ví dụ giả định, bạn cần thay đổi để phản ánh thực tế của bạn.
//        return true; // hoặc false nếu xác minh thất bại
//    }
    }
}
