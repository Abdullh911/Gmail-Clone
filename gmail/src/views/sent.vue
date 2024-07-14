<template>
  <div class="all">
    <section class="d-flex window">
      <sideBar />
      <div v-if="hasMessages" class="body flex-column">
        <div class="pagination">
          <button @click="prevPage" :disabled="currentPage === 1" class="bttn">
            <i class="fa-solid fa-arrow-left"></i>
          </button>
          <span>{{ currentPage }}</span>
          <button @click="nextPage" :disabled="currentPage === totalPages" class="bttn">
            <i class="fa-solid fa-arrow-right"></i>
          </button>
        </div>
        <msgBar v-for="msg in displayedMessages" :key="msg.id" :msg="msg" />
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
export default {
  components: {
    sideBar,
    msgBar,
  },
  mounted() {
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
    $store.commit("setSrtPriority", false);
    setInterval(() => {
      this.messages =
        $store.state.currUser && $store.state.currUser.sent
          ? $store.state.currUser.sent
          : null;
    }, 50);
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
  // updated() {
  //   this.messages = $store.state.currUser.sent;
  // },
  data() {
    return {
      messages: null,
      currentPage: 1,
      pageSize: 4,
    };
  },
  methods: {
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
  },
  computed: {
    hasMessages() {
      return this.messages && this.messages.length > 0;
    },
    totalPages() {
      if(!$store.state.signOutClicked){
      const totalMessages = this.$store.state.currUser.sent
        ? this.$store.state.currUser.sent.length
        : 0;
      return Math.ceil(totalMessages / this.pageSize);}
    },
    displayedMessages() {
      if(!$store.state.signOutClicked){
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.$store.state.currUser.sent.slice(start, end);}
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
.window {
  height: 100vh;
}
.flex-column {
  height: 100vh;
}
.pagination {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
}
.pagination button {
  cursor: pointer;
}
.bttn{
  font-size: 1.5rem;
  border-radius: 20%;
}
</style>