<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Car order</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/all.css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/headerMenu.jspf"%>
	<fmt:setLocale value="${local}" />
	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="orderClient.">


		<div
			style="background: url('/RetroCars/picture/l_picture/${chooseCar.imageLocAdress}') no-repeat 100%;background-size: 100%;width:avto; height: 100%;">
			<div id="order">
				<form action="ClientController" method="post">
					<div>${chooseCar.producer}${chooseCar.make}</div>
					<div>
						<fmt:message key="Rank" />
						: ${chooseCar.rank}
					</div>
					<div>
						<fmt:message key="Price1Day" />
						: ${chooseCar.price}
					</div>
					<div>
						<c:if test="${orderDriver=='true'}">
							<c:set var="checked" value="checked" />
							<c:set var="driverOrder" value="on" />
						</c:if>
						<fmt:message key="Driver" />
						<input type="checkbox" disabled="disabled" ${checked}> (
						<fmt:message key="Price1Day" />
						) 10 <input type="hidden" name="driverOrder"
							value="${driverOrder}"> <input type="hidden"
							name="commandValue" value="driver">
					</div>

					<fmt:message key="From" />
					<input type="date" name="startRentOrder" readonly="readonly"
						value="${startRentOrder}">
					<fmt:message key="Before" />
					<input type="date" name="finishRentOrder" readonly="readonly"
						value="${finishRentOrder}">
					<div>
						<fmt:message key="TotalPrice" />
						:${priceTotal}
					</div>

					<div></div>
					<input type="hidden" name="command" value="agreeOrder"> <input
						type="hidden" name="idCar" value="${chooseCar.id}"> <input
						type="submit" value="<fmt:message key="Agree"/>">


				</form>
				<form action="ClientController" method="get">
					<input type="hidden" name="command" value="noAgreeOrder"> <input
						type="hidden" name="idCar" value="${chooseCar.id}"> <input
						type="submit" value="<fmt:message key="Back"/>">
				</form>
			</div>
		</div>
	</fmt:bundle>
</body>
</html>