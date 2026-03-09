<template>
  <div class="home-container">
    <header class="hero">
      <h1>Quality Bits and Bridles</h1>
      <h1>Fit with Care</h1>
      <router-link to="/services" class="cta-button">View Our Services</router-link>
    </header>

    <main>
      <section class="content-section">
        <div class="image-box">
          <img src="../assets/Pam Holding Medal.png" alt="Pam holding a medal">
        </div>
        <div class="text-box">
          <h2>Meet Pam</h2>
          <p style="margin-bottom: 20px;">A lifelong horse person with a deep-rooted passion for the equestrian world, Pam currently manages a diverse herd of five horses across multiple disciplines, primarily Dressage and Eventing, on her three-generation family farm. She just recently received her USDF Bronze medal with a horse she trained exclusively. She blends her extensive hands-on experience with a highly analytical background, holding a BS in Systems Engineering and an MS in Epidemiology. Her unique perspective allows her to approach bitting not just as a craft, but as a science.</p>

        <p style="margin-bottom: 20px;">Pam is at the forefront of equine connection science, having earned her Qualified Bit Analyst Certification from the Neue Schule Academy in November 2025. Her academic credentials in the field are equally distinguished. She graduated with Distinction in both Equine Bits & Bitting Science and Lorinery Science.</p>
        </div>
      </section>

      <section class="content-section reverse">
        <div class="image-box">
          <img src="../assets/Pam Jumping.png" alt="Pam jumping over obstacle with horse">
        </div>
        <div class="text-box">
          <h2>Our Mission</h2>
          <p style="margin-bottom: 20px;">What is Bits and Bridles fitting about? It's about safety, style, and the perfect fit.</p>

           <p style="margin-bottom: 20px;">Often we struggle when we are riding - searching  for that right feel of connection with our horse.  We often are very careful to have our saddles fit by a professional fitter - but how often do we look to our bit or bridle for what’s missing in the connection?</p> 

          <p style="margin-bottom: 20px;">Regardless of your level of riding, your horse will perform better for you if he is comfortable in his bit and bridle.  Is your bridle something that needs adjustment - or do you need a different style to accommodate a specific type of head?  Is he comfortable in his bit that you are using?  Do you need to try a different size or different bit action altogether?</p>

           <p style="margin-bottom: 20px;">There are so many modern types of bits that have been researched and designed by companies specifically to address certain needs of horse and rider.  Pam is here to help you navigate that journey and find the best possible combination of bit and bridle that makes your horse comfortable and you as effective a rider as you can be. </p>
          <router-link to="/booking" class="cta-button">Have Questions?</router-link>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
};
</script>

<style scoped>
.home-container {
  color: #333;
  line-height: 1.6;
}

h1, h2, h3 { font-family: 'Playfair Display', serif; }

.hero {
  background: linear-gradient(rgba(26, 43, 73, 0.6), rgba(26, 43, 73, 0.6)), 
              url('../assets/Website Dante gallop.jpg') no-repeat center 70%/cover;
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
  box-shadow: 10px 10px 0px #C5A059;
}

h2 {
  font-size: 2.5rem;
  color: #1A2B49;
  margin-bottom: 20px;
}

.cta-button {
  display: inline-block;
  background: #C5A059;
  color: white;
  padding: 12px 25px;
  text-decoration: none;
  border-radius: 4px;
  margin-top: 20px;
  transition: all 0.3s ease;
  font-weight: bold;
}

.cta-button:hover {
  background: #1A2B49;
}

@media (max-width: 768px) {
  .content-section, .content-section.reverse { flex-direction: column; }
  .hero h1 { font-size: 2.5rem; }
}
</style>