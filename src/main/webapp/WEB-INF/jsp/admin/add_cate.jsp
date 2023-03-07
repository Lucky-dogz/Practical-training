<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>添加分类</title>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link href="${ pageContext.request.contextPath }/css/managestyle.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	<div class="top">
		<h1>&nbsp;&nbsp;EasyMall商城管理后台</h1>
	</div>	
	<div class="content">
		<div class="left">			
			<%@ include file = "_left.jsp" %>
		</div>
	<div class="right">	
	<div class="addprod">
	<jsp:useBean id="mycategory"  class="easymall.pojo.MyCategory" scope="request" ></jsp:useBean>
	<form:form modelAttribute="mycategory" onsubmit="return formobj.checkForm();" method="POST" 
	  enctype="multipart/form-data" action="${ pageContext.request.contextPath }/admin/catesave">			
		<fieldset>
		<legend>添加分类</legend>			
		<p>
            <label>分类名称:</label>
            <form:input path="name"/>
        </p><p>
            <label>分类介绍：</label>
            <form:input path="description"/>
        </p><p id="buttons">
            <input id="submit" type="submit" value="添加">
        </p>
    	</fieldset>
    	<!-- 取出所有验证错误 -->
    	<form:errors path="*"/>
	</form:form>
	</div>
	</div><!-- right结束 -->
	</div><!-- content结束 -->		
	
	
	</body>
</html>