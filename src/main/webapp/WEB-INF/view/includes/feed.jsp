<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" uri="/WEB-INF/custom.tld"%>


<a class="ui raised card" href="/messages?feed_id=${feed.id}">
  <div class="image">
    <img src="${feed.imageUrl}">
  </div>
  <div class="content">
    <div class="header">${feed.feedName}</div>
    <div class="meta">
      ${feed.description}
    </div>
    <div class="description">
      ${feed.title}
    </div>
  </div>
  <div class="extra content">
    <span class="">
      Last Updated:
      <span class="is-primary"><m:prettydate date="${feed.lastUpdated}" /></span> 
    </span>
  </div>
  <!--<div class="ui bottom attached button is-danger">
    Remove
  </div>-->
</a>