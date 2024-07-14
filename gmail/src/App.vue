<template>
  <router-view/>
</template>
<script>
import $store from "./store/index.js";
export default {
}
</script>

<style>
#app {
  font-family:"Quicksand",Avenir, Helvetica, Arial, sans-serif;
  font-weight: 900;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  text-transform: capitalize;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>