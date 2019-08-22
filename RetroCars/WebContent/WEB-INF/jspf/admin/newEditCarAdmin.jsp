<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>Admin Changer</title>
</head>
<body>
	<fmt:setLocale value="${local}" />
	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="admin.">

		<form action="AdminController" method="post">
			<input type="hidden" name="id" value="${editCar.id}">

			<fieldset>
				<legend>
					<fmt:message key="Image" />
				</legend>
				<input name="imageLocAdress" value="${editCar.imageLocAdress}" />
			</fieldset>

			<fieldset>
				<legend>
					<fmt:message key="Producer" />
				</legend>
				<input name="producer" required="required"
					value="${editCar.producer}" />
			</fieldset>
			<fieldset>
				<legend>
					<fmt:message key="Make" />
				</legend>
				<input name=make required="required" value="${editCar.make}" />
			</fieldset>
			<fieldset>
				<legend>
					<fmt:message key="Rank" />
				</legend>
				<input name="rank" required="required" value="${editCar.rank}" />
			</fieldset>
			<fieldset>
				<legend>
					<fmt:message key="Year" />
				</legend>
				<input type="number" required="required" name="year"
					value="${editCar.year}" />
			</fieldset>
			<fieldset>
				<legend>
					<fmt:message key="Price" />
				</legend>
				<input name="price" required="required" value="${editCar.price}" />
			</fieldset>

			<c:choose>
				<c:when test="${commandAdmin =='New car'}">
					<input type="hidden" name="commandAdmin" value="Create new car">
					<input type="submit" value="<fmt:message key="CreateNewCar"/>">
				</c:when>
				<c:when test="${commandAdmin =='Edit car'}">
					<input type="hidden" name="commandAdmin" value="Save car">
					<input type="submit" name="commandAdmin"
						value="<fmt:message key="SaveCar"/>">
				</c:when>
			</c:choose>
		</form>
		<c:if test="${commandAdmin =='Edit car'}">
			<form action="AdminController" method="post">
				<input type="hidden" name="id" value="${editCar.id}"> <input
					type="hidden" name="commandAdmin" value="Delete car"> <input
					type="submit" name="commandAdmin"
					value="<fmt:message key="DeleteCar"/>">
			</form>
		</c:if>
	</fmt:bundle>
</body>
</html>