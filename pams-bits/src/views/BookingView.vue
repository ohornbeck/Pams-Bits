<template>
  <div class="booking-container">
    <header class="hero-booking">
      <div class="hero-wrapper">
        <div class="hero-overlay-text">
          <h1>Ready to schedule a fitting?</h1>
          <p>Fill out the form below.</p>
        </div>
      </div>
    </header>

    <main>
      <div class="form-wrapper">
        <div class="iframe-container">
          <iframe
              src="https://docs.google.com/forms/d/e/1FAIpQLSc_iyWBOoJ9fXBBdzlJqHv_7-bUAsQvH1yWCh0FCZbWyTpiWA/viewform?embedded=true"
              width="100%"
              height="800"
              frameborder="0"
              loading="lazy"
              title="Booking Form"
          >Loading…</iframe>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'BookingView'
};
</script>

<style scoped>
.booking-container {
  color: #333;
  line-height: 1.6;
  background-color: #f4f1ee;
  min-height: 100vh;
}

h1 { font-family: 'Playfair Display', serif; }

.hero-booking {
  background: linear-gradient(rgba(26, 43, 73, 0.3), rgba(26, 43, 73, 0.3)),
  url('../assets/bridles.jpeg') no-repeat center center/cover;
  height: 30vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.hero-wrapper {
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: center;
}

.hero-overlay-text {
  text-align: center;
  color: white;
  background: rgba(26, 43, 73, 0.8);
  padding: 20px 35px;
  border: 1px solid #C5A059;
  box-shadow: 0 10px 30px rgba(0,0,0,0.3);
}

.hero-overlay-text h1 {
  font-size: 1.6rem;
  margin-bottom: 5px;
}

.hero-overlay-text p {
  font-size: 1.1rem;
  font-style: italic;
  margin: 0;
}

main {
  max-width: 900px;
  margin: 40px auto 100px;
  padding: 0 20px;
}

.form-wrapper {
  background: white;
  padding: 25px;
  border: 1px solid #C5A059;
  border-radius: 0;
}

.iframe-container {
  width: 100%;
  min-height: 800px;
  overflow: hidden;
  background: #fff;
}

iframe {
  display: block;
  width: 100%;
  border: none;
}

@media (max-width: 768px) {
  .hero-booking { height: 25vh; }
  .hero-overlay-text {
    padding: 15px 20px;
    width: 90%;
  }
  .hero-overlay-text h1 { font-size: 1.4rem; }
  .hero-overlay-text p { font-size: 1rem; }

  main { margin-top: 20px; }
  .form-wrapper { padding: 10px; }
}
</style>