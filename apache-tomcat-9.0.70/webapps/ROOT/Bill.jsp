<%@ page import="vn.edu.hcmuaf.fit.bean.products" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.DetailInvoice" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.security.PrivateKey" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.security.KeyFactory" %>
<%@ page import="java.security.spec.PKCS8EncodedKeySpec" %>
<%--
  Created by IntelliJ IDEA.
  User: tranl
  Date: 12/4/2023
  Time: 10:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>

<%@ page import="java.util.Objects" %>
<%@ page import="java.util.Optional" %>


<script src="https://kjur.github.io/jsrsasign/jsrsasign-latest-all-min.js"></script>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hóa Đơn Điện Tử</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .success-message {
            background-color: #4CAF50; /* Màu xanh lá cây */
            color: white;
            padding: 15px;
            margin: 10px 0;
            border-radius: 5px;
        }


        .invoice-container {
            width: 80%;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            border-radius: 8px;
        }

        .header {
            text-align: center;
            margin-bottom: 20px;
        }

        .invoice-details {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .invoice-items {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .invoice-items th, .invoice-items td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        .total {
            margin-top: 20px;
            text-align: right;
        }
    </style>
</head>
<body>
<%--<%--%>

<%--    // Lấy khóa riêng tư từ session--%>
<%--    String privateKey = (String) session.getAttribute("priKey");--%>

<%--    System.out.println("Private Key: " + privateKey);--%>
<%--%>--%>


<div class="invoice-container">
    <div class="header">
        <h2>Hóa Đơn Điện Tử</h2>
    </div>

    <div class="invoice-details">
        <div>
            <p><strong>Người Nhận:</strong> ${sessionScope.invoice.nameuser}</p>
            <p><strong>Địa Chỉ:</strong> ${sessionScope.invoice.address}</p>
        </div>
        <div>
            <p><strong>Số Hóa Đơn:</strong>${sessionScope.invoice.idIn}</p>
            <p><strong>Ngày Tạo:</strong> ${sessionScope.invoice.datecreate}</p>
        </div>
    </div>

    <table class="invoice-items">
        <thead>
        <tr>
            <th>Mặt Hàng</th>
            <th>Số Lượng</th>
            <th>Đơn Giá</th>
            <th>Thành Tiền</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <%

                List<DetailInvoice> listde = (List<DetailInvoice>) session.getAttribute("listde");
                List<products> listp = (List<products>) session.getAttribute("listp");
                if (listde != null && listp != null) {
                    for (DetailInvoice de : listde) {
                        for (products ma : listp) {
                            if (de.getIdpro() == ma.getIdProduct()) {
            %>
            <td class=""><%= ma.getNameProduct() %></td>
            <td class=""><%= de.getQuantity() %></td>
            <td class=""><%= de.getPrice() %></td>
            <td class=""><%= de.getPrice() * de.getQuantity() %></td>
        </tr>
        <!-- Truyền dữ liệu sang JavaScript -->
        <script>
            var productDetails = productDetails || [];
            var productDetail = {
                name: "<%= ma.getNameProduct() %>",
                quantity: <%= de.getQuantity() %>,
                price: <%= de.getPrice() %>
            };


            productDetails.push(productDetail);

            console.log("Added to productDetails:", productDetail);

            // Kiểm tra giá trị của biểu thức nhúng
            console.log("Name:", "<%= ma.getNameProduct() %>");
            console.log("Quantity:", <%= de.getQuantity() %>);
            console.log("Price:", <%= de.getPrice() %>);

        </script>
        </tbody>

        <%    }
        }
        }
        }%>
    </table>
    <script>

        function signInvoice() {
           // console.log("signInvoice() has been called");
            // Lấy dữ liệu cần ký số từ trang
            var invoiceId = "${invoice.idIn}";
            var totalAmount = "${invoice.getTotal()}";
            var recipientName = "${sessionScope.invoice.nameuser}";
            var recipientAddress = "${invoice.address}";
            var creationDate = "${invoice.datecreate}";



            if (invoiceId !== '' && totalAmount !== '' && recipientName !== '' && recipientAddress !== '' && creationDate !== '') {
                // Các giá trị đã được lấy thành công, thực hiện các thao tác tiếp theo
                var signatureData = recipientName +" "+ recipientAddress +" " + invoiceId +" "+ creationDate +" "+ totalAmount;
                //  Thêm thông tin sản phẩm vào dữ liệu ký số

                for (var i = 0; i < productDetails.length; i++) {
                    var product = productDetails[i];
                    signatureData += " " + product.name + " " + product.quantity + " " + product.price + " ";
                    console.log("Added to signatureData:", product.name + " " + product.quantity + " " + product.price);

                }
                console.log("All products in productDetails:", JSON.stringify(productDetails, null, 2));

                console.log("invoiceId: " + invoiceId);
                console.log("totalAmount: " + totalAmount);
                console.log("recipientName: " + recipientName);
                console.log("recipientAddress: " + recipientAddress);
                console.log("creationDate: " + creationDate);

                // kt dl
                console.log("Dữ liệu cần ký số: " + signatureData);
                //Tiếp tục xử lý chữ ký số...
                // Khởi tạo đối tượng XMLHttpRequest
                var xhr = new XMLHttpRequest();

                // Xác định hàm xử lý sự kiện khi trạng thái của yêu cầu thay đổi
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        // Xử lý phản hồi từ server ở đây
                        console.log("Server Response:", xhr.responseText);
                    } else if (xhr.readyState === 4 && xhr.status !== 200) {
                        // Xử lý trạng thái không phải 200
                        console.error("Server Error. Status:", xhr.status);
                    }
                };

                // Mở kết nối và cấu hình yêu cầu
                xhr.open("POST", "/kydl", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                // Gửi dữ liệu tới server
                xhr.send("signatureData=" + encodeURIComponent(signatureData));

            } else {
                // Hiển thị thông báo hoặc thực hiện các thao tác khác khi dữ liệu không hợp lệ
                alert("Không thể lấy đủ thông tin để ký số.");
            }
            // ...




        }
    </script>
    <div class="total">
        <div class="signature-result" id="signatureResult"></div>
        <form action="/kydl" method="post">
            <button type="button" onclick="signInvoice()">Xác nhận và Ký số</button>
        </form>
        <p><strong>Tổng Cộng:</strong> ${invoice. getTotal()}</p>

    </div>
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="success-message">${sessionScope.successMessage}</div>
        <% session.removeAttribute("successMessage"); %>
    </c:if>

</div>

</body>
</html>
