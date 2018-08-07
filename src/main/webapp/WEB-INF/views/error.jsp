<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<jsp:include page="includes/header.jsp"></jsp:include>
<section class="section">
    <div class="container has-text-centered">
        <h1 class="title is-2">An error has occured</h1>
        <h2 class="subtitle">Error: ${message ? message : "Unknown Error"}</h2> 
    </div>
</section>
</body>
</html>