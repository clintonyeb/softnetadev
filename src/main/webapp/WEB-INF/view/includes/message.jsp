<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="item">
  <div class="image">
    <img src="${mess.thumbnail}">
  </div>
  <div class="content">
    <span class="header">${mess.title}</span>
    <div class="meta">
      <span>${mess.published}</span>
    </div>
    <div class="description">
      <p>${mess.description}</p>
    </div>
    <div class="extra">
        <a class="ui right floated is-primary button" href="${mess.link}" target="_blank">
              Read full story
         </a>
     </div>
  </div>
</div>