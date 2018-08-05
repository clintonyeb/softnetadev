<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Softneta | Feeds</title>
    <link href="css/styles.css" rel="stylesheet" type="text/css">
    <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous"> -->
</head>
<body>
  <jsp:include page="includes/header.jsp"></jsp:include>

  <section class="section">
    <div class="container">
      <div class="ui link cards">
        <c:forEach var="feed" items="${items.content}">
            <c:set var="feed" value="${feed}" scope="request"/>
            <jsp:include page="includes/feed.jsp"></jsp:include>
        </c:forEach>
      </div>
    </div>
  </section>
  <script src="js/script.js"></script>
</body>
</html>