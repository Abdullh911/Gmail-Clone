<template>
  <section class="all">
    <div class="container">
      <div class="txt">
        <h1>Welcome to</h1>
        <h1 class="logo">PostMail</h1>
        <h4>Join us in an impressive experience</h4>
      </div>
      <div class="butns">
        <router-link to="/signup"><button class="button">signUp</button></router-link>
        <router-link to="/login"><button class="button">login</button></router-link>
      </div>
    </div>
  </section>
</template>

<script>

export default {
  mounted(){
    localStorage.removeItem("userData");
  }
};
</script>

<style scoped>

.button {
  padding: .9rem 1.5rem;
  border: .2rem solid #443;
  border-radius: 95% 4% 97% 5% / 4% 94% 3% 95%;
  color: #443;
  background: none;
  cursor: pointer;
  margin-top: 1rem;
  font-size: 1.7rem;
  transition: all .2s linear;
}

.button:hover {
  border-radius: 4% 95% 6% 95% / 95% 4% 92% 5%;
  border: .2rem dashed #443;
}

.all {
  height: 100vh;
  background: linear-gradient(to bottom, white, aqua);
  background-image: url("view-white-crumpled-paper.jpg");
}
h1 {
  font-size: 78px;
}
.container {
  position: relative;
  top: 200px;
}
.btn {
  background: white;
  padding: 20px 30px;
  margin: 20px;
}
.butns {
  margin-top: 80px;
}
.btn:hover {
  color: white;
  background: black;
}
.logo{
  font-family: Rubik Doodle Shadow;
  font-size: 100px;
}
</style>
