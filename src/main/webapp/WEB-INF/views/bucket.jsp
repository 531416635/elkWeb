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
		$("#filterbutton").click(function() {
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
					alert("匹配的文档数量：" + data);
				}
			});
		});

		$("#filtersbutton").click(
				function() {
					var list = "";
					var field = $(".filtersFieldSpan").text().split(" ");
					var field1 = $(".filtersTypeSpan").text().split(" ");
					var field2 = $(".filterstextSpan").text().split(" ");
					for (var e = 0; e < field.length - 1; e++) {
						if (e < field.length - 2) {
							list += "{\"field\":\"" + field[e]
									+ "\",\"type\":\"" + field1[e]
									+ "\",\"text\":\"" + field2[e] + "\"}="
						} else {
							list += "{\"field\":\"" + field[e]
									+ "\",\"type\":\"" + field1[e]
									+ "\",\"text\":\"" + field2[e] + "\"}"
						}

					}
					list += "";
					$.ajax({
						dataType : "text",
						async : false,
						type : "POST",
						url : "filtersAccount",
						data : {
							list : list,
						},
						success : function(data) {
							var str = eval("(" + data + ")");
							for ( var item in str) {
								var tr = Number(item) + 1;
								$("#div" + tr).append(
										"<span style='color:red;'>得到的文档数："
												+ str[item] + "</span>");
							}
						}
					});
				})
	})
	var i = 0;
	function add() {
		i = i + 1;
		var field = $("#filtersField").val();
		var fieldValue = $("#filtersField option:selected").text();
		var text = $("#filterstext").val();
		var type = $("#filtersType").val();
		var str = "<div id='div"+i+"'><span class='filtersFieldSpan' style='display:none'>"
				+ field
				+ " </span>字段：<span>"
				+ fieldValue
				+ "</span>"
				+ " 类型：<span class='filtersTypeSpan'>"
				+ type
				+ " </span> 信息：<span class='filterstextSpan'>"
				+ text
				+ " </span><button id='prep' onclick='prep("
				+ i
				+ ")'>-</button></div>";
		$("#filters").append(str);
		$("#filterstext").val("");
	}
	function prep(t) {
		$("#div" + t).remove();
	}

	function missingAggs() {
		var str = $("#Missingselect").val().trim();
		var str1 = $("#Missingselect option:selected").text().trim();
		$.ajax({
			dataType : "text",
			async : false,
			type : "POST",
			url : "missingAccount",
			data : {
				str : str
			},
			success : function(data) {
				alert("查询【" + str1 + "】的missing文档数为：" + data);
			}

		});
	}

	function netseAggs() {
		$.ajax({
			dataType : "text",
			async : false,
			type : "POST",
			url : "nestedAccount",
			success : function(data) {
				alert("查询嵌套结果为：" + data);
			}

		});
	}
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

	<div>
		<span style="color: red;">Filters aggs:</span>
		<button id="filtersbutton"
			style="width: 200px; background-color: #25f52e;">查询</button>
		<br> 选择字段:<select id="filtersField">
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
		</select> 类型：<select id="filtersType">
			<option value="term">精确</option>
			<option value="fuzzy">模糊</option>
		</select>输入查询信息：<input id="filterstext" type="text">
		<button id="add" onclick="add()">+</button>
		<div id="filters"></div>
	</div>
	<div>
		<span style="color: red;">Missing aggs:</span><br> 选择字段:<select
			id="Missingselect">
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
		</select>
		<button onclick="missingAggs();">查询</button>
	</div>
	<div>
		<span style="color: red;">netse aggs:</span>
		<button onclick="netseAggs();">查询</button>
		<br /> <span>问题描述：在es中有一个product的产品类型，它里面有一个嵌套的resellers经销商字段，一个商品会有多个经销商，使用netse
			aggs可以查询对应的产品的经销商数量</span>

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