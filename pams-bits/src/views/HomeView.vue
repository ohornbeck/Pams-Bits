<template>
  <div class="home-container">
    <nav>
      <div class="brand-font">Pam's Bits & Bridles</div>
      <div>
        <router-link to="/">Home</router-link>
        <a href="#">Services</a>
        <a href="#">Book Appointment</a>
      </div>
    </nav>

    <header class="hero">
      <h1>Quality Gear for Quality Rides</h1>
      <p>Expertly fitted to your horse's unique needs.</p>
      <a href="#" class="cta-button">View Our Services</a>
    </header>

    <main>
      <section class="content-section">
        <div class="image-box">
          <img src="https://images.unsplash.com/photo-1598974357851-98166a9d9b56?auto=format&fit=crop&q=80&w=800" alt="Pam working with horse tack">
        </div>
        <div class="text-box">
          <h2>Meet Pam</h2>
          <p>lorem ipsum...</p>
        </div>
      </section>

      <section class="content-section reverse">
        <div class="image-box">
          <img src="https://images.unsplash.com/photo-1551884831-bbf3cdc67170?auto=format&fit=crop&q=80&w=800" alt="Detailed horse bridle">
        </div>
        <div class="text-box">
          <h2>Our Mission</h2>
          <p>What is Bits and Bridles about? It's about safety, style, and the perfect fit.</p>
          <a href="#" class="cta-button">Learn More About Our Fittings</a>
        </div>
      </section>
    </main>

    <footer>
      <p>&copy; 2026 Pam's Bits and Bridles. All rights reserved.</p>
      <p>Contact: (443) 619-3904 | pamsbitsandbridles@gmail.com</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
};
</script>

<style scoped>
/* Paste your CSS here - 'scoped' ensures it doesn't leak to other pages */
.home-container {
  font-family: 'Lato', sans-serif;
  background-color: #f4f1ee;
  color: #333;
  line-height: 1.6;
}

h1, h2, h3, .brand-font {
  font-family: 'Playfair Display', serif;
}

nav {
  background: #fff;
  padding: 1rem 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.brand-font {
  font-size: 1.5rem; 
  font-weight: bold; 
  color: #b85c38;
}

nav a {
  text-decoration: none;
  color: #333;
  margin-left: 20px;
  font-weight: 600;
  transition: color 0.3s;
}

nav a:hover { color: #b85c38; }

.hero {
  background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), 
              url('https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?auto=format&fit=crop&q=80&w=1470') no-repeat center center/cover;
  height: 60vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  text-align: center;
  padding: 20px;
}

.hero h1 { font-size: 3.5rem; }
.hero p { font-size: 1.5rem; font-style: italic; }

main {
  max-width: 1100px;
  margin: 40px auto;
  padding: 0 20px;
}

.content-section {
  display: flex;
  align-items: center;
  gap: 40px;
  margin-bottom: 80px;
}

.content-section.reverse { flex-direction: row-reverse; }

.image-box { flex: 1; }
.text-box { flex: 1; }

.image-box img {
  width: 100%;
  border-radius: 8px;
  box-shadow: 10px 10px 0px #b85c38;
}

h2 {
  font-size: 2.5rem;
  color: #b85c38;
  margin-bottom: 20px;
}

.cta-button {
  display: inline-block;
  background: #b85c38;
  color: white;
  padding: 12px 25px;
  text-decoration: none;
  border-radius: 4px;
  margin-top: 20px;
}

footer {
  text-align: center;
  padding: 40px 20px;
  background: #333;
  color: #fff;
}

@media (max-width: 768px) {
  .content-section, .content-section.reverse { flex-direction: column; }
  .hero h1 { font-size: 2.5rem; }
}
</style>