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
<%
    // Lấy khóa riêng tư từ session
    String privateKeyBefore = (String) session.getAttribute("privateKey");

    if (privateKeyBefore != null) {
        System.out.println("Private Key from Session in JSP: " + privateKeyBefore);
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKeyBefore.getBytes());
        System.out.println("Encoded Private Key in JSP: " + encodedPrivateKey);
    } else {
        System.out.println("Private Key not found in Session.");
    }
    // Kiểm tra xem khóa riêng tư có tồn tại hay không trước khi mã hóa
    String encodedPrivateKey = (privateKeyBefore != null) ? Base64.getEncoder().encodeToString(privateKeyBefore.getBytes()) : "";
    System.out.println("Encoded Private Key: " + encodedPrivateKey);
%>


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
            var productDetails = [];
            var productDetail = {
                name: "<%= ma.getNameProduct() %>",
                quantity: <%= de.getQuantity() %>,
                price: <%= de.getPrice() %>
            };

            if (typeof productDetails === 'undefined') {
                var productDetails = []; // Khai báo nếu chưa tồn tại
            }
            productDetails.push(productDetail);
            console.log("Added to productDetails:", productDetail);
        </script>
        </tbody>

            <%    }
            }
            }
            }%>
    </table>
    <script>

        function signInvoice() {
            // Lấy dữ liệu cần ký số từ trang
            var invoiceId = "${invoice.idIn}";
            var totalAmount = "${invoice.getTotal()}";
            var recipientName = "${sessionScope.invoice.nameuser}";
            var recipientAddress = "${invoice.address}";
            var creationDate = "${invoice.datecreate}";



            if (invoiceId !== '' && totalAmount !== '' && recipientName !== '' && recipientAddress !== '' && creationDate !== '') {
                // Các giá trị đã được lấy thành công, thực hiện các thao tác tiếp theo
                var signatureData = recipientName + recipientAddress + invoiceId + creationDate + totalAmount;
                //  Thêm thông tin sản phẩm vào dữ liệu ký số

                for (var i = 0; i < productDetails.length; i++) {
                    var product = productDetails[i];
                    signatureData += product.name + product.quantity + product.price;
                }

                // kt dl
                console.log("Dữ liệu cần ký số: " + signatureData);
                //Tiếp tục xử lý chữ ký số...
            } else {
                // Hiển thị thông báo hoặc thực hiện các thao tác khác khi dữ liệu không hợp lệ
                alert("Không thể lấy đủ thông tin để ký số.");
            }
            console.log("invoiceId: " + invoiceId);
            console.log("totalAmount: " + totalAmount);
            console.log("recipientName: " + recipientName);
            console.log("recipientAddress: " + recipientAddress);
            console.log("creationDate: " + creationDate);

            // Lấy khóa riêng tư

            var privateKey = "${sessionScope.privateKeyBefore}";

// Kiểm tra xem khóa riêng tư có tồn tại hay không
            if (privateKey) {
                // Thực hiện các thao tác ký số
                try {
                    var rsa = new RSAKey();
                    rsa.readPrivateKeyFromPEMString(privateKey);
                    var hSig = rsa.signString(signatureData, 'sha256');

                    // Encode chữ ký dưới dạng chuỗi Base64
                    var signature = hSig.replace(/(.{64})/g, "$1\n");


                    // Gán chữ ký số vào trường  trong form
                    document.getElementById("signatureResult").innerText = "Chữ ký số: " + signature;
                } catch (e) {
                    alert("Có lỗi xảy ra khi ký số: " + e.message);
                }
            } else {
                alert("Không thể tìm thấy khóa riêng tư. Vui lòng thử lại.");
            }

            // Sử dụng khóa riêng tư để ký số (mã hóa SHA-256)

        }
    </script>
    <div class="total">
        <div class="signature-result" id="signatureResult"></div>
        <form action="/Kydl" method="post">
            <button onclick="signInvoice()">Xác nhận và Ký số</button>
        </form>
        <p><strong>Tổng Cộng:</strong> ${invoice. getTotal()}</p>

<%--        <c:if test="${signatureAdded}">--%>
<%--            <p style="color: green;">Hóa đơn đã được ký số thành công!</p>--%>
<%--            <p>Chữ ký: ${sessionScope.signature}</p>--%>
<%--            &lt;%&ndash; In giá trị chữ ký ra console &ndash;%&gt;--%>
<%--            <%--%>
<%--                System.out.println("Signature value in JSP: " + session.getAttribute("signature"));--%>
<%--            %>--%>
<%--        </c:if>--%>
<%--        <c:if test="${not signatureAdded}">--%>
<%--            <p style="color: red;">Ấn xác nhận để ký hóa đơn. </p>--%>
<%--        </c:if>--%>

    </div>


</div>

</body>
</html>
