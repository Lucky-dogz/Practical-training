<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>分类管理</title>
		<meta charset="utf-8"/>
		<link href="${ pageContext.request.contextPath }/css/managestyle.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	<div class="top">
		<h1>分类管理</h1>
	</div>	
	<div class="content">
		<div class="left">			
			<%@ include file = "_left.jsp" %>
		</div>
	<div class="right">
	
			<a href="${pageContext.request.contextPath}/admin/addcate">添加分类</a>
	</div>
	
	<table width="799" height="40" border="1" cellpadding="0" cellspacing="0" bordercolor="#d8d8d8">
				<tr>
					<th width="247">分类名称</th>
					<th width="276">分类介绍</th>
					<th width="232">修改</th>
					<th width="232">删除</th>
				</tr>
			
				<c:forEach items="${categories}" var="cate">
				<tr>
					<td>${cate.name }</td>
					<td>${cate.description }</td>
					<td><a href="${pageContext.request.contextPath}/admin/changecate?name=${cate.name }">修改</a></td>
					<td><a href="${pageContext.request.contextPath}/admin/delcate?name=${cate.name }">删除</a></td>
				</tr>				
				</c:forEach>
			</table>
	
	</div>		
	</body>	
</html>