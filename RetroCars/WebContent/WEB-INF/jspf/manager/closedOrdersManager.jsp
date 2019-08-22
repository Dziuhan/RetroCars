<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="manager.">

		<c:choose>
			<c:when test="${empty orders}">
				<b>Not found closed orders</b>
			</c:when>
			<c:otherwise>
				<table>
					<tr>
						<th><fmt:message key="Login" /></th>
						<th><fmt:message key="Car" /></th>
						<th><fmt:message key="Begin" /></th>
						<th><fmt:message key="End" /></th>
						<th><fmt:message key="Driver" /></th>
						<th><fmt:message key="PriceOrder" /></th>
						<th><fmt:message key="PriceCrush" /></th>
						<th><fmt:message key="State" /></th>
					</tr>
					<c:forEach items="${orders}" var="order">
						<tr>
							<td>${order.login}</td>
							<td>${order.producer}${order.make} ${order.yearCar}</td>
							<td>${order.startRent}</td>
							<td>${order.finishRent}</td>
							<td>${order.driver}</td>
							<td>${order.priceTotal}</td>
							<td>${order.priceCrush}</td>
							<td>${order.state}</td>
						</tr>
					</c:forEach>
				</table>

			</c:otherwise>
		</c:choose>
	</fmt:bundle>
</body>
</html>