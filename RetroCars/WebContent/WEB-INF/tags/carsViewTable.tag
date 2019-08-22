<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${local}" />
<fmt:bundle
	basename="ua.RetroCars.web.ResourceBundle.Page"
	prefix="tableCars.">
	<table>
		<tr>
			<th><fmt:message key="Foto" /></th>
			<th><fmt:message key="Producer" /></th>
			<th><fmt:message key="Make" /></th>
			<th><fmt:message key="Year" /></th>
			<th><fmt:message key="Rank" /></th>
			<th><fmt:message key="Price" /></th>
		</tr>
		<c:forEach items="${pageList}" var="car">
			<tr class="color">
				<td><img alt=" No foto"
					src="picture/s_picture/${car.imageLocAdress}"></td>
				<td>${car.producer}</td>
				<td>${car.make}</td>
				<td>${car.year}</td>
				<td>${car.rank}</td>
				<td>${car.price}</td>
				<td><form action="ClientController" method="get">
						<input type="hidden" name="idByChoose" value="${car.id}">
						<input type="hidden" name="command" value="Choose"> <input
							type="submit" value="<fmt:message key="Choose"/>">
					</form> <a href="ClientController?command=ReviewsCar&carId=${car.id}"><fmt:message
							key="Reviews" /></a></td>

			</tr>
		</c:forEach>
	</table>
	<c:if test="${empty pageList}">
		<b class="center"><fmt:message key="NotFoundCars" /></b>
	</c:if>
</fmt:bundle>