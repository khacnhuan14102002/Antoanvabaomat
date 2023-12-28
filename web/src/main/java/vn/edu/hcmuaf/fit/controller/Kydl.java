package vn.edu.hcmuaf.fit.controller;

import javax.servlet.annotation.WebServlet;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import vn.edu.hcmuaf.fit.bean.*;
import vn.edu.hcmuaf.fit.service.*;

@WebServlet(name = "Kydl", value = "/Kydl")
public class Kydl extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Kiểm tra xem khóa đã được lưu trong session chưa
        if (session.getAttribute("publicKey") == null || session.getAttribute("privateKey") == null) {
            // Nếu chưa có khóa, tạo và lưu chúng
            RSAKeyGenerator rsaKeyGenerator = null;

            try {
                rsaKeyGenerator = new RSAKeyGenerator();
                rsaKeyGenerator.createKeys();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                request.setAttribute("error", "Không thể tạo khóa RSA do lỗi hệ thống.");
            }


// Lấy chuỗi Base64 của khóa công khai và riêng tư
            String publicKeyBefore = rsaKeyGenerator.getPublicKey();
            String privateKeyBefore = rsaKeyGenerator.getPrivateKey();

            // Kiểm tra giá trị trước khi đặt vào session
            System.out.println("publicKeyBefore: " + publicKeyBefore);
            System.out.println("privateKeyBefore: " + privateKeyBefore);

            // Đặt giá trị khóa vào session
            session.setAttribute("publicKey", publicKeyBefore);
            session.setAttribute("privateKey", privateKeyBefore);

        }

        // Chuyển hướng đến "Bill.jsp"
        RequestDispatcher dispatcher = request.getRequestDispatcher("Bill.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Lấy khóa từ session
            String privateKey = (String) session.getAttribute("privateKey");
            // Tiếp tục xử lý
        }
//       lấy dữ liệu từ form
        String signature = request.getParameter("signatureResult");
        // Ví dụ: in thông tin chữ ký ra console
        System.out.println("Chữ ký số: " + signature);


        request.getRequestDispatcher("Bill.jsp").forward(request, response);

    }


}
