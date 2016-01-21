<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户操作</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String productId = request.getParameter("ProductId");
	String jsonAddrs = (String) request.getAttribute("jsonAddrs");
%>
<script type="text/javascript"
	src="<%=path%>/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#filterbutton").click(
				function() {
					var field = $("#fieldselect").val().trim();
					var type = $("#typeselect").val().trim();
					var inputext = $("#filtertext").val().trim();
					$.ajax({
						dataType : "text",
						async : false,
						type : "POST",
						url : "filterAccount",
						data : {
							field : field,
							type : type,
							inputext : inputext
						},
						success : function(data) {
							var str = eval('(' + data + ')');
							var result = "";
							for ( var i in str) {
								var str1 = eval('(' + str[i] + ')')
								result += " <tr>" + "<td>"
										+ str1.account_number + "</td>"
										+ "<td>" + str1.address + "</td>"
										+ "<td>" + str1.age + "</td>" + "<td>"
										+ str1.balance + "</td>" + "<td>"
										+ str1.city + "</td>" + "<td>"
										+ str1.email + "</td>" + "<td>"
										+ str1.employer + "</td>" + "<td>"
										+ str1.firstname + "</td>" + "<td>"
										+ str1.gender + "</td>" + "<td>"
										+ str1.grade + "</td>" + "<td>"
										+ str1.lastname + "</td>" + "<td>"
										+ str1.state + "</td>"
										+ "<td><button id='del"
										+ str1.account_number + "'"
										+ "onclick='del(" + str1.account_number
										+ ")'>删除</button></td>" + "</tr>"
							}
							$("#tbody").html(result);
						},
						error : function(e) {
							alert("hello jquery");
						}
					});
				})
	})
</script>
</head>
<body>
	<div>
		<h1 style="text-align: center;">账户信息管理--Bucket聚合函数（Bucket
			aggregation）</h1>
	</div>
	<div>
		<b>Global Aggregation </b>处理地理上的经纬度的聚合函数，自己查看PAI
	</div>

	<div>
		<span style="color: red;">Filter aggs:</span><br> 选择字段:<select
			id="fieldselect">
			<option value="account_number">账户编号</option>
			<option value="address">地址</option>
			<option value="age">年龄</option>
			<option value="balance">薪水</option>
			<option value="city">城市</option>
			<option value="email">email</option>
			<option value="employer">雇主</option>
			<option value="firstname">firstname</option>
			<option value="gender">性别</option>
			<option value="grade">年龄</option>
			<option value="lastname">lastname</option>
			<option value="state">state</option>
		</select> 类型：<select id="typeselect">
			<option value="term">精确</option>
			<option value="fuzzy">模糊</option>
		</select>输入查询信息：<input id="filtertext" type="text">
		<button id="filterbutton">查询</button>
	</div>
	<div>&nbsp;</div>
	<table>
		<tr>
			<th>账户编号</th>
			<th>地址</th>
			<th>年龄</th>
			<th>薪水</th>
			<th>城市</th>
			<th>email</th>
			<th>雇主</th>
			<th>firstname</th>
			<th>性别</th>
			<th>级别</th>
			<th>lastname</th>
			<th>state</th>

		</tr>
		<tbody id="tbody">
			<c:forEach items="${accountList }" var="account">
				<tr>
					<td>${account.account_number }</td>
					<td>${account.address }</td>
					<td>${account.age }</td>
					<td>${account.balance }</td>
					<td>${account.city }</td>
					<td>${account.email }</td>
					<td>${account.employer }</td>
					<td>${account.firstname }</td>
					<td>${account.gender }</td>
					<td>${account.grade }</td>
					<td>${account.lastname }</td>
					<td>${account.state }</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>