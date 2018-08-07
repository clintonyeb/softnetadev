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
      <h1 class="title">
        RSS Feed
      </h1>
      <h2 class="subtitle">
        Staying Updated with the World
      </h2>
      <a class="button is-medium is-primary is-inverted" id="add-feed-btn">Add New Feed</a>
    </div>
  </div>

  <!-- Hero footer: will stick at the bottom -->
  <!--<div class="hero-foot">
    <nav class="tabs">
      <div class="container">
        <ul>
          <li class="is-active"><a>Overview</a></li>
          <li><a>Modifiers</a></li>
          <li><a>Grid</a></li>
          <li><a>Elements</a></li>
          <li><a>Components</a></li>
          <li><a>Layout</a></li>
        </ul>
      </div>
    </nav>
  </div> -->

  <!-- Modal for adding new Feed -->
  <div class="modal" id="feed-modal">
    <div class="modal-background" id="feed-modal-background"></div>
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Add New RSS Feed</p>
        <button class="delete" aria-label="close" id="feed-modal-close"></button>
      </header>
      <section class="modal-card-body">

        <div class="notification is-danger is-hidden" id="notification">
          <button class="delete"></button>
          <span id="message-content"></span>
        </div>

        <div class="field">
          <label class="label">URL:</label>
          <div class="control">
            <input class="input" type="text" placeholder="URL to RSS Feed" id="feed-url" required>
          </div>
        </div>

        <div class="field">
          <label class="label">Name:</label>
          <div class="control">
            <input class="input" type="text" placeholder="Name of RSS Feed" id="feed-name" required>
          </div>
        </div>

      </section>
      <footer class="modal-card-foot">
        <button class="button is-primary" id="feed-modal-save">Save changes</button>
        <button class="button is-danger is-inverted" id="feed-modal-cancel">Cancel</button>
      </footer>
    </div>
  </div>

</section>