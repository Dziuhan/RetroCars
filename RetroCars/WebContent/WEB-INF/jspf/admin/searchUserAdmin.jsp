<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Admin Changer</title>
</head>
<body>
	<c:choose>
		<c:when test="${user.ban==true}">
			<c:set value="checked" var="check" />
		</c:when>
		<c:when test=" ${user.ban==false}">
			<c:set value="" var="check" />
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${empty usersFindByLogin}">
			<b> Not found user </b>
		</c:when>
		<c:otherwise>
			<form action="AdminController" method="post">
				<table>
					<tr>
						<th>Id</th>
						<th>Login</th>
						<th>Role</th>
						<th>Ban</th>
					</tr>
					<c:forEach items="${usersFindByLogin}" var="findUser">

						<tr>
							<td>${findUser.id}</td>
							<td>${findUser.login}</td>

							<td><select name="roleUserAdmin">
									<option>${findUser.role}</option>
									<optgroup label="Change role"></optgroup>
									<option value="client">client</option>
									<option value="manager">manager</option>
									<option value="admin">admin</option>
							</select></td>
							<td><input type="checkbox" name="banUserAdmin" ${check}></td>
							<td><input type="submit" name="commandAdmin"
								value="Save user"></td>
						</tr>

					</c:forEach>


				</table>
			</form>
		</c:otherwise>

	</c:choose>


</body>
</html>
