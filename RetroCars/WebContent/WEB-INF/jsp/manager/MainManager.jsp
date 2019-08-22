<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manager</title>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/headerMenu.jspf"%>

	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="manager.">
		<div>
			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager" value="new orders">
				<input type="submit" value="<fmt:message key="NewOrders" />">
			</form>

			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager"
					value="waiting for payment"> <input type="submit"
					value="<fmt:message key="WaitingForPayment" />">
			</form>

			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager" value="paid orders">
				<input type="submit" value="<fmt:message key="PaidOrders"/>">
			</form>

			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager" value="rejected orders">
				<input type="submit" value="<fmt:message key="RejectedOrders"/>">
			</form>

			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager" value="crush orders">
				<input type="submit" value="<fmt:message key="CrushCarOrders"/>">
			</form>

			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager" value="close orders">
				<input type="submit" value="<fmt:message key="MayCloseOrders"/>">
			</form>

			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager" value="closed orders">
				<input type="submit" value="<fmt:message key="ClosedOrders"/>">
			</form>

			<form action="ManagerController" class="inline">
				<input type="hidden" name="commandManager" value="all orders">
				<input type="submit" value="<fmt:message key="AllOrders"/>">
			</form>
		</div>
		<c:choose>
			<c:when test="${commandManager=='new orders'}">
				<jsp:include page="/WEB-INF/jspf/manager/newOrdersManager.jsp" />
			</c:when>
			<c:when test="${commandManager=='waiting for payment'}">
				<jsp:include page="/WEB-INF/jspf/manager/waitingOrdersManager.jsp" />
			</c:when>
			<c:when test="${commandManager=='rejected orders'}">
				<jsp:include page="/WEB-INF/jspf/manager/rejectedOrdersManager.jsp" />
			</c:when>
			<c:when test="${commandManager=='paid orders'}">
				<jsp:include page="/WEB-INF/jspf/manager/paidOrdersManager.jsp" />
			</c:when>
			<c:when test="${commandManager=='crush orders'}">
				<jsp:include page="/WEB-INF/jspf/manager/crushCarOrdersManager.jsp" />
			</c:when>
			<c:when test="${commandManager=='close orders'}">
				<jsp:include page="/WEB-INF/jspf/manager/mayCloseOrdersManager.jsp" />
			</c:when>
			<c:when test="${commandManager=='closed orders'}">
				<jsp:include page="/WEB-INF/jspf/manager/closedOrdersManager.jsp" />
			</c:when>
			<c:otherwise>
				<jsp:include page="/WEB-INF/jspf/manager/allOrdersManager.jsp" />
			</c:otherwise>
		</c:choose>

	</fmt:bundle>
</body>
</html>