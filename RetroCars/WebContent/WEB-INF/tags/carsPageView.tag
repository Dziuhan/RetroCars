<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/u.tld" prefix="u"%>
<fmt:setLocale value="${local}" />
<c:if test="${empty indexPage}">
	<c:set var="indexPage" value="1" />
</c:if>
<u:pageView indexPage="${indexPage}" list="${cars}" />
<c:choose>
	<c:when test="${viewFormat=='table'}">
		<u:carsViewTable />
	</c:when>
	<c:when test="${viewFormat=='list'}">
		<u:carsViewList />
	</c:when>
	<c:when test="${viewFormat=='Tile'}">
		<u:carsViewLargeIcons />
	</c:when>
	<c:otherwise>
		<u:carsViewTable />
	</c:otherwise>
</c:choose>
<div class="center">
	<a
		href="ClientController?command=changePageView&indexPage=${indexFirstPage}">${indexFirstPage}</a>
	<a
		href="ClientController?command=changePageView&indexPage=${indexPreviousPage}">${indexPreviousPage}</a>
	<b>${indexPage} </b> <a
		href="ClientController?command=changePageView&indexPage=${indexNextPage}">${indexNextPage}</a>
	<a
		href="ClientController?command=changePageView&indexPage=${indexLastPage}">${indexLastPage}</a>
</div>

