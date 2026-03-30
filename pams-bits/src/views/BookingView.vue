<template>
  <div class="booking-container">
    <header class="hero-booking">
      <h1>Contact Pam</h1>
      <p>Ready to schedule a fitting? Fill out the form below.</p>
    </header>

<main>
  <div class="form-wrapper">
    <div class="iframe-container">
      <iframe src="https://docs.google.com/forms/d/e/1FAIpQLSc_iyWBOoJ9fXBBdzlJqHv_7-bUAsQvH1yWCh0FCZbWyTpiWA/viewform?embedded=true" width="100%" height="800" frameborder="0" marginheight="0" marginwidth="0">Loading…</iframe>
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
      // 1. Reset Errors
      this.errors = { firstName: false, lastName: false, email: false, message: false };
      let hasErrors = false;

      // 2. Validation Logic
      if (!this.firstName.trim()) { this.errors.firstName = true; hasErrors = true; }
      if (!this.lastName.trim()) { this.errors.lastName = true; hasErrors = true; }
      if (!this.email.trim() || !this.email.includes('@')) { this.errors.email = true; hasErrors = true; }
      if (!this.message.trim()) { this.errors.message = true; hasErrors = true; }

      if (hasErrors) return;

      // 3. Send Data to Formspree
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
/* Keeping your existing styles exactly as they were */
.booking-container { color: #333; line-height: 1.6; }
h1, h2 { font-family: 'Playfair Display', serif; }

.hero-booking {
  background: linear-gradient(rgba(26, 43, 73, 0.7), rgba(26, 43, 73, 0.7)), 
              url('https://images.unsplash.com/photo-1551884831-bbf3cdc67170?auto=format&fit=crop&q=80&w=1470') no-repeat center center/cover;
  height: 30vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  text-align: center;
  padding: 20px;
}

.hero-booking h1 { font-size: 3rem; margin-bottom: 10px; }

main { max-width: 800px; margin: 40px auto 80px; padding: 0 20px; }

.form-wrapper {
  background: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  border-top: 6px solid #C5A059;
}

.contact-form { display: flex; flex-direction: column; gap: 20px; }
.name-row { display: flex; gap: 20px; }
.form-group { display: flex; flex-direction: column; flex: 1; }

label { font-weight: bold; margin-bottom: 8px; color: #1A2B49; }

input, textarea {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-family: 'Lato', sans-serif;
  font-size: 1rem;
  transition: all 0.3s ease;
}

textarea::placeholder { color: #999; font-style: italic; font-size: 0.95rem; }
input:focus, textarea:focus { outline: none; border-color: #C5A059; box-shadow: 0 0 5px rgba(197, 160, 89, 0.2); }

.error-border { border-color: #d9534f !important; background-color: #fff8f8; }
.error-text { color: #d9534f; font-size: 0.85rem; margin-top: 5px; font-weight: bold; }

.submit-button {
  background: #C5A059;
  color: white;
  padding: 15px;
  border: none;
  border-radius: 4px;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s ease;
  margin-top: 10px;
}

.submit-button:disabled { background: #ccc; cursor: not-allowed; }
.submit-button:hover:not(:disabled) { background: #1A2B49; }

.success-message { text-align: center; padding: 40px 0; }
.success-message h2 { color: #1A2B49; font-size: 2.5rem; margin-bottom: 20px; }
.success-message p { font-size: 1.1rem; max-width: 500px; margin: 0 auto 30px; color: #555; }

.cta-button-outline {
  background: transparent;
  color: #1A2B49;
  border: 2px solid #1A2B49;
  padding: 10px 20px;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 20px;
}

.cta-button-outline:hover { background: #1A2B49; color: white; }

@media (max-width: 600px) {
  .name-row { flex-direction: column; gap: 20px; }
}
</style>