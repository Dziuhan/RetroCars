<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${local}" />
<fmt:bundle
	basename="ua.RetroCars.web.ResourceBundle.Page"
	prefix="tableCars.">
	<table>
		<c:forEach items="${pageList}" var="car">
			<tr class="color">
				<td><img alt=" No foto"
					src="/RetroCars/picture/s_picture/${car.imageLocAdress}"></td>
				<td>${car.producer}${car.make} / ${car.year} / ${car.rank} /
					${car.price}</td>
				<td>
					<form action="ClientController" method="get">
						<input type="hidden" name="idByChoose" value="${car.id}">
						<input type="hidden" name="command" value="Choose"> <input
							type="submit" value="<fmt:message key="Choose"/>">
					</form> <a href="ClientController?command=ReviewsCar&carId=${car.id}"><fmt:message
							key="Reviews" /></a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${empty pageList}">
		<b class="center"><fmt:message key="NotFoundCars" /></b>
	</c:if>
</fmt:bundle>