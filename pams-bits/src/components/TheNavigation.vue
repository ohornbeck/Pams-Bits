<template>
  <nav>
    <div class="brand-container">
      <img src="../assets/PBB Logo.png" alt="Pam's Bits and Bridles Logo" class="nav-logo">
      <div class="brand-font">Pam's Bits & Bridles</div>
    </div>
    <div class="nav-links">
      <router-link to="/">Home</router-link>
      <router-link to="/services">Services</router-link>
      <router-link to="/booking">Contact Pam</router-link>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'TheNavigation'
}
</script>

<style scoped>
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

.brand-container {
  display: flex;
  align-items: center;
}

.nav-logo {
  height: 150px;
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

.nav-links a {
  text-decoration: none;
  color: #1A2B49;
  margin-left: 20px;
  font-weight: 600;
  transition: color 0.3s;
  font-family: 'Lato', sans-serif;
}

.nav-links a:hover {
  color: #C5A059;
}

@media (max-width: 768px) {
  nav {
    flex-direction: column;
    align-items: center;
  }

  .nav-links {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 10px;
  }

  .nav-links a {
    margin: 5px 0;
  }

  .nav-logo {
    height: 80px;
  }

  .brand-font {
    font-size: 1.2rem;
  }
}
</style>