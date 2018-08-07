<section class="hero is-primary is-medium">
  <!-- Hero head: will stick at the top -->
  <div class="hero-head">
    <nav class="navbar">
      <div class="container">
        <div class="navbar-brand">
          <a class="navbar-item" href="/feeds">
            <img src="images/logo.png" alt="Logo" id="brand">
          </a>
          <span class="navbar-burger burger" data-target="navbarMenuHeroA">
            <span></span>
            <span></span>
            <span></span>
          </span>
        </div>
        <div id="navbarMenuHeroA" class="navbar-menu">
          <div class="navbar-end">
            <a class="navbar-item" href="/feeds">
              Home
            </a>
            <span class="navbar-item">
              <a class="button is-primary is-inverted">
                <span>Login</span>
              </a>
            </span>
          </div>
        </div>
      </div>
    </nav>
  </div>

  <!-- Hero content: will be in the middle -->
  <div class="hero-body">
    <div class="container has-text-centered">
      <div class="columns">
        <div class="column">
          <h1 class="title">
            ${feed.feedName}
          </h1>
          <h2 class="subtitle">
            ${feed.title}
          </h2>
          <p>
            <strong>Last Updated: </strong> ${feed.lastUpdated}
          </p>
        </div>

        <div class="column">
          <p>
            <strong>Article Count: </strong> ${article_count}
          </p>
          <p>
            <a href="${feed.url}">${feed.url}</a>
          </p>
          <p>
            <br>
            <a class="button is-medium is-primary is-inverted" href="/feeds">View All Feeds</a>
          </p>
        </div>
      </div>
    </div>
  </div>

</section>