<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>图表</title>
<link href="${ pageContext.request.contextPath }/css/managestyle.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${ pageContext.request.contextPath }/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="${ pageContext.request.contextPath }/js/echarts.js"></script>
<style type="text/css">
.chartStyle {
	margin-left: 30px;
	margin-top: 30px;
	margin-bottom: 50px;
}
</style>
</head>
<body>
	<div class="top">
		<h1>商品管理</h1>
	</div>
	<div class="content">
		<div class="left">
			<%@ include file="_left.jsp"%>
		</div>
		<div class="content">
			<div class="right">
				<div class=chartStyle>
					<!-- 显示Echarts图表 -->
					<div style="height: 410px; min-height: 100px; margin: 0 auto;"
						id="pie"></div>
					<div style="height: 410px; min-height: 100px; margin: 0 auto;"
						id="bar"></div>
					<!-- <div style="height:410px;min-height:100px;margin:0 auto;" id="pie2"></div> -->
					<!-- 饼状图 -->
					<script>
						var chart = echarts
								.init(document.getElementById('pie'));
						chart.setOption({
							aria : {
								show : true
							},
							title : {
								text : '各类商品所占比例',
								x : 'center'
							},
							tooltip : {
								trigger : 'item',
								formatter : "{a} <br/>{b} : {c} ({d}%)"
							},
							legend : {
								orient : 'vertical',
								left : 'left',
								data : [ '手机数码', '日用百货', '家用电器', '图书杂志',
										'服装服饰', '床上用品', '汽车用品' ]
							},
							series : [ {
								name : '商品类别',
								type : 'pie',
								radius : '55%',
								center : [ '50%', '60%' ],
								data : [

								],
								itemStyle : {
									emphasis : {
										shadowBlur : 10,
										shadowOffsetX : 0,
										shadowColor : 'rgba(0, 0, 0, 0.5)'
									}
								}
							} ]

						});

						var nums = []; //销量数组（实际用来盛放Y坐标值）
						$.ajax({
							url : "getPie",
							cache : false,
							dataType : "JSON",
							success : function(data) {
								if (data) {
									data = data.substring(1, data.length - 1); //因为传过来的数据是string类型[13, 9, 2, 1]
									data = data.split(", "); //所以要将数据转换成数组类型，方便赋值
									for (var i = 0; i < data.length; i++) {
										nums.push(data[i]); //挨个取出并填入数组

									}
								}
							}
						})
						chart.setOption({
							series : [ {
								data : [ {
									value : 13,
									name : '手机数码'
								}, {
									value : 9,
									name : '日用百货'
								}, {
									value : 15,
									name : '家用电器'
								}, {
									value : 7,
									name : '图书杂志'
								}, {
									value : 15,
									name : '服装服饰'
								}, {
									value : 5,
									name : '床上用品'
								}, {
									value : 1,
									name : '汽车用品'
								}, ]
							} ]
						});
					</script>
					<!-- 柱状图 -->
					<script type="text/javascript">
						// 基于准备好的dom，初始化echarts实例
						var chart2 = echarts.init(document
								.getElementById('bar'));
						chart2.setOption({
							aria : {
								show : true
							},
							title : {
								text : '各类商品的数量',
								x : 'center'
							},
							legend : {
								orient : 'vertical',
								left : 'left',
								data : [ '数量' ]
							},
							xAxis : {
								data : [ '手机数码', '日用百货', '家用电器', '图书杂志',
										'服装服饰', '床上用品', '汽车用品' ]
							},
							yAxis : {},
							series : [ {
								name : '数量',
								type : 'bar',
								data : []
							} ]
						});
						// var names=[];    //类别数组（实际用来盛放X轴坐标值）
						var nums = []; //销量数组（实际用来盛放Y坐标值）
						$.ajax({
							url : "getPie",
							cache : false,
							dataType : "JSON",
							success : function(data) {
								if (data) {

									for (var i = 0; i < data.length; i++) {
										nums.push(data[i]); //挨个取出销量并填入销量数组
									}
									;
								}
							}
						})
						chart2.setOption({
							series : [ {
								itemStyle : {
									normal : {
										color : function(params) {
											//注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
											var colorList = [ '#c23531',
													'#2f4554', '#61a0a8',
													'#d48265', '#91c7ae',
													'#749f83', '#ca8622' ];
											return colorList[params.dataIndex]
										}
									}
								},
								name : '数量',
								type : 'bar',
								data : [ {
									value : 13,
									name : '手机数码'
								}, {
									value : 9,
									name : '日用百货'
								}, {
									value : 18,
									name : '家用电器'
								}, {
									value : 7,
									name : '图书杂志'
								}, {
									value : 12,
									name : '服装服饰'
								}, {
									value : 5,
									name : '床上用品'
								}, {
									value : 1,
									name : '汽车用品'
								}, ]
							} ]
						})
					</script>

					<!-- 饼状图 -->
					<script>
						var chart = echarts.init(document
								.getElementById('pie2'));
						chart.setOption({
							aria : {
								show : true
							},
							title : {
								text : '商品上下架所占比例',
								x : 'center'
							},
							tooltip : {
								trigger : 'item',
								formatter : "{a} <br/>{b} : {c} ({d}%)"
							},
							legend : {
								orient : 'vertical',
								left : 'left',
								data : [ '上架', '下架' ]
							},
							series : [ {
								name : '商品状态',
								type : 'pie',
								radius : '55%',
								center : [ '50%', '60%' ],
								data : [

								],
								itemStyle : {
									emphasis : {
										shadowBlur : 10,
										shadowOffsetX : 0,
										shadowColor : 'rgba(0, 0, 0, 0.5)'
									}
								}
							} ]

						});

						var nums = [];
						$.ajax({
							url : "getSale",
							cache : false,
							dataType : "JSON",
							success : function(data) {
								if (data) {
									data = data.substring(1, data.length - 1); //因为传过来的数据是string类型[13, 9, 2, 1]
									data = data.split(", "); //所以要将数据转换成数组类型，方便赋值
									for (var i = 0; i < data.length; i++) {
										nums.push(data[i]); //挨个取出并填入数组

									}
								}
							}
						})
						chart.setOption({
							series : [ {
								data : [ {
									value : 27,
									name : '上架'
								}, {
									value : 2,
									name : '下架'
								}, ]
							} ]
						});
					</script>
				</div>
			</div>
		</div>
</body>
</html>