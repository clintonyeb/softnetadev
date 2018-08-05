<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css">
    <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous"> -->
</head>
<body>
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
            <a class="button is-medium is-danger" id="remove-feed-btn" onclick="removeFeed(${feed.id})">Delete Feed</a>
        </p>
        
    </div>
 </section>
 <script src="js/script.js"></script>
</body>
</html>