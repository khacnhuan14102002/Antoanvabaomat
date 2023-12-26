<%@ page import="vn.edu.hcmuaf.fit.bean.products" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.DetailInvoice" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: tranl
  Date: 12/4/2023
  Time: 10:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<%
    // Lấy khóa riêng tư từ session
    String privateKey = (String) session.getAttribute("privateKey");
%>
<script src="https://kjur.github.io/jsrsasign/jsrsasign-latest-all-min.js"></script>
<script>
    function signInvoice() {
        // Lấy dữ liệu cần ký số từ trang
        var invoiceId = "${invoice.idIn}";
        var totalAmount = "${invoice.getTotal()}";
        var recipientName = "${sessionScope.invoice.nameuser}";
        var recipientAddress = "${invoice.address}";
        var creationDate = "${invoice.datecreate}";


        // Tạo dữ liệu để ký số (ở đây, ví dụ sử dụng mã hóa SHA-256)
        var signatureData = recipientName + recipientAddress + invoiceId + creationDate + totalAmount;
        // Lấy khóa riêng tư từ JSP
        var privateKey = "<%= privateKey %>";
        // Sử dụng khóa riêng tư để ký số (mã hóa SHA-256)
        var rsa = new RSAKey();
        rsa.readPrivateKeyFromPEMString(privateKey);
        var hSig = rsa.signString(signatureData, 'sha256');

        // Encode chữ ký dưới dạng chuỗi Base64
        var signature = hSig.replace(/(.{64})/g, "$1\n");


        // Gán chữ ký số vào trường  trong form
        document.getElementById("signatureResult").innerText = "Chữ ký số: " + signature;
    }
</script>
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
        </tbody>

            <%    }
            }
            }
            }%>
    </table>

    <div class="total">
        <div class="signature-result" id="signatureResult"></div>
        <form action="/order" method="post">
            <button onclick="signInvoice()">Xác nhận và Ký số</button>
        </form>
        <p><strong>Tổng Cộng:</strong> ${invoice. getTotal()}</p>

        <c:if test="${signatureAdded}">
            <p style="color: green;">Hóa đơn đã được ký số thành công!</p>
            <p>Chữ ký: ${sessionScope.signature}</p>
            <%-- In giá trị chữ ký ra console --%>
            <%
                System.out.println("Signature value in JSP: " + session.getAttribute("signature"));
            %>
        </c:if>
        <c:if test="${not signatureAdded}">
            <p style="color: red;">Ấn xác nhận để ký hóa đơn. </p>
        </c:if>

    </div>


</div>

</body>
</html>
