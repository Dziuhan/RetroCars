<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/u.tld" prefix="u"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RetroCars</title>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/headerMenu.jspf"%>
	<fmt:bundle
		basename="ua.RetroCars.web.ResourceBundle.Page"
		prefix="tableCars.">
		<table>
			<tr>
				<th></th>
				<td id="sorterCars">
					<form name="sorter" action="/RetroCars/ClientController"
						method="get">
						<fmt:message key="SortBy" />
						<select name="sorterCar" onchange="sorter.submit()">

							<option><c:if test="${not empty sortCarBy}">
									<fmt:message key="${sortCarBy}" />
								</c:if>
							</option>
							<optgroup label="Choose sort:"></optgroup>
							<option value="byProducer"><fmt:message key="byProducer" /></option>
							<option value="byProducerReverse"><fmt:message
									key="byProducerReverse" /></option>
							<option value="byPrice"><fmt:message key="byPrice" /></option>
							<option value="byPriceReverse"><fmt:message
									key="byPriceReverse" /></option>
							<option value="byRank"><fmt:message key="byRank" /></option>
							<option value="byRankReverse"><fmt:message
									key="byRankReverse" /></option>
						</select> <input type="hidden" name="command" value="Sort"> <a
							href="ClientController?command=View&viewFormat=table"><fmt:message
								key="Table" /> </a> <a
							href="ClientController?command=View&viewFormat=list"><fmt:message
								key="List" /> </a> <a
							href="ClientController?command=View&viewFormat=Tile"><fmt:message
								key="Tile" /> </a>
					</form>
				</td>
			</tr>

			<tr>
				<td id="filterCars">
					<form action="ClientController" class="center">
						<u:filter-cars></u:filter-cars>
						<input type="hidden" name="command" value="Filter"> <input
							type="submit" value="<fmt:message key="Filter"/>">
					</form>

				</td>
				<td id="tableCars"><u:carsPageView /></td>
			</tr>
		</table>
	</fmt:bundle>
	<hr>
</body>
</html>