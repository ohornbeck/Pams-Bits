<template>
  <div class="services-container">
    <header class="hero-triple">
      <div class="hero-img img-1"></div>
      <div class="hero-img img-2">
        <div class="hero-wrapper">
          <div class="hero-overlay-text">
            <h1>Our Services</h1>
            <p>Precision fitting for performance.</p>
          </div>
        </div>
      </div>
      <div class="hero-img img-3"></div>
    </header>

    <main>
      <section class="wireframe-row">
        <div class="row-image img-focus-3">
          <div class="label-box">What to Expect</div>
        </div>
        <div class="row-text">
          <h3>Your Fitting Journey</h3>
          <p>Prior to your appointment, you will be provided with a questionnaire which will help Pam understand your history and your needs for bit fitting. Pam will then speak on the phone with you to discuss your specific goals and issues.</p>
          <div class="sub-steps">
            <strong>During the fitting:</strong>
            <ul>
              <li>Inspection of horse’s head, mouth, and current equipment.</li>
              <li>Observation of horse and rider in the current setup.</li>
              <li>Collaborative solutions for bridle fit or bit type.</li>
              <li>Opportunity to ride in multiple bits for trial or purchase.</li>
            </ul>
          </div>
        </div>
      </section>

      <section class="wireframe-row">
        <div class="row-image img-focus-1">
          <div class="label-box">What Can We Do</div>
        </div>
        <div class="row-text">
          <h3>Equine Ergonomics</h3>
          <p>We analyze the biomechanics of your horse's head and mouth to ensure that both your bridle and bit support natural movement. This includes mouth anatomy and measurements which leads to equine comfort including mouth pressure equalization and nerve pressure relief.</p>
        </div>
      </section>

      <section class="wireframe-row">
        <div class="row-image img-focus-2">
          <div class="label-box">The Consultation</div>
        </div>
        <div class="row-text">
          <h3>Professional Results</h3>
          <p>Expect a thorough evaluation and a noticeable difference in your horse's comfort. We provide a detailed report of all adjustments and recommendations as well as suggested follow up plans and information on how to perform mouth and head examinations yourself.</p>
          <div class="sub-steps">
            <strong>Consultation Pricing:</strong>
            <ul>
              <li>Cost of consultation: $150 Session.</li>
              <li>A discount is offered for multiple horses at the same time.</li>
              <li>A travel cost will be added if location is over 75 miles.</li>
            </ul>
          </div>
        </div>
      </section>

      <!-- Updated Suppliers Section: Individual Sizing & Faster Speed -->
      <section class="suppliers-section">
        <h2>Our Trusted Suppliers</h2>
        <div class="logo-slider">
          <div class="logo-track">
            <!-- First Loop -->
            <div v-for="(logo, index) in supplierLogos" :key="'a' + index" class="logo-slide">
              <img :src="getImgUrl(logo.file)" :alt="logo.name" :class="logo.sizeClass" />
            </div>
            <!-- Second Loop for seamless scroll -->
            <div v-for="(logo, index) in supplierLogos" :key="'b' + index" class="logo-slide">
              <img :src="getImgUrl(logo.file)" :alt="logo.name" :class="logo.sizeClass" />
            </div>
          </div>
        </div>
      </section>

      <div class="cta-banner">
        <div class="cta-content">
          <h2>Ready for a better ride?</h2>
          <div class="contact-stack">
            <p>Book a consultation <router-link to="/booking" class="text-cta-button">here</router-link></p>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'ServicesView',
  data() {
    return {
      supplierLogos: [
        { name: 'Neue Schule', file: 'neue-scheule.png', sizeClass: 'logo-large' },
        { name: 'NS Verbindend', file: 'NS verbindend.png', sizeClass: 'logo-xl' },
        { name: 'NS Turtle Top', file: 'NS turtle top.png', sizeClass: 'logo-xl' },
        { name: 'NS Turtle Tactio', file: 'NS turtle tactio.png', sizeClass: 'logo-xl' },
        { name: 'Neue Schule', file: 'neue-scheule.png', sizeClass: 'logo-large' },
        { name: 'Sprenger', file: 'sprenger.png', sizeClass: 'logo-small' },
        { name: 'HS WH Ultra', file: 'HS WH ultra.png', sizeClass: 'logo-xl' },
        { name: 'HS Duo', file: 'HSduo.png', sizeClass: 'logo-xl' },
        { name: 'HS KK', file: 'HSKK.png', sizeClass: 'logo-xl' },
        { name: 'Sprenger', file: 'sprenger.png', sizeClass: 'logo-small' },
        { name: 'Trust', file: 'trust.png', sizeClass: 'logo-xl' },
        { name: 'Trust Innosense', file: 'Trust innosense.png', sizeClass: 'logo-xl' },
        { name: 'Trust Sweet Iron', file: 'Trust sweet iron.png', sizeClass: 'logo-xl' },
        { name: 'Trust Waterford', file: 'trust waterford.png', sizeClass: 'logo-xl' },
        { name: 'Trust', file: 'trust.png', sizeClass: 'logo-xl' }
      ]
    };
  },
  methods: {
    getImgUrl(name) {
      try {
        return require(`@/assets/${name}`);
      } catch (e) {
        console.error("Missing asset:", name);
        return "";
      }
    }
  }
};
</script>

<style scoped>
h1, h2, h3 { font-family: 'Playfair Display', serif; }

/* 3 image section */
.hero-triple { display: flex; height: 45vh; overflow: hidden; background: #1A2B49; }
.hero-img {
  flex: 1;
  background-size: cover;
  background-position: center;
  transition: flex 0.5s ease;
  filter: brightness(0.7);
}
.hero-img:hover { flex: 1.2; filter: brightness(0.9); }

.img-1 { background-image: url('../assets/Close up of horse.jpg'); }
.img-2 { background-image: url('../assets/up close of horse 3.jpg'); }
.img-3 { background-image: url('../assets/up close of horse 2.jpg'); }

.hero-wrapper { display: flex; width: 100%; height: 100%; align-items: center; justify-content: center; }

.hero-overlay-text {
  text-align: center;
  color: white;
  background: rgba(26, 43, 73, 0.7);
  padding: 20px 40px;
  border: 1px solid #C5A059;
}

/* main content */
main { max-width: 1100px; margin: 60px auto; padding: 0 20px; }

.wireframe-row {
  display: flex; background: #f8f9fa; margin-bottom: 40px;
  align-items: stretch; border-radius: 4px; border: 1px solid #C5A059;
  overflow: hidden;
}

.row-image {
  flex: 0 0 300px; min-height: 250px; background-size: cover; background-position: center;
  display: flex; align-items: center; justify-content: center;
}

.img-focus-3 { background-image: url('../assets/pam working 1.jpg'); }
.img-focus-1 { background-image: url('../assets/pam working 3.jpg'); }
.img-focus-2 { background-image: url('../assets/pam working 4.jpg'); }

.label-box {
  background: rgba(26, 43, 73, 0.8); color: white; padding: 10px 20px;
  font-weight: bold; border: 1px solid #C5A059;
}

.row-text {
  flex: 1; padding: 40px;
  display: flex; flex-direction: column;
  justify-content: center;
  text-align: left;
}
.row-text h3 { color: #1A2B49; margin-bottom: 15px; }

.sub-steps { margin-top: 20px; padding-top: 20px; border-top: 1px solid #dee2e6; }
.sub-steps ul { margin-top: 10px; padding-left: 20px; }
.sub-steps li { margin-bottom: 8px; list-style-type: square; }

/* Suppliers Section Styles */
.suppliers-section { margin-top: 100px; text-align: center; }
.suppliers-section h2 { margin-bottom: 40px; color: #1A2B49; }

.logo-slider { overflow: hidden; padding: 60px 0; background: white; white-space: nowrap; position: relative; }
.logo-track {
  display: inline-flex;
  align-items: center;
  animation: scroll 30s linear infinite; /* Faster scroll */
  width: max-content;
}
.logo-slide {
  margin: 0 45px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Individual Logo Sizing Logic */
.logo-slide img { width: auto; transition: transform 0.3s ease; }

.logo-small { height: 75px !important; }  /* Sprenger/HS */
.logo-large { height: 135px !important; } /* Neue Schule */
.logo-xl { height: 160px !important; }    /* Trust */

.logo-slide img:hover { transform: scale(1.05); }

@keyframes scroll {
  from { transform: translateX(0); }
  to { transform: translateX(-50%); }
}

/* CTA Banner Style */
.cta-banner {
  background: #1A2B49; color: white; padding: 80px 20px;
  display: flex; justify-content: center; align-items: center; margin-top: 80px;
}
.cta-content { text-align: center; }
.contact-stack { display: flex; flex-direction: column; align-items: center; gap: 10px; margin-top: 25px; }
.contact-stack p { font-size: 1.2rem; color: #FFFFFF; margin: 0; }

.text-cta-button {
  display: inline-block;
  color: #C5A059;
  text-decoration: none;
  font-weight: 700;
  letter-spacing: 1px;
  transition: color 0.3s ease;
  cursor: pointer;
}
.text-cta-button:hover { color: #FFFFFF; }

@media (max-width: 768px) {
  .hero-triple { height: 35vh; }
  .img-1, .img-3 { display: none; }
  .wireframe-row { flex-direction: column; }
  .row-image { flex: 0 0 200px; width: 100%; }

  /* Mobile Logo Sizing */
  .logo-small { height: 50px !important; }
  .logo-large { height: 85px !important; }
  .logo-xl { height: 100px !important; }
  .logo-track { animation: scroll 20s linear infinite; }
}
</style>