<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Client cabinet</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/all.css" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/headerMenu.jspf"%>
	<fmt:setLocale value="${local}" />
	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="cabinetClient.">

		<table>
			<tr>
				<th><fmt:message key="Car" /></th>
				<th><fmt:message key="Begin" /></th>
				<th><fmt:message key="End" /></th>
				<th><fmt:message key="Price" /></th>
				<th><fmt:message key="State" /></th>
			</tr>
			<c:forEach items="${ordersClient}" var="order">
				<tr>
					<td>${order.producer}${order.make} ${order.yearCar}</td>
					<td>${order.startRent}</td>
					<td>${order.finishRent}</td>
					<td><c:choose>
							<c:when test="${order.state=='waiting for payment'}">
						${order.priceTotal}				
					</c:when>
							<c:when test="${order.state=='pay crush car'}">
						${order.priceCrush}
					</c:when>
						</c:choose></td>
					<td>${order.state}</td>
					<td><c:choose>
							<c:when test="${order.state=='waiting for payment'}">
								<form action="ClientController" method="post">
									<input type="hidden" name="idOrder" value="${order.id}">
									<input type="hidden" name="command" value="PayOrder"> <input
										type="submit" value="<fmt:message key="PayOrder"/>">
								</form>
							</c:when>
							<c:when test="${order.state=='pay crush car'}">
								<form action="ClientController" method="post">
									<input type="hidden" name="idOrder" value="${order.id}">
									<input type="hidden" name="command" value="PayCrushOrder">
									<input type="submit" value="<fmt:message key="PayCrush"/>">
								</form>
							</c:when>
							<c:otherwise>
						${order.state}
					</c:otherwise>
						</c:choose></td>

				</tr>
			</c:forEach>
		</table>
	</fmt:bundle>
</body>
</html>