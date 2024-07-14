<template>
  <div class="all">
    <section class="d-flex window">
      <sideBar />
      <div v-if="messages.length !== 0" class="body flex-column">
        <draftMsg
          
          v-for="msg in messages"
          :key="msg.id"
          :msg="msg"
        />
      </div>
      <div v-else class="body flex-column">
        <h1>No messages</h1>
      </div>
    </section>
  </div>
</template>

<script>
import $store from "../store/index.js";
import msgBar from "@/components/msgBar.vue";
import sideBar from "@/components/sideBar.vue";
import draftMsg from "./draftMsg.vue";

export default {
  components: {
    sideBar,
    msgBar,
    draftMsg,
  },
  mounted(){
    location.reload();
    const userDataString = localStorage.getItem("userData");
    if (!userDataString) {
      $store.commit("setLoginStatus", false);
      $store.commit("setCurrUser", null);
    } else {
      $store.commit("setLoginStatus", true);
      let em = JSON.parse(userDataString).email;
      let newData;
      fetch(`http://localhost:8080/users/${em}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        })
        .then((userData) => {
          console.log(userData);
          newData = userData;
          $store.commit("setSearch", localStorage.getItem("search"));
          $store.commit("setSearchType", localStorage.getItem("searchType"));
          setTimeout(() => {
            $store.commit("searchMsg", $store.state.search);
          }, 0);
          $store.commit("setCurrUser", newData);
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
    }
  },
  mounted() {
    setInterval(() => {
      this.messages =
        $store.state.currUser && $store.state.currUser.draft
          ? $store.state.currUser.draft
          : [];
    }, 100);
    const userDataString = localStorage.getItem("userData");
    if (!userDataString) {
      $store.commit("setLoginStatus", false);
      $store.commit("setCurrUser", null);
    } else {
      $store.commit("setLoginStatus", true);
      let em = JSON.parse(userDataString).email;
      let newData;
      fetch(`http://localhost:8080/users/${em}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        })
        .then((userData) => {
          console.log(userData);
          newData = userData;
          $store.commit("setSearch", localStorage.getItem("search"));
          $store.commit("setSearchType", localStorage.getItem("searchType"));
          setTimeout(() => {
            $store.commit("searchMsg", $store.state.search);
          }, 0);
          $store.commit("setCurrUser", newData);
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
    }
  },
  methods: {
  },
  data() {
    return {
      messages: [],
    };
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.body {
  background-color: #ebe6ef;
  width: 100%;
  height: 650px;
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 3px 0px 14px 0px #00000086;
  overflow-y: scroll;
}
.all {
  height: 100%;
  background-color: rgb(203, 220, 247);
}
.window {
  padding-top: 0px;
  display: flex;
  flex-flow: row;
  justify-content: center;
  width: 100%;
  margin: auto;
  border-radius: 50px;
  
}
.body > * {
  margin: 2px;
  margin-top: 5px;
}
.window{
  height: 100vh;
}
.flex-column{
  height: 100vh;
}
</style>