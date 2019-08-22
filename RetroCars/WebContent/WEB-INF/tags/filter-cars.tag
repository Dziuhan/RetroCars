<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/all.css" />
<fmt:setLocale value="${local}" />
<fmt:bundle
	basename="ua.RetroCars.web.ResourceBundle.Page"
	prefix="tableCars.">

	<table>
		<tr>
			<td><b><fmt:message key="Producer" /></b></td>
		</tr>
		<c:forEach items="${producers}" var="producer">
			<tr class="color">
				<td><c:set var="producerForCheck"
						value="${fn:split(', ,',' ')}">
					</c:set> <c:set var="producerForCheck"
						value="${fn:join(producerForCheck,producer)}">
					</c:set> <c:choose>
						<c:when test="${fn:contains(producersForJsp,producerForCheck)}">
							<c:set value="checked" var="check" />
						</c:when>
						<c:otherwise>
							<c:set value="" var="check" />
						</c:otherwise>
					</c:choose>
					<div>
						<input name="producerFilter" type="checkbox" ${check}
							value="${producer}"> <a>${producer}</a>
					</div></td>
			</tr>
		</c:forEach>
		<tr>
			<td><b><fmt:message key="Rank" /> </b></td>
		</tr>
		<c:forEach items="${ranks}" var="rank">
			<tr class="color">
				<td><c:set var="rankForCheck" value="${fn:split(', ,',' ')}">
					</c:set> <c:set var="rankForCheck" value="${fn:join(rankForCheck,rank)}">
					</c:set> <c:choose>
						<c:when test="${fn:contains(ranksForJsp,rankForCheck)}">
							<c:set value="checked" var="check" />
						</c:when>
						<c:otherwise>
							<c:set value="" var="check" />
						</c:otherwise>
					</c:choose> <input name="rankFilter" type="checkbox" ${check} value="${rank}"><a>${rank}</a></td>
			</tr>
		</c:forEach>
	</table>

</fmt:bundle>
