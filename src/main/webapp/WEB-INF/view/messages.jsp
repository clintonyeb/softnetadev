<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<jsp:include page="includes/header.jsp"></jsp:include>
<c:set var="feed" value="${feed}" scope="request"/>
<c:set var="article_count" value="${article_count}" scope="request"/>
<jsp:include page="includes/message_header.jsp"></jsp:include>

<section class="section">
    <div class="container">
        <div class="ui divided items">
           <c:forEach var="mess" items="${messages}">
            <c:set var="mess" value="${mess}" scope="request"/>
                <jsp:include page="includes/message.jsp"></jsp:include>
           </c:forEach>
        </div>

        <hr>
        <p class="has-text-right">
            <a class="button is-danger" id="remove-feed-btn" onclick="removeFeed(${feed.id})">Delete This Feed</a>
        </p>
        
    </div>
 </section>
<jsp:include page="includes/footer.jsp"></jsp:include>