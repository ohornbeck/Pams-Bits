<template>
  <div class="booking-container">
    <header class="hero-booking">
      <h1>Ready to schedule a fitting?</h1>
      <p>Fill out the form below.</p>
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
  name: 'BookingView',
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      message: '',
      submitted: false,
      isSubmitting: false,
      errors: {
        firstName: false,
        lastName: false,
        email: false,
        message: false
      }
    };
  },
  methods: {
    async handleSubmit() {
      this.errors = { firstName: false, lastName: false, email: false, message: false };
      let hasErrors = false;

      if (!this.firstName.trim()) { this.errors.firstName = true; hasErrors = true; }
      if (!this.lastName.trim()) { this.errors.lastName = true; hasErrors = true; }
      if (!this.email.trim() || !this.email.includes('@')) { this.errors.email = true; hasErrors = true; }
      if (!this.message.trim()) { this.errors.message = true; hasErrors = true; }

      if (hasErrors) return;

      this.isSubmitting = true;
      try {
        const response = await fetch("https://formspree.io/f/YOUR_FORMSPREE_ID", {
          method: "POST",
          headers: { "Content-Type": "application/json", "Accept": "application/json" },
          body: JSON.stringify({
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
            message: this.message
          })
        });

        if (response.ok) {
          this.submitted = true;
        } else {
          alert("Oops! There was a problem sending your message.");
        }
      } catch (error) {
        alert("Oops! There was a connection error.");
      } finally {
        this.isSubmitting = false;
      }
    },
    resetForm() {
      this.submitted = false;
      this.firstName = '';
      this.lastName = '';
      this.email = '';
      this.message = '';
    }
  }
};
</script>

<style scoped>
.booking-container {
  color: #333;
  line-height: 1.6;
  background-color: #f4f1ee;
  min-height: 100vh;
}

h1, h2 { font-family: 'Playfair Display', serif; }

.hero-booking {
  background: linear-gradient(rgba(26, 43, 73, 0.3), rgba(26, 43, 73, 0.3)),
  url('../assets/bridles.jpeg') no-repeat center center/cover;
  height: 35vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  text-align: center;
  padding: 20px;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

.hero-booking h1 { font-size: 3.5rem; margin-bottom: 10px; }
.hero-booking p { font-size: 1.2rem; font-style: italic; }

main {
  max-width: 900px;
  margin: 60px auto 100px;
  padding: 0 20px;
}

.form-wrapper {
  background: white;
  padding: 25px;
  border: 1px solid #C5A059;
  border-radius: 4px;
  box-shadow: none;
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
  .hero-booking h1 { font-size: 2.5rem; }
  main { margin-top: 30px; }

  .form-wrapper {
    padding: 10px;
  }
}
</style>