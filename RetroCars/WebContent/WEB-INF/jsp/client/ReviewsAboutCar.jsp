<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cars order</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/all.css" />
</head>
<body>

	<fmt:setLocale value="${local}" />
	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="reviewsCar.">
		<%@ include file="/WEB-INF/jspf/headerMenu.jspf"%>
		<form action="ClientController" class="center">
			<input type="hidden" name="idByChoose" value="${idCar}"> <input
				type="hidden" name="command" value="Choose"> <input
				type="submit" value="<fmt:message key="Choose"/>">
		</form>
		<form action="ClientController" method="post" class="center">
			<textarea cols="75" rows="8" maxlength="499"
				name="reviewAboutCarFromUser"></textarea>
			<input type="hidden" name="command" value="createReviewAboutCar">
			<input type="hidden" name="idCar" value="${idCar}">
			<div>
				<input type="submit" value="<fmt:message key="SendReview"/>">
			</div>
		</form>


		<c:forEach items="${reviewsAboutCar}" var="Review">
			<div>
				<fieldset>
					<legend>${Review.login} ${Review.dateReview} </legend>
					${Review.review}
				</fieldset>
			</div>
		</c:forEach>
	</fmt:bundle>
</body>
</html>