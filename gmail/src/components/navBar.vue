<template>
  <section class="d-flex">
    <input type="text" placeholder="search" />
    <button @click="sortMsgAsc">sortDec</button>
    <button @click="sortMsgDec">sortAsc</button>
    <button @click="sortMsgPri">sortpriority</button>
    <button @click="signOut">signOut</button>
  </section>
</template>

<script>
import $store from "../store/index.js";
export default {
  methods: {
    sortMsgAsc() {
      this.$store.commit("sortMsgAsc");
      console.log($store.state.currUser.inbox);
    },
    sortMsgDec() {
      this.$store.commit("sortMsgDec");
      console.log($store.state.currUser.inbox);
    },
    sortMsgPri() {
      this.$store.commit("sortMsgPri");
      console.log($store.state.currUser.inbox);
    },
    signOut() {
      localStorage.removeItem("token");
      localStorage.removeItem("userData");
      this.$store.commit("setLoginStatus", false);
      this.$store.commit("setCurrUser", null);
      this.$router.push({ name: "prehome" });
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
section {
  max-height: 35px;
  text-align: left;
  margin-left: -1px;
  width: 100%;
  justify-content: left;
}
input {
  margin-right: 5px;
  display: block;
  padding: 2px 1px;
  width: 50%;
  border: none;
  background-color: transparent;
  border-bottom: 2px solid #ddd;
  transition: all 0.1s ease-in;
  color: black;
}
</style>