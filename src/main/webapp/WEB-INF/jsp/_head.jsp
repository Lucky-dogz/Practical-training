<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<link rel="stylesheet"
	href="${ pageContext.request.contextPath }/css/head.css" />
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="common_head">
	<div id="line1">
		<div id="content">
			<div class="dropdown">
				<span>我的</span>
				<svg t="1676773294462" class="icon" viewBox="0 0 1024 1024"
					version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2764"
					width="16" height="16">
					<path
						d="M917.284001 297.722752c-5.406127-5.437849-13.06762-8.396227-21.479197-8.396227-9.611915 0-18.953677 3.844561-25.713638 10.543124L511.980046 659.992589 153.873018 299.91672c-6.729262-6.745634-16.072047-10.619872-25.654286-10.619872-8.470929 0-16.131399 2.989077-21.598924 8.457626-12.301164 12.435217-11.32493 33.69031 2.192945 47.312562l376.764969 378.821815c6.758937 6.788613 15.860223 10.723226 25.052582 10.8143l3.425006 0c8.981559-0.301875 17.814738-4.205788 24.423249-10.8143l376.733247-378.852514C928.728658 331.382363 929.690566 310.113967 917.284001 297.722752"
						fill="#231F20" p-id="2765" data-spm-anchor-id="a313x.7781069.0.i0"
						class="selected"></path></svg>
				<div class="dropdown-content">
					<c:if test="${ empty sessionScope.user }">
						<a href="${ pageContext.request.contextPath }/index/login">登录</a>	 &nbsp;	
			 <a href="${ pageContext.request.contextPath }/index/regist">注册</a>
					</c:if>

					<c:if test="${ !(empty sessionScope.user) }">
		 	 	"${ sessionScope.user.username }~&nbsp;"
		 	<a href="${ pageContext.request.contextPath }/index/logout">退出</a>
					</c:if>
				</div>
			</div>


		</div>
	</div>
	<div id="line2">
		<img id="logo"
			src="${ pageContext.request.contextPath }/img/head/logo1.png" /> <span
			id="goto"> <a id="goto_order"
			href="${ pageContext.request.contextPath }/order/showorder">我的订单</a>
			<a id="goto_cart"
			href="${ pageContext.request.contextPath }/cart/showcart">我的购物车</a> <a
			id="goto_admin" href="${pageContext.request.contextPath}/admin/login">后台登录</a>&nbsp;&nbsp;&nbsp;
		</span> <input type="text" name="" /> <input type="button" value="搜 索" />
		<%-- <img id="erwm" src="${ pageContext.request.contextPath }/img/head/qr.jpg"/> --%>
	</div>
	<div id="line3">
		<div id="content">
			<ul>
				<li><a href="${ pageContext.request.contextPath }/index.jsp">首页</a></li>
				<li><a href="${ pageContext.request.contextPath }/prodlist">全部商品</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/手机数码">手机数码</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/日用百货">日用百货</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/家用电器">家用电器</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/图书杂志">图书杂志</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/服装服饰">服装服饰</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/床上用品">床上用品</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/汽车用品">汽车用品</a></li>
				<li><a
					href="${ pageContext.request.contextPath }/prodclass/户外运动">户外运动</a></li>
			</ul>
		</div>
	</div>
</div>