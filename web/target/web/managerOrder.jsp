<%@ page import="vn.edu.hcmuaf.fit.bean.Invoice" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.User" %>
<%@ page import="javax.management.relation.Role" %>
<%@ page import="vn.edu.hcmuaf.fit.bean.Manager" %><%--
  Created by IntelliJ IDEA.
  User: Kim_Tuyet
  Date: 5/15/2023
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Hóa đơn</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- Thư viện jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Thư viện Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="build/css/manager.css" rel="stylesheet" type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<style>
    img {
        width: 200px;
        height: 120px;
    }
</style>

<body>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Quản lý Hóa Đơn</h2>

                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>
                    <span class="custom-checkbox">
                        <input type="checkbox" id="selectAll">
                        <label for="selectAll"></label>
                    </span>
                </th>
                <th>Mã hóa đơn</th>
                <th>Mã khách hàng</th>
                <th>Tên khách hàng</th>
                <th>Địa chỉ</th>
                <th>Số điện thoại</th>
                <th>Tổng tiền</th>
                <th>Phương thức <br>thanh toán</th>
                <th>Ngày đặt hàng</th>
                <th>Trạng thái</th>
                <th>Xác Minh</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Invoice> listI = (List<Invoice>) request.getAttribute("listI");
                for (Invoice in_voice : listI) {
            %>
            <tr>
                <td>
                        <span class="custom-checkbox">
                            <input type="checkbox" id="checkbox1" name="options[]" value="1">
                            <label for="checkbox1"></label>
                        </span>
                </td>
                <td class="invoice-IdIn-<%=in_voice.getIdIn()%>"><%=in_voice.getIdIn()%>
                </td>
                <td class="invoice-IdUs-<%=in_voice.getIdIn()%>"><%=in_voice.getIdUs()%>
                </td>
                <td class="invoice-NameUs-<%=in_voice.getIdIn()%>"><%=in_voice.getNameuser()%>
                </td>
                <td class="invoice-Address-<%=in_voice.getIdIn()%>"><%=in_voice.getAddress()%>
                </td>
                <td class="invoice-N_phone-<%=in_voice.getIdIn()%>"><%=in_voice.getPhone()%>
                </td>
                <td class="invoice-Total-<%=in_voice.getIdIn()%>"><%=in_voice.getTotal()%>
                </td>
                <td class="invoice-Type-<%=in_voice.getIdIn()%>"><%=in_voice.getType()%>
                </td>
                <td class="invoice-Date-<%=in_voice.getIdIn()%>"><%=in_voice.getDatecreate()%>
                </td>
                <td class="invoice-Status"<%=in_voice.getIdIn()%>><%=in_voice.getStatusIn()%>
                </td>
               <td>  <form action="/vetify" method="get">
                <input type="hidden" name="Idinvoice" value="<%=in_voice.getIdIn()%>">
                   <input type="hidden" name="IdUser" value="<%=in_voice.getIdUs()%>">
                <button type="submit" class="btn" id="vetifyButton">Xác minh</button>
                <%--                            <button type="submit" class="btn btn-xac-minh">Xác minh</button>--%>
            </form>

                </td>
            </tr>
            <% }%>
            </tbody>
        </table>
        <div class="clearfix">
            <ul class="pagination">
                <li class="page-item active"><a href="#" class="page-link">1</a></li>
            </ul>
        </div>
    </div>
    <div>
    <a href="/adminpage" style="width: 20%; float:left">
        <button type="button" class="btn btn-primary">Trở về</button>

    </a>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
        Xuất file Excel
    </button>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>           <form action="/xuatexcelHoadon" method="get" >
            <div class="modal-body">


                        <p>chọn thời gian bắt đầu</p>
                        <input name="dateHoaDons" class="input" type="text" placeholder="nhập tháng muốn xuất">
                        <p>chọn thời gian kết thúc</p>
                        <input name="dateHoaDone" class="input" type="text" placeholder="nhập năm muốn xuất">


            </div>
            <div class="modal-footer">
                <button class="primary-btn">Xuất</button>
            </div>
        </form>
        </div>
    </div>
</div>
<div class="modal" tabindex="-1" role="dialog" id="myModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thông báo</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Content of the modal goes here -->
                <p style="color:red">${mess}</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<script src="build/js/manager.js" type="text/javascript"></script>
<script>
    $(document).ready(function() {
        // Gán sự kiện click cho nút "Xác minh"
        $("#vetifyButton").click(function(event) {
            // Ngăn chặn hành động mặc định của nút
            event.preventDefault();

            // Hiển thị modal
            $("#myModal").modal("show");

            // Thực hiện form submission
            $("#verificationForm").submit();
        });
    });
</script>
</body>
</html>
