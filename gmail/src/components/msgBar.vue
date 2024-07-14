<template>
  <div
    class="msg"
    :style="{
      backgroundColor: msg.read ? 'white' : 'aliceblue',
      fontWeight: msg.read ? 'bold' : 'bolder',
    }"
  >
    <div class="row msgBody flex-column">
      <div @click="openMessage" class="overLay"></div>
      <div class="sender d-flex justify-content-between">
        <p @click="openMessage">{{ msg.title }}</p>
        <div class="actions d-flex">
          <label class="checkcontainer">
            <input type="checkbox" @click="choose" v-model="chosen" />
            <div class="checkmark"></div>
          </label>
          <div v-show="inTrash" class="yourrate">
            <i
              class="fa-solid fa-star"
              @mousedown="mousedown(1)"
              :style="{
                color:
                  usersRate > 0 ? 'rgba(255, 0, 0, 0.582)' : 'rgb(57, 59, 60)',
              }"
            ></i>
            <i
              class="fa-solid fa-star"
              @mousedown="mousedown(2)"
              :style="{
                color:
                  usersRate > 1 ? 'rgba(255, 0, 0, 0.582)' : 'rgb(57, 59, 60)',
              }"
            ></i>
            <i
              class="fa-solid fa-star"
              @mousedown="mousedown(3)"
              :style="{
                color:
                  usersRate > 2 ? 'rgba(255, 0, 0, 0.582)' : 'rgb(57, 59, 60)',
              }"
            ></i>
            <i
              class="fa-solid fa-star"
              @mousedown="mousedown(4)"
              :style="{
                color:
                  usersRate > 3 ? 'rgba(255, 0, 0, 0.582)' : 'rgb(57, 59, 60)',
              }"
            ></i>
            <i
              class="fa-solid fa-star"
              @mousedown="mousedown(5)"
              :style="{
                color:
                  usersRate > 4 ? 'rgba(255, 0, 0, 0.582)' : 'rgb(57, 59, 60)',
              }"
            ></i>
          </div>
          <div class="trashbtn">
            <button v-show="inTrash" class="button" @click="moveToTrash">
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
          <div v-show="!inTrash" class="trashbtn">
            <button class="Btn2" @click="restore">
              <div class="sign">
                <svg viewBox="0 0 512 512">
                  <path
                    d="M377.9 105.9L500.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L377.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1-128 0c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM160 96L96 96c-17.7 0-32 14.3-32 32l0 256c0 17.7 14.3 32 32 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32l-64 0c-53 0-96-43-96-96L0 128C0 75 43 32 96 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32z"
                  ></path>
                </svg>
              </div>

              <div class="text">Restore</div>
            </button>
          </div>
        </div>
      </div>
      <div class="title">
        <p @click="openMessage">{{ msg.sender }}</p>
      </div>
      <div class="dateandfolder d-flex justify-content-between">
        <div class="addtofolder d-flex flex-column" v-if="routename != 'trash'">
          <button class="addbutton" type="button" @click="showfs">
            <span class="addbutton__text">To folders</span>
            <span class="addbutton__icon"
              ><svg
                class="svg"
                fill="none"
                height="24"
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                viewBox="0 0 24 24"
                width="24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <line x1="12" x2="12" y1="5" y2="19"></line>
                <line x1="5" x2="19" y1="12" y2="12"></line></svg
            ></span>
          </button>
          <div class="folders d-flex flex-column" v-if="showfolders">
            <div
              class="flds fldmenu col-5 d-flex justify-content-between"
              v-for="fld in folders"
              :key="fld.foldername"
            >
              <label class="checkcontainer">
                <input type="checkbox" v-model="isChecked[fld.folderindex]" />
                <div class="checkmark"></div>
              </label>
              <label class="foldername flex-grow-1" for="myCheckbox">{{
                fld.foldername
              }}</label>
            </div>
          </div>
        </div>
        <div class="date">
          <p @click="openMessage">{{ msg.date }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $store from "../store/index.js";
export default {
  data() {
    return {
      rate: null,
      usersRate: this.msg.priorityLvl,
      clicked: false,
      inTrash: !$store.state.currUser.trash.includes(this.msg),
      showfolders: false,
      folders: $store.state.currUser.folders,
      isChecked: [],
      routename: "",
      folderindex: null,
      chosen: false,
    };
  },
  props: ["msg"],
  methods: {
    choose() {
      if (!this.chosen) {
        console.log("choose");
        $store.commit("choose", {
          msg: this.msg,
          route: this.routename,
          index: this.folderindex,
        });
        console.log($store.state.foldersmirror);
      } else {
        console.log("unchoose");
        $store.commit("unchoose", {
          msg: this.msg,
          route: this.routename,
          index: this.folderindex,
        });
        console.log($store.state.foldersmirror);
      }
    },
    showfs() {
      if (this.showfolders === true) {
        this.addtofolders();
        this.isChecked.fill(false);
      }
      this.showfolders = !this.showfolders;
    },
    msginfolder(id, index) {
      for (let i = 0; i < this.folders[index].messages.length; i++) {
        if (this.folders[index].messages[i].id === id) {
          return true;
        }
      }
      return false;
    },
    addtofolders() {
      for (let i = 0; i < this.folders.length; i++) {
        if (this.isChecked[i] === true) {
          if (!this.msginfolder(this.msg.id, i)) {
            let msgs = JSON.parse(JSON.stringify(this.msg));
            msgs.priorityLvl = 0;
            $store.state.currUser.folders[i].messages.push(msgs);
          } else {
            console.log("tmam");
          }
        }
      }
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
    openMessage() {
      this.msg.read = true;
      $store.commit("updateMsg", this.msg);
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
      this.$router.push({ name: "message", params: { id: this.msg.id } });
    },
    deletemsgfromfolder() {
      let msg = {
        index: this.folderindex,
        message: this.msg,
      };
      $store.commit("deletemsgfromfolder", msg);
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
    moveToTrash() {
      if (this.routename === "folder") {
        this.deletemsgfromfolder();
        return;
      }
      $store.commit("moveToTrash", this.msg);
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
    mousedown(index) {
      if (index === this.usersRate) {
        this.usersRate = 0;
        this.clicked = false;
      } else {
        this.usersRate = index;
        this.clicked = true;
      }
      this.updatepriority();
      if ($store.state.srtPriority === true) {
        $store.commit("sortMsgPri");
      }
      console.log($store.state.currUser);
    },
    restore() {
      $store.commit("restore", this.msg);
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
    updatepriority() {
      $store.commit("updatepriority", {
        msg: this.msg,
        usersRate: this.usersRate,
        route: this.routename,
        index: this.folderindex,
      });
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
      console.log($store.currUser);
    },
  },
  mounted() {
    this.routename = this.$route.name;
    this.folderindex = parseInt(this.$route.path.replace("/folder/", ""));
    console.log(this.routename);
    console.log(this.$route.path);
    console.log(this.folderindex);
    for (let i = 0; i < this.folders.length; i++) {
      this.isChecked.push(false);
    }
    console.log(this.isChecked);
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
  max-height: 10000px;
  position: relative;
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
.Btn2 {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 45px;
  height: 45px;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition-duration: 0.3s;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.199);
  background-color: rgb(255, 65, 65);
}

/* plus sign */
.sign {
  width: 100%;
  transition-duration: 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.sign svg {
  width: 17px;
}

.sign svg path {
  fill: white;
}
/* text */
.text {
  position: absolute;
  right: 0%;
  width: 0%;
  opacity: 0;
  color: white;
  font-size: 1.2em;
  font-weight: 600;
  transition-duration: 0.3s;
}
/* hover effect on button width */
.Btn2:hover {
  width: 125px;
  border-radius: 40px;
  transition-duration: 0.3s;
}

.Btn2:hover .sign {
  width: 30%;
  transition-duration: 0.3s;
  padding-left: 20px;
}
/* hover effect button's text */
.Btn2:hover .text {
  opacity: 1;
  width: 70%;
  transition-duration: 0.3s;
  padding-right: 10px;
}
/* button click effect*/
.Btn2:active {
  transform: translate(2px, 2px);
}

/* checkbox */
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

/* add button */
.addbutton {
  --main-focus: #2d8cf0;
  --font-color: #323232;
  --bg-color-sub: #dedede;
  --bg-color: #eee;
  --main-color: #323232;
  position: relative;
  width: 150px;
  height: 40px;
  cursor: pointer;
  display: flex;
  align-items: center;
  border: 2px solid var(--main-color);
  box-shadow: 4px 4px var(--main-color);
  background-color: var(--bg-color);
  border-radius: 10px;
  overflow: hidden;
}

.addbutton,
.addbutton__icon,
.addbutton__text {
  transition: all 0.3s;
}

.addbutton .addbutton__text {
  transform: translateX(22px);
  color: var(--font-color);
  font-weight: 600;
}

.addbutton .addbutton__icon {
  position: absolute;
  transform: translateX(109px);
  height: 100%;
  width: 39px;
  background-color: var(--bg-color-sub);
  display: flex;
  align-items: center;
  justify-content: center;
}

.addbutton .svg {
  width: 20px;
  fill: var(--main-color);
}

.addbutton:hover {
  background: var(--bg-color);
}

.addbutton:hover .addbutton__text {
  color: transparent;
}

.addbutton:hover .addbutton__icon {
  width: 148px;
  transform: translateX(0);
}

.addbutton:active {
  transform: translate(3px, 3px);
  box-shadow: 0px 0px var(--main-color);
}
.folders {
  background: inherit;
}
.folders {
  margin-top: 40px;
  position: absolute;
  width: 500px;
}
.foldername {
  word-break: break-all;
}
.fldmenu {
  margin: 1px;
  position: relative;
  height: auto;
  z-index: 2;
  border-radius: 10px;
  outline: none;
  border-color: rgba(255, 0, 0, 0.582);
  background-color: #fff;
  box-shadow: 0 0 0 2px rgb(255, 222, 222);
  align-self: start;
}
.overLay {
  position: absolute;
  top: 0;
  left: 0;
  width: 75%;
  height: 100%;
}
</style>