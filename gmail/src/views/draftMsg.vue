<template>
  <div class="msg">
    <div class="row msgBody flex-column">
      <div class="sender d-flex justify-content-between">
        <p @click="goTosend(msg)">{{ msg.title }}</p>
        <div class="title">
          <p @click="goTosend(msg)">{{ sentTo }}</p>
        </div>
        <div class="date">
          <p @click="goTosend(msg)">{{ msg.date }}</p>
        </div>
        <div class="delete d-flex">
          <label class="checkcontainer">
            <input type="checkbox" @click="choose" v-model="chosen" />
            <div class="checkmark"></div>
          </label>
          <div class="trashbtn">
            <button  class="button" @click="deleteMsg">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 69 14"
                class="svgIcon bin-top"
              >
                <g clip-path="url(#clip0_35_24)">
                  <path
                    fill="black"
                    d="M20.8232 2.62734L19.9948 4.21304C19.8224 4.54309 19.4808 4.75 19.1085 4.75H4.92857C2.20246 4.75 0 6.87266 0 9.5C0 12.1273 2.20246 14.25 4.92857 14.25H64.0714C66.7975 14.25 69 12.1273 69 9.5C69 6.87266 66.7975 4.75 64.0714 4.75H49.8915C49.5192 4.75 49.1776 4.54309 49.0052 4.21305L48.1768 2.62734C47.3451 1.00938 45.6355 0 43.7719 0H25.2281C23.3645 0 21.6549 1.00938 20.8232 2.62734ZM64.0023 20.0648C64.0397 19.4882 63.5822 19 63.0044 19H5.99556C5.4178 19 4.96025 19.4882 4.99766 20.0648L8.19375 69.3203C8.44018 73.0758 11.6746 76 15.5712 76H53.4288C57.3254 76 60.5598 73.0758 60.8062 69.3203L64.0023 20.0648Z"
                  ></path>
                </g>
                <defs>
                  <clipPath id="clip0_35_24">
                    <rect fill="white" height="14" width="69"></rect>
                  </clipPath>
                </defs>
              </svg>

              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 69 57"
                class="svgIcon bin-bottom"
              >
                <g clip-path="url(#clip0_35_22)">
                  <path
                    fill="black"
                    d="M20.8232 -16.3727L19.9948 -14.787C19.8224 -14.4569 19.4808 -14.25 19.1085 -14.25H4.92857C2.20246 -14.25 0 -12.1273 0 -9.5C0 -6.8727 2.20246 -4.75 4.92857 -4.75H64.0714C66.7975 -4.75 69 -6.8727 69 -9.5C69 -12.1273 66.7975 -14.25 64.0714 -14.25H49.8915C49.5192 -14.25 49.1776 -14.4569 49.0052 -14.787L48.1768 -16.3727C47.3451 -17.9906 45.6355 -19 43.7719 -19H25.2281C23.3645 -19 21.6549 -17.9906 20.8232 -16.3727ZM64.0023 1.0648C64.0397 0.4882 63.5822 0 63.0044 0H5.99556C5.4178 0 4.96025 0.4882 4.99766 1.0648L8.19375 50.3203C8.44018 54.0758 11.6746 57 15.5712 57H53.4288C57.3254 57 60.5598 54.0758 60.8062 50.3203L64.0023 1.0648Z"
                  ></path>
                </g>
                <defs>
                  <clipPath id="clip0_35_22">
                    <rect fill="white" height="57" width="69"></rect>
                  </clipPath>
                </defs>
              </svg>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- <div>
    <h1>{{ msg.title }}</h1>
    <p>{{ sentTo }}</p>
    <p>{{ msg.date }}</p>
    <p>{{ msg.message }}</p>
  </div> -->
</template>

<script>
import $store from "../store/index.js";
export default {
  props: ["msg"],
  mounted() {
    this.routename = this.$route.name;
    this.folderindex = parseInt(this.$route.path.replace("/folder/", ""));
    if ($store.state.selectedMsg > 0) {
      $store.state.currDraftMsg.sentToMails.map((ms, index) => {
        if ($store.state.currDraftMsg.sentToMails.length === 1) {
          this.sentto += ms;
        } else {
          if ($store.state.currDraftMsg.sentToMails.length - 1 === index) {
            this.sentto += ms;
          } else {
            this.sentto += ms + ",";
          }
        }
      });
    }
  },
  data() {
    return {
      messages: this.msg,
      sentTo: "",
      chosen:false ,
      routename: "",
      folderindex: null,
    };
  },
  methods: {
    choose() {
      if (!this.chosen) {
        console.log("choose");
        $store.commit("choose", {
          msg: this.msg,
          route: this.routename,
          index: this.folderindex,
        });
        console.log($store.state.draftmirror);
      } else {
        console.log("unchoose");
        $store.commit("unchoose", {
          msg: this.msg,
          route: this.routename,
          index: this.folderindex,
        });
        console.log($store.state.inboxmirror);
      }
    },
    goTosend(ms) {
      
      localStorage.setItem("willBeSentId",ms.id);
      console.log(localStorage.getItem("willBeSentId"));
      $store.commit("setCurrDraftMsg",ms);
      //$store.state.currDraftMsg.sentToMails = ms.sentToMails;

      $store.commit("setCurrMsg", ms);

      
      $store.commit("setHoldDraft", true);
      let x = $store.state.currDraftMsg.id;
      console.log(x);
      $store.commit("setSelectedMsg", x);
      console.log($store.state.selectedMsg);
      console.log(this.messages);
      this.$router.push({ name: "send" });
    },
    deleteMsg() {
      $store.commit("deleteMsg", this.msg);
      fetch(
        `http://localhost:8080/updateMessages/${$store.state.currUser.email}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify($store.state.currUser),
        }
      )
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        })
        .then((userData) => {
          $store.commit("setCurrUser", userData);
          console.log(userData);
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  box-sizing: border-box;
}
.msg {
  background-color: aliceblue;
  border-bottom: white 2px solid;
  width: 100%;
  text-align: left;
  padding: 0px 50px;
  padding-top: 10px;
  border-radius: 5px;
}
.msgBody {
  padding: 15px;
  max-height: 170px;
}
.msgBody p {
  margin: 0;
}
.msgBody .sender {
  margin-bottom: 10px;
}
.msgBody .title {
  margin-bottom: 10px;
}
.msgBody .title p {
  margin-bottom: 5px;
}

.msgBody .date {
  text-align: right;
}

.title {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.date {
  text-align: right;
}
.trash {
  padding-left: 10px;
  padding-right: 10px;
}
.button {
  margin-left: 20px;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: rgb(20, 20, 20);
  border: none;
  font-weight: 600;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.164);
  cursor: pointer;
  transition-duration: 0.3s;
  overflow: hidden;
  position: relative;
  gap: 2px;
}

.svgIcon {
  width: 12px;
  transition-duration: 0.3s;
}

.svgIcon path {
  fill: white;
}

.button:hover {
  transition-duration: 0.3s;
  background-color: rgb(255, 69, 69);
  align-items: center;
  gap: 0;
}

.bin-top {
  transform-origin: bottom right;
}
.button:hover .bin-top {
  transition-duration: 0.5s;
  transform: rotate(160deg);
}
.checkcontainer {
  max-height: 20px;
  margin: 5px;
  margin-right: 20px;
  --input-focus: rgba(255, 0, 0, 0.582);
  --input-out-of-focus: #ccc;
  --bg-color: #fff;
  --bg-color-alt: #666;
  --main-color: #323232;
  position: relative;
  cursor: pointer;
}

.checkcontainer input {
  position: absolute;
  opacity: 0;
}

.checkmark {
  width: 20px;
  height: 20px;
  position: relative;
  top: 0;
  left: 0;
  border: 2px solid var(--main-color);
  border-radius: 5px;
  box-shadow: 4px 4px var(--main-color);
  background-color: var(--input-out-of-focus);
  transition: all 0.3s;
}

.checkcontainer input:checked ~ .checkmark {
  background-color: var(--input-focus);
}

.checkmark:after {
  content: "";
  width: 7px;
  height: 10px;
  position: absolute;
  top: 1.5px;
  left: 4px;
  display: none;
  border: solid var(--bg-color);
  border-width: 0 2.5px 2.5px 0;
  transform: rotate(45deg);
}

.checkcontainer input:checked ~ .checkmark:after {
  display: block;
}

</style>