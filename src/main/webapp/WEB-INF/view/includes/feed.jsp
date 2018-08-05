<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="m" uri="/WEB-INF/custom.tld"%>


<a class="ui raised card" href="/messages?feed_id=${feed.id}">
  <div class="image">
    <img src="${feed.imageUrl}">
  </div>
  <div class="content">
    <div class="header">${feed.feedName}</div>
    <div class="description">
      ${feed.description ? feed.description : feed.url}
    </div>
    <div class="subtitle is-6 has-text-primary">
      ${feed.title}
    </div>
  </div>
  <div class="extra content has-text-centered">
    <span class="">
      Last Updated:
      <span class="is-primary"><m:prettydate date="${feed.lastUpdated}" /></span> 
    </span>
  </div>
  <!--<div class="ui bottom attached button is-danger">
    Remove
  </div>-->
</a>