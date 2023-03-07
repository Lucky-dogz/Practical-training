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
	<jsp:useBean id="products"  class="easymall.po.Products" scope="request" ></jsp:useBean>
	<form:form modelAttribute="products" onsubmit="return formobj.checkForm();"
	enctype="multipart/form-data" method="POST"  action="${ pageContext.request.contextPath }/admin/update">		
	<input type="hidden" name="id" value="${param.id}">
	<fieldset>
		<legend>修改商品（暂不支持修改图片）</legend>			
		<p>
            <label>商品名:</label>
            <input type="text" name="name" value="${product.name }"/>
        </p><p>
            <label>商品价格:</label>
            <input type="text" name="price" value="${product.price }"/>
        </p><p>
            <label>商品类别:</label>
            <select name="category">
				<c:forEach items="${categories}" var="c">
					<option value="${c }">${c }</option>
				</c:forEach>
			</select>
        </p><p>
            <label>库存:</label>
            <input type="text" name="pnum" value="${product.pnum }"/>
        </p><p>
            <label>商品描述:</label>
            <input type="text" name="description" value="${product.description }"/>
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