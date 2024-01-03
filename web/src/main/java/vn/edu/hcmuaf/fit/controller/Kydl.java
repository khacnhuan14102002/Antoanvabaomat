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

@WebServlet(name = "kydl", value = "/kydl")
public class Kydl extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Lấy private key từ session
            String privateKey = (String) session.getAttribute("privateKey");

            // Lấy dữ liệu từ form
            String signatureData = request.getParameter("signatureData");
            System.out.println("dl ký số: " + signatureData);
            // Kiểm tra xem chữ ký có tồn tại và không rỗng
            if (privateKey != null && !privateKey.isEmpty() && signatureData != null) {
                try {
                    // Sử dụng private key để ký dữ liệu
                    String signature = signatureData(signatureData, privateKey);

                    // Tiếp tục xử lý hoặc gửi chữ ký về client nếu cần
                    System.out.println("Chữ ký số: " + signature);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Xử lý nếu có lỗi xảy ra trong quá trình ký
                }
            } else {
                // Xử lý khi private key hoặc dữ liệu không hợp lệ
                System.out.println("Private Key hoặc dữ liệu không hợp lệ");
            }
        } else {
            // Xử lý khi session không tồn tại (người dùng chưa đăng nhập)
            System.out.println("Người dùng chưa đăng nhập");
        }


        request.getRequestDispatcher("Bill.jsp").forward(request, response);

    }

    private String signatureData(String signatureData, String privateKey) {

        return signatureData;
    }


}
