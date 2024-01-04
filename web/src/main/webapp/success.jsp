<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập thành công</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="css/Success.css">
</head>
<body>
<div class="col-sm-5 container">
    <form action="user" method="post">
        <h4>Cập nhật thông tin</h4>
        <label>Họ tên</label>
        <input name="fullname" type="text" class="form-control" value="${user.getNameUser()}">
        <label>email</label>
        <input name="email" type="text" class="form-control" value="${user.getEmailUs()}">
        <label>Password</label>
        <input name="pass" type="password" class="form-control" value="${user.getPass()}">
        <label>Số điện thoại</label>
        <input name="sdt" type="text" class="form-control" value="${user.getPhone()}">

        <%

            String key = (String) request.getAttribute("key");
            if(key != null) { %>
        <label>Publickey</label>
        <input name="pukey" type="text" class="form-control" value="<%= key %>">
        <% } %>
        <button class="btn btn-success" style="margin-left:60px">Cập nhật</button>

        <div class="dropdown" style="margin-top:-54px;margin-left:150px">
            <button class="btn btn-success dropdown-toggle" type="button" data-toggle="dropdown">Key
                <span class="caret"></span></button>
            <ul class="dropdown-menu">
                <li><a href="#" onclick="showKeyForm('Nhập Key')">Nhập Key</a></li>
                <li><a href="#" onclick="reportKey()">Báo cáo Key</a></li>
                <li><a href="#" onclick="generateKey()">Tạo Key</a></li>
                <li><a href="#" onclick="forgotKey()">Quên Key</a></li>
            </ul>
        </div>

    </form>



    <div id="keyForm" style="display: none;">
        <!-- Add your key input form here -->
        <form action="addkey" method="post">
            <label>Nhập Key:</label>
            <input name="key" type="text" class="form-control">
            <button class="btn btn-success">Xác nhận</button>
        </form>
    </div>
    <div id="baoCaoKeyForm" style="display: none;">
        <!-- Add your Báo cáo Key input form here -->
        <form id="reportKeyForm" action="reportkey" method="post">
            <label for="reportDate">Ngày báo cáo:</label>
            <input type="date" id="reportDate" name="reportDate" required>
            <label for="reportTime">Giờ báo cáo:</label>
            <input type="time" id="reportTime" name="reportTime" required>
            <button class="btn btn-info">Báo cáo</button>
        </form>
    </div>
    <div id="taoKeyForm" style="display: none;">
        <!-- Add your Tạo Key input form here -->
        <form id="createKeyForm" action="KeyController" method="get">
            <!-- Add fields for key generation if needed -->
            <button class="btn btn-warning">Tạo Key</button>
        </form>

        <!-- Display private key here -->

    </div>
    <div id="quenKeyForm" style="display: none;">
        <!-- Add your Tạo Key input form here -->
        <form id="forgotKeyForm" action="forgot" method="get">
            <!-- Add fields for key generation if needed -->
            <button class="btn btn-warning">Quên Key</button>
        </form>

        <!-- Display private key here -->

    </div>
    <div id="privateKeyContainer" style="display: none;">
        <!-- Display private key here -->
        <p style="color: red">Vui lòng nhớ Private Key và không cho người khác biết</p>
        <textarea id="privateKey" rows="4" cols="50"></textarea>
    </div>

    <script>
        function hideAllForms() {
            document.getElementById('keyForm').style.display = 'none';
            document.getElementById('baoCaoKeyForm').style.display = 'none';
            document.getElementById('taoKeyForm').style.display = 'none';
            document.getElementById('quenKeyForm').style.display = 'none';
        }

        function showKeyForm(option) {
            hideAllForms();
            document.getElementById('keyForm').style.display = 'block';
        }

        function reportKey() {
            hideAllForms();
            document.getElementById('baoCaoKeyForm').style.display = 'block';
        }

        function generateKey() {
            hideAllForms();
            document.getElementById('taoKeyForm').style.display = 'block';
        }
        function forgotKey() {
            hideAllForms();
            document.getElementById('quenKeyForm').style.display = 'block';
        }

        document.getElementById("createKeyForm").addEventListener("submit", function(event) {
            event.preventDefault();

            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/KeyController", true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                    var response = xhr.responseText;

                    // Assuming the response is just the private key string
                    var privateKeyTextarea = document.getElementById("privateKey");
                    privateKeyTextarea.value = response;
                    document.getElementById("privateKeyContainer").style.display = "block";
                }
            };
            xhr.send();
        });
        document.getElementById("forgotKeyForm").addEventListener("submit", function(event) {
            event.preventDefault();

            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/forgot", true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                    var response = xhr.responseText;

                    // Assuming the response is just the private key string
                    var privateKeyTextarea = document.getElementById("privateKey");
                    privateKeyTextarea.value = response;
                    document.getElementById("privateKeyContainer").style.display = "block";
                }
            };
            xhr.send();
        });
    </script>
</div>