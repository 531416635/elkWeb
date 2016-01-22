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

	function del(id){
		$.ajax({
			dataType : "text",
			async : false,
			type : "POST",
			url : "delAccount",
			data:{id:id},
			
            success: function(data){
            	var str=data;
            	if(str=="true"){
            		 location.reload();
            	}else if(str=="false"){
            		alert("str==false");
            		alert("删除失败");
            	}else{
            		alert("发生未知错误");
            	}
		 }
        });
	}
	$(function(){
		$("#ascend").click(function(){
			sort("ascend");
		});
	   	$("#descend").click(function(){
	   		sort("descend");
		});
	});
	function sort(name){
		var input1Value=$("#input1").val().trim();
		
		if(input1Value==""){
			alert("请输入合理的有效值");
		}else{
		
		$.ajax({
			dataType : "text",
			async : false,
			type : "POST",
			url : "sortAccounts",
			data:{name:name,input:input1Value},
            success: function(data){
            	var str=eval('('+data+')');
            	var result="";
            	for ( var i in str) {
            		var str1=eval('('+str[i]+')')
            	 result+=" <tr>"
					+"<td>"+str1.account_number+"</td>"
					+"<td>"+str1.address+"</td>"
					+"<td>"+str1.age+"</td>"
					+"<td>"+str1.balance+"</td>"
					+"<td>"+str1.city+"</td>"
					+"<td>"+str1.email+"</td>"
					+"<td>"+str1.employer+"</td>"
					+"<td>"+str1.firstname+"</td>"
					+"<td>"+str1.gender+"</td>"
					+"<td>"+str1.grade+"</td>"
					+"<td>"+str1.lastname+"</td>"
					+"<td>"+str1.state+"</td>"
					+"<td><button id='del"+str1.account_number +"'"
					+"onclick='del("+str1.account_number +")'>删除</button></td>"
					+"</tr>"
				}
            	$("#tbody").html(result);
		 }
        });
	}}
</script>
</head>
<body>
	<div>
		<h1 style="text-align: center;">账户信息管理--权值聚合函数（metrics
			aggregation）</h1>
	</div>
	<div>
		默认排序：
		<button onclick="window.location.href='/elkWeb/accounts/getAccounts'">无序</button>
	</div>
	<div>
		输入字段：<input type="text" id="input1" />
		<button id="ascend">升序</button>
		<button id="descend">降序</button>
	</div>
	<div>
		<table style="text-align: center;">
			<tr>
				<td bgcolor="red">本页面内</td>
				<td width="150px">年龄</td>
				<td width="150px">薪水</td>
				<td width="150px">级别</td>
			</tr>
			<tr>
				<td>最大值</td>
				<td>${maxList.ageMax }</td>
				<td>${maxList.balanceMax }</td>
				<td>${maxList.gradeMax }</td>
			</tr>
			<tr>
				<td>最小值</td>
				<td>${minList.ageMin }</td>
				<td>${minList.balanceMin }</td>
				<td>${minList.gradeMin }</td>
			</tr>
			<tr>
				<td>平均值</td>
				<td>${avgList.ageAvg }</td>
				<td>${avgList.balanceAvg }</td>
				<td>${avgList.gradeAvg }</td>
			</tr>
			<tr>
				<td>总个数</td>
				<td>${countList.ageCount }</td>
				<td>${countList.balanceCount }</td>
				<td>${countList.gradeCount }</td>
			</tr>
			<tr>
				<td>总和</td>
				<td>${sumList.ageSum }</td>
				<td>${sumList.balanceSum }</td>
				<td>${sumList.gradeSum }</td>
			</tr>
		</table>
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
			<th>操作</th>
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
					<td><button id="del${account.account_number }"
							onclick="del(${account.account_number })">删除</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>