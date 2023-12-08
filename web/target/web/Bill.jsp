<%--
  Created by IntelliJ IDEA.
  User: tranl
  Date: 12/4/2023
  Time: 10:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>
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
            <p><strong>Người Nhận:</strong> John Doe</p>
            <p><strong>Địa Chỉ:</strong> 123 Main Street, Cityville</p>
        </div>
        <div>
            <p><strong>Số Hóa Đơn:</strong> INV-12345</p>
            <p><strong>Ngày Tạo:</strong> 2023-01-01</p>
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
            <td>Item 1</td>
            <td>2</td>
            <td>$50.00</td>
            <td>$100.00</td>
        </tr>
        <tr>
            <td>Item 2</td>
            <td>1</td>
            <td>$75.00</td>
            <td>$75.00</td>
        </tr>
        </tbody>
    </table>

    <div class="total">
        <p><strong>Tổng Cộng:</strong> $175.00</p>
    </div>
</div>

</body>
</html>
