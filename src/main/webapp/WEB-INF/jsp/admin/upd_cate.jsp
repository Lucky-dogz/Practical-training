<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>添加商品</title>
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
	<jsp:useBean id="categories"  class="easymall.po.Category" scope="request" ></jsp:useBean>
	<form:form modelAttribute="categories" onsubmit="return formobj.checkForm();"
	enctype="multipart/form-data" method="POST"  action="${ pageContext.request.contextPath }/admin/change">		
	<input type="hidden" name="name" value="${param.name}">
	<fieldset>
		<legend>修改分类（仅修改描述）</legend>			
		<p>
            <label>分类介绍:</label>
            <input type="text" name="description" value="${category.description }"/>
        </p><p id="buttons">
            <input id="submit" type="submit" value="修改">
        </p>
    	</fieldset>
    	<!-- 取出所有验证错误 -->
    </form:form>
	</div>
	</div><!-- right结束 -->
	</div><!-- content结束 -->		
	
	
	</body>
</html>