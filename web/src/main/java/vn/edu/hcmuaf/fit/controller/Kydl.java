package vn.edu.hcmuaf.fit.controller;

import javax.servlet.annotation.WebServlet;

import java.security.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

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

            // Lấy private key từ session
        String privateKey = (String) session.getAttribute("priKey");
        System.out.println("Private Key retrieved from session: " + privateKey);
            // Lấy dữ liệu từ form
        String signatureData = request.getParameter("signatureData");
        System.out.println("Dữ liệu ký số từ client: " + signatureData);

        try {
            // Kiểm tra xem private key và dữ liệu ký số có tồn tại không
            if (privateKey != null && !privateKey.isEmpty() && signatureData != null && !signatureData.isEmpty()) {
                // Ký dữ liệu
                String signature = signData(signatureData, privateKey);

                // In chữ ký số vào log
                System.out.println("Chữ ký số: " + signature);

                // Tiếp tục xử lý hoặc gửi chữ ký về client nếu cần

            } else {
                // Xử lý khi private key hoặc dữ liệu không hợp lệ
                System.out.println("Private Key hoặc dữ liệu không hợp lệ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý nếu có lỗi xảy ra trong quá trình ký
        }
        request.getSession().setAttribute("successMessage", "Ký số thành công!");
        request.getRequestDispatcher("Bill.jsp").forward(request, response);

    }

    private String signData(String data, String privateKeyString) throws Exception {
        // Base64 decode private key
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);

        // Create private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // Initialize Signature with private key
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        // Update data to be signed
        signature.update(data.getBytes("UTF-8"));

        // Generate signature
        byte[] signatureBytes = signature.sign();

        // Base64 encode the signature
        String base64Signature = Base64.getEncoder().encodeToString(signatureBytes);
        // Print the signature to console
        System.out.println("Chữ ký: " + base64Signature);

        // Return the base64-encoded signature
        return base64Signature;

    }



}
