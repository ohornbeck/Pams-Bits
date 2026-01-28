<template>
  <div class="home-container">
    <nav>
      <div class="brand-container">
        <img src="../assets/PBB Logo.png" alt="Pam's Bits and Bridles Logo" class="nav-logo">
        <div class="brand-font">Pam's Bits & Bridles</div>
      </div>
      <div class="nav-links">
        <router-link to="/">Home</router-link>
        <a href="#">Services</a>
        <a href="#">Book Appointment</a>
      </div>
    </nav>

    <header class="hero">
      <h1>Services Page</h1>
      <p>Expertly fitted to your horse's unique needs.</p>
      <a href="#" class="cta-button">View Our Services</a>
    </header>

    <main>
      <section class="content-section">
        <div class="image-box">
          <img src="../assets/Pam Holding Medal.png" alt="Pam holding a medal">
        </div>
        <div class="text-box">
          <h2>Meet Pam</h2>
          <p>With years of experience in equine ergonomics, Pam ensures that every bit and bridle is more than just equipment—it's a gateway to better communication between horse and rider.</p>
        </div>
      </section>

      <section class="content-section reverse">
        <div class="image-box">
          <img src="../assets/Pam Jumping.png" alt="Pam jumping over obstacle with horse">
        </div>
        <div class="text-box">
          <h2>Our Mission</h2>
          <p>What is Bits and Bridles about? It's about safety, style, and the perfect fit. We provide a curated selection of high-quality gear sourced from trusted manufacturers.</p>
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
.home-container {
  font-family: 'Lato', sans-serif;
  background-color: #f8f9fa; /* Slightly cooler background to match the logo */
  color: #333;
  line-height: 1.6;
}

h1, h2, h3, .brand-font {
  font-family: 'Playfair Display', serif;
}

/* Navigation Updates */
nav {
  background: #fff;
  padding: 0.5rem 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.brand-container {
  display: flex;
  align-items: center;
}

.nav-logo {
  height: 60px; /* Adjusted for visibility */
  width: auto;
  margin-right: 15px;
  border-radius: 50%;
}

.brand-font {
  font-size: 1.5rem; 
  font-weight: bold; 
  color: #1A2B49; /* Logo Navy */
}

nav a {
  text-decoration: none;
  color: #1A2B49;
  margin-left: 20px;
  font-weight: 600;
  transition: color 0.3s;
}

nav a:hover {
  color: #C5A059; /* Logo Gold */
}

/* Hero Section */
.hero {
  background: linear-gradient(rgba(26, 43, 73, 0.6), rgba(26, 43, 73, 0.6)), 
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

/* Content Sections */
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
  box-shadow: 10px 10px 0px #C5A059; /* Gold shadow */
}

h2 {
  font-size: 2.5rem;
  color: #1A2B49; /* Navy Headers */
  margin-bottom: 20px;
}

/* Buttons */
.cta-button {
  display: inline-block;
  background: #C5A059;
  color: white;
  padding: 12px 25px;
  text-decoration: none;
  border-radius: 4px;
  margin-top: 20px;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.cta-button:hover {
  background: #C5A059;
  color: #1A2B49;
  font-weight: bold;
}

footer {
  text-align: center;
  padding: 40px 20px;
  background: #1A2B49; /* Navy Footer */
  color: #fff;
}

@media (max-width: 768px) {
  .content-section, .content-section.reverse { flex-direction: column; }
  .hero h1 { font-size: 2.5rem; }
  .nav-links { display: none; } /* Consider a burger menu for mobile later */
}
</style>