<template>
  <nav>
    <div class="brand-container">
      <img src="../assets/PBB Logo.png" alt="Pam's Bits and Bridles Logo" class="nav-logo">
      <div class="brand-font">Pam's Bits & Bridles</div>
    </div>

    <button
        class="hamburger"
        @click="isMenuOpen = !isMenuOpen"
        :class="{ 'is-active': isMenuOpen }"
        aria-label="Menu"
    >
      <span class="line"></span>
      <span class="line"></span>
      <span class="line"></span>
    </button>

    <div class="nav-links" :class="{ 'nav-active': isMenuOpen }">
      <router-link to="/" @click="isMenuOpen = false">Home</router-link>
      <router-link to="/services" @click="isMenuOpen = false">Services</router-link>
      <router-link to="/booking" @click="isMenuOpen = false">Book a Consultation</router-link>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'TheNavigation',
  data() {
    return {
      isMenuOpen: false
    }
  }
}
</script>

<style scoped>
nav {
  background: #fff;
  padding: 1rem 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 1000 !important;
  pointer-events: auto !important;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.brand-container {
  display: flex;
  align-items: center;
}

.nav-logo {
  height: 140px;
  width: auto;
  margin-right: 20px;
  border-radius: 50%;
}

.brand-font {
  font-family: 'Playfair Display', serif;
  font-size: 2rem;
  font-weight: bold;
  color: #1A2B49;
}

.nav-links {
  display: flex;
  align-items: center;
}

.nav-links a {
  text-decoration: none;
  color: #1A2B49;
  margin-left: 30px;
  font-weight: 700;
  font-size: 1.3rem;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
  font-family: 'Lato', sans-serif;
}

.nav-links a:hover {
  color: #C5A059;
}

.hamburger {
  display: none;
  flex-direction: column;
  justify-content: space-between;
  width: 30px;
  height: 21px;
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  z-index: 1100;
}

.hamburger .line {
  width: 100%;
  height: 3px;
  background-color: #1A2B49;
  display: block;
  transition: all 0.3s ease-in-out;
  border-radius: 3px;
}

@media (max-width: 992px) {
  nav {
    padding: 1rem 20px;
  }

  .hamburger {
    display: flex;
  }

  .nav-links {
    position: fixed;
    top: 0;
    right: 0;
    height: 100vh;
    width: 70%;
    background-color: white;
    flex-direction: column;
    justify-content: center;
    box-shadow: -5px 0px 15px rgba(0,0,0,0.1);
    transform: translateX(100%);
    transition: transform 0.4s ease-in-out;
  }

  .nav-links.nav-active {
    transform: translateX(0);
  }

  .nav-links a {
    margin: 20px 0;
    font-size: 1.6rem;
    margin-left: 0;
  }

  .brand-font {
    font-size: 1.4rem;
  }

  .nav-logo {
    height: 60px;
  }

  .hamburger.is-active .line:nth-child(1) {
    transform: translateY(9px) rotate(45deg);
  }

  .hamburger.is-active .line:nth-child(2) {
    opacity: 0;
  }

  .hamburger.is-active .line:nth-child(3) {
    transform: translateY(-9px) rotate(-45deg);
  }
}
</style>