<template>
  <div class="services-container">
    <header class="hero-triple">
      <div class="hero-img img-1"></div>
      <div class="hero-img img-2">
        <div class="hero-overlay-text">
          <h1>Our Services</h1>
          <p>Precision fitting for performance.</p>
        </div>
      </div>
      <div class="hero-img img-3"></div>
    </header>

    <main>
      <section class="wireframe-row">
        <div class="row-image img-focus-1">
          <div class="label-box">What Can We Do</div>
        </div>
        <div class="row-text">
          <h3>Equine Ergonomics</h3>
          <p>We analyze the biomechanics of your horse to ensure that every piece of tack supports natural movement. This includes mouth measurements and nerve pressure relief.</p>
        </div>
      </section>

      <section class="wireframe-row">
        <div class="row-image img-focus-2">
          <div class="label-box">What You Can Expect</div>
        </div>
        <div class="row-text">
          <h3>Professional Results</h3>
          <p>Expect a thorough evaluation and a noticeable difference in your horse's comfort. We provide a detailed report of all adjustments and recommendations.</p>
        </div>
      </section>

      <div class="cta-banner">
        <h2>Ready for a better ride?</h2>
        <router-link to="/booking" class="cta-button">Book an Appointment</router-link>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'ServicesView'
};
</script>

<style scoped>
h1, h2, h3 { font-family: 'Playfair Display', serif; }

.hero-triple {
  display: flex;
  height: 45vh;
  overflow: hidden;
  background: #1A2B49;
}

.hero-img {
  flex: 1;
  background-size: cover;
  background-position: center;
  transition: flex 0.5s ease;
  filter: brightness(0.7);
}

.hero-img:hover { flex: 1.2; filter: brightness(0.9); }

.img-1 { background-image: url('https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?q=80&w=800'); }
.img-2 { 
  background-image: url('https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?q=80&w=800');
  display: flex;
  align-items: center;
  justify-content: center;
}
.img-3 { background-image: url('https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?q=80&w=800'); }

.hero-overlay-text {
  text-align: center;
  color: white;
  background: rgba(26, 43, 73, 0.7);
  padding: 10px 20px;
  border: 1px solid #C5A059;
}

main { max-width: 1100px; margin: 60px auto; padding: 0 20px; }

.wireframe-row {
  display: flex;
  background: #e9ecef; 
  margin-bottom: 40px;
  align-items: stretch;
  border-radius: 4px;
  border: 1px solid #C5A059;
  overflow: hidden;
}

.row-image {
  flex: 0 0 300px;
  height: 250px;
  background-size: cover;
  background-position: center;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.img-focus-1 { background-image: url('https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?q=80&w=800'); }
.img-focus-2 { background-image: url('https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?q=80&w=800'); }

.label-box {
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(5px);
  color: white;
  padding: 10px 20px;
  font-weight: bold;
  border: 1px solid rgba(255,255,255,0.3);
}

.row-text {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.row-text h3 { color: #1A2B49; margin-bottom: 15px; }

.cta-banner {
  background: #1A2B49;
  color: white;
  padding: 60px 20px;
  text-align: center;
  margin-top: 80px;
}

.cta-button {
  display: inline-block;
  background: #C5A059;
  color: white;
  padding: 12px 30px;
  text-decoration: none;
  border-radius: 4px;
  margin-top: 25px;
  font-weight: bold;
}

@media (max-width: 768px) {
  .hero-triple { height: 30vh; }
  .img-1, .img-3 { display: none; }
  .wireframe-row { flex-direction: column; }
  .row-image { flex: 0 0 200px; width: 100%; }
}
</style>