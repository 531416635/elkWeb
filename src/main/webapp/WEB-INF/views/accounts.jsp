<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String productId = request.getParameter("ProductId");
	String jsonAddrs = (String) request.getAttribute("jsonAddrs");
%>
</head>
<body>
	<table>
		<tr>
			<th colspan="13">账户信息管理</th>
		</tr>
		<tr>
			<td>账户编号</td>
			<td>地址</td>
			<td>年龄</td>
			<td>薪水</td>
			<td>城市</td>
			<td>email</td>
			<td>雇主</td>
			<td>firstname</td>
			<td>性别</td>
			<td>级别</td>
			<td>lastname</td>
			<td>state</td>
			<td>操作</td>
		</tr>
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
				<td></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>