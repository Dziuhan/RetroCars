<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Admin Changer</title>
</head>
<body>
	<c:choose>
		<c:when test="${userFindByLogin.ban==true}">
			<c:set value="checked" var="check" />
		</c:when>
		<c:when test=" ${userFindByLogin.ban==false}">
			<c:set value="" var="check" />
		</c:when>
	</c:choose>

	<form action="AdminController" method="post">
		<table>
			<tr>
				<th>Id</th>
				<th>Login</th>
				<th>Role</th>
				<th>Ban</th>
			</tr>

			<tr>
				<td>${userFindByLogin.id}</td>
				<td>${userFindByLogin.login}</td>

				<td><select name="roleUserAdmin">
						<option>${userFindByLogin.role}</option>
						<optgroup label="Change role"></optgroup>
						<option value="client">client</option>
						<option value="manager">manager</option>
						<option value="admin">admin</option>
				</select></td>
				<td><input type="checkbox" name="banUserAdmin" ${check}></td>
				<td><input type="hidden" name="loginUserAdmin"
					value="${userFindByLogin.login}"> <input type="submit"
					name="commandAdmin" value="Save user"></td>

			</tr>
		</table>
	</form>
</body>
</html>