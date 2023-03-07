<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>订单管理</title>
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
	
	<table width="799" height="40" border="1" cellpadding="0" cellspacing="0" bordercolor="#d8d8d8">
				<tr>
					<th width="247">订单id</th>
					<th width="276">地址</th>
					<th width="232">支付状态</th>
					<th width="232">发货状态</th>
					<th width="232">请求时间</th>
				</tr>
			
				<c:forEach items="${orders}" var="ord">
				<tr>
					<td>${ord.id }</td>
					<td>${ord.receiverinfo }</td>
					<td>
						<c:if test="${ord.paystate==0 }">未支付</c:if>
						<c:if test="${ord.paystate==1 }">已支付</c:if>
					</td>
					<td>
						<c:if test="${ord.paystate==0 }">请先支付</c:if>
						<c:if test="${ord.paystate==1 }">
							<c:if test="${ord.sendstate==0 }">未发货 |
							<a href="${ pageContext.request.contextPath }/admin/sendorder?id=${ord.id}">确认发货</a></c:if>
							<c:if test="${ord.sendstate==1 }">已发货</c:if>
						</c:if>
					</td>
					<td>${ord.ordertime }</td>
				</tr>				
				</c:forEach>
			</table>
	
	</div>		
	</body>	
</html>
