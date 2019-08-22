<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>Admin</title>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/headerMenu.jspf"%>
	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="admin.">
		<div class="center">


			<form action="AdminController" method="get" class="inline">
				<input type="hidden" name="commandAdmin" value="New car"> <input
					type="submit" value="<fmt:message key="NewCar"/>">
			</form>

			<form action="AdminController" method="get" class="inline">
				<input type="hidden" name="commandAdmin" value="All cars"> <input
					type="submit" value="<fmt:message key="AllCars"/>">
			</form>



			<form action="AdminController" method="get" class="inline">
				<input type="hidden" name="commandAdmin" value="Search user">
				<fmt:message key="SearchUser" />
				: <input type="text" name="loginUserSearchAdmin" value="">
			</form>
			<form action="AdminController" method="get" class="inline">
				<input type="hidden" name="commandAdmin" value="All users">
				<input type="submit" value="<fmt:message key="AllUsers"/>">
			</form>


			<a href="AdminController?commandAdmin=Task">Task</a>

		</div>
		<hr>

		<c:choose>
			<c:when test="${commandAdmin=='Task'}">
				<b>Count: ${taskCount} </b>
				<div>Logins:</div>
				<c:forEach items="${taskLogins}" var="login">
					<b> ${login} </b>
				</c:forEach>

			</c:when>
			<c:when test="${commandAdmin=='New car' or commandAdmin=='Edit car'}">
				<jsp:include page="/WEB-INF/jspf/admin/newEditCarAdmin.jsp" />
			</c:when>
			<c:when test="${commandAdmin=='All cars'}">
				<jsp:include page="/WEB-INF/jspf/admin/allCarAdmin.jsp" />
			</c:when>
			<c:when test="${commandAdmin=='All users'}">
				<jsp:include page="/WEB-INF/jspf/admin/allUserAdmin.jsp" />
			</c:when>

			<c:when test="${commandAdmin=='Search user'}">
				<jsp:include page="/WEB-INF/jspf/admin/allUserAdmin.jsp" />
			</c:when>
			<c:when test="${commandAdmin=='Edit user'}">
				<jsp:include page="/WEB-INF/jspf/admin/editUserAdmin.jsp" />
			</c:when>

			<c:otherwise>
				<jsp:include page="/WEB-INF/jspf/admin/allCarAdmin.jsp" />
			</c:otherwise>

		</c:choose>
	</fmt:bundle>

</body>



</html>