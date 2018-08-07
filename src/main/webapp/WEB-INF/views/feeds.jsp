<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<jsp:include page="includes/header.jsp"></jsp:include>
  <jsp:include page="includes/feed_header.jsp"></jsp:include>

  <section class="section">
    <div class="container">

     <c:if test="${empty items}">
    	<h2 class="subtitle is-4 has-text-centered">You have created no feeds. Please use the button above to create new feeds.</h2>
    </c:if>

    <c:if test="${not empty items}">
    	<div class="ui link cards">
        <c:forEach var="feed" items="${items}">
            <c:set var="feed" value="${feed}" scope="request"/>
            <jsp:include page="includes/feed.jsp"></jsp:include>
        </c:forEach>
      </div>
    </c:if>
    </div>
  </section>
<jsp:include page="includes/footer.jsp"></jsp:include>