<template>
  <section class="all">
    <div class="container">
      <div class="txt">
        <h1>Welcome to</h1>
        <h4>Join us in an impressive experience</h4>
      </div>
      <div class="butns">
        <router-link to="/signup"><button class="btn">signUp</button></router-link>
        <router-link to="/login"><button class="btn">login</button></router-link>
      </div>
    </div>
  </section>
</template>

<script>
// @ is an alias to /src

export default {};
</script>

<style scoped>
.all {
  height: 723px;
  background: linear-gradient(to bottom, white, aqua);
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
</style>
