<%@ page import="java.util.ArrayList" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.products" %>

<%@ page import="vn.edu.hcmuaf.fit.bean.User" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.ProductCart" %>
<%@ page import="java.util.Map" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.DetailInvoice" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<head>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chi Tiết Lịch Sử</title>
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,500,700" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
        <link type="text/css" rel="stylesheet" href="css/slick.css"/>
        <link type="text/css" rel="stylesheet" href="css/slick-theme.css"/>
        <link type="text/css" rel="stylesheet" href="css/nouislider.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link type="text/css" rel="stylesheet" href="css/style2.css"/>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <link type="text/css" rel="stylesheet" href="css/style2.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <link href="build/css/manager.css" rel="stylesheet" type="text/css"/>
        <style>
            /*body {*/
            /*    font-family: Arial, sans-serif;*/
            /*    background-color: #f4f4f4;*/
            /*    margin: 0;*/
            /*    padding: 0;*/
            /*}*/

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
<body>
<header>
    <div id="top-header">
        <div class="container">
            <ul class="header-links pull-left">
                <li><a href="#"><i class="fa fa-phone"></i>0723658910</a></li>
                <li><a href="#"><i class="fa fa-envelope-o"></i> shopntt@email.com</a></li>
                <li><a href="#"><i class="fa fa-map-marker"></i> Linh Trung, Thủ Đức</a></li>
            </ul>
            <ul class="header-links pull-right">
                <%
                    User user = (User) session.getAttribute("user");
                    if (user != null) {
                %>
                <li><a href="/successAccount"><i class="fa fa-user-o"></i> <%= user.getNameUser() %></a></li>
                <li><a href="/historyinvoice"><i class="fa fa-bars"></i>Lịch sử mua hàng</a></li>
                <%--    Nếu Roleus = 1 thì là admin hiện chữ tài khoản     --%>
                <%
                    if (user.getRoleUs() == 1) {
                %>
                <li><a href="/adminpage"><i class="fa fa-cog"></i>Quản lý</a></li>
                <%
                    }
                %>
                <li><a href="logout"><i class="fa fa-sign-out"></i> Đăng xuất</a></li>
                <%
                } else {
                %>
                <li><a href="/login"><i class="fa fa-user-o"></i> Tài Khoản</a></li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
    <div id="header">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="header-logo">
                        <a href="/index" class="logo">
                            <img src="./image/ntt.png" alt="">
                        </a>
                    </div>
                    <div class="page">
                        <p>CHI TIẾT HÓA ĐƠN</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="header-search">
                        <form>
                            <input class="input" placeholder="Tìm kiếm tại đây">
                            <button class="search-btn">Tìm kiếm</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>

<div class="container bootdey">
    <div class="row bootstrap snippets">
        <div class="col-lg-9 col-md-9 col-sm-12">
            <div class="col-lg-12 col-sm-12">
                <h1 class="title">CHI TIẾT HÓA ĐƠN</h1>
            </div>
            <div class="col-lg-12 col-sm-12 hero-feature">
                <div class="invoice-details">
                    <div>
                        <p><strong>Người Nhận:</strong> ${invoice.nameuser}</p>
                        <p><strong>Địa Chỉ:</strong> ${invoice.address}</p>
                    </div>
                    <div>
                        <p><strong>Số Hóa Đơn:</strong>${invoice.idIn}</p>
                        <p><strong>Ngày Tạo:</strong> ${invoice.datecreate}</p>
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
                            List<DetailInvoice> listde = (List<DetailInvoice>) request.getAttribute("listde");
                            List<products> listp = (List<products>) request.getAttribute("listp");

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
                    }%>
                </table>

                <div class="total">

                    <p><strong>Tổng Cộng:</strong> ${invoice. getTotal()}</p>
                </div>

            </div>
            </div>
            <div class="btn-group btns-cart">
                <button type="button" class="bt btn btn-primary"><i class="fa fa-arrow-circle-left"></i><a href="/index">Trở về trang chủ</a></button>
                <form action="">
                    <button>Xác nhận</button>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<script src="js/main.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slick.min.js"></script>
<script src="js/nouislider.min.js"></script>
<script src="js/jquery.zoom.min.js"></script>
<script src="js/main.js"></script>

</body>

</html>