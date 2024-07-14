<template>
  <div class="all">
    <section class="d-flex window">
      <sideBar />
      <div v-if="hasfolders" class="body flex-column">
        <div
          class="folder d-flex justify-content-between"
          v-for="fld in folders"
          :key="fld.foldername"
        >
          <p class="name flex-grow-1" @click="openfolder(fld)">
            {{ fld.foldername }}
          </p>
          <button @click="showrenameform(fld.folderindex)" class="rename">
            Rename folder
          </button>
          <div class="form" v-show="showrename[fld.folderindex]">
            <input
              class="input"
              type="text"
              placeholder="folder name"
              v-model="newname[fld.folderindex]"
            />
            <button @click="renamefolder(fld)" class = "rename">confirm</button>
          </div>
          <div class="trashbtn">
            <button class="button" @click="deletefolder(fld)">
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
        <div class="addfolder d-flex justify-content-around">
          <button class="addbutton" type="button" @click="showform">
            <span class="addbutton__text">Add folder</span>
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
          <div class="form" v-show="showf">
            <input
              placeholder="folder name"
              type="text"
              name="text"
              class="input"
              v-model="foldername"
            />

            <button class="createbutton" @click="createfolder">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M4.5 12h15m0 0l-6.75-6.75M19.5 12l-6.75 6.75"
                ></path>
              </svg>

              <div class="text">create</div>
            </button>
          </div>
        </div>
      </div>
      <div v-else class="body flex-column">
        <h1>No Folders</h1>
        <div class="addfolder d-flex justify-content-around">
          <button class="addbutton" type="button" @click="showform">
            <span class="addbutton__text">Add folder</span>
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
          <div class="form" v-show="showf">
            <input
              placeholder="folder name"
              type="text"
              name="text"
              class="input"
              v-model="foldername"
            />

            <button class="createbutton" @click="createfolder">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M4.5 12h15m0 0l-6.75-6.75M19.5 12l-6.75 6.75"
                ></path>
              </svg>

              <div class="text">create</div>
            </button>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import $store from "../store/index.js";
import sideBar from "@/components/sideBar.vue";
export default {
  components: {
    sideBar,
  },
  mounted() {
    setInterval(() => {
      this.folders =
        $store.state.currUser && $store.state.currUser.folders
          ? $store.state.currUser.folders
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
    for (let i = 0; i < $store.state.currUser.folders.length; i++) {
      this.showrename.push(false);
      this.newname.push("");
    }
  },
  data() {
    return {
      folders: null,
      showf: false,
      foldername: "",
      newname: [],
      showrename: [],
    };
  },
  computed: {
    hasfolders() {
      return this.folders && this.folders.length > 0;
    },
  },
  methods: {
    renamefolder(fld) {
      if (this.newname === "") return;
      fld.foldername = this.newname[fld.folderindex];
      $store.commit("rename", fld);
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
        this.newname[fld.folderindex] = "" ;
        this.showrename[fld.folderindex] = false ;
    },
    showrenameform(x) {
      this.showrename[x] = !this.showrename[x];
      this.newname[x] = "" ;
    },
    deletefolder(fld) {
      $store.commit("deletefolder", fld);
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
    showform() {
      this.showf = !this.showf;
      this.foldername = "" ;
    },
    createfolder() {
      for (let i = 0; i < this.folders.length; i++) {
        if (this.foldername === this.folders[i].foldername) {
          return;
        }
      }
      const newfolder = {
        messages: [],
        foldername: this.foldername,
        folderindex: this.folders.length,
      };
      console.log($store.state.currUser);
      $store.commit("createfolder", newfolder);
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
      this.foldername = "" ;
      this.showf=false ;
    },
    openfolder(fld) {
      this.$router.push({ name: "folder", params: { id: fld.folderindex } });
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
  overflow-x: hidden;
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
  height: 100vh;
}
.flex-column {
  height: 100vh;
}
.body > * {
  margin: 2px;
  margin-top: 5px;
}
.addfolder {
  width: 100%;
  padding: 0px 50px;
  padding-top: 10px;
  border-radius: 5px;
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

/*create*/

.createbutton {
  margin-left: 20px;
  background-color: #ffffff00;
  color: #323232;
  width: 8.5em;
  height: 2.9em;
  border: #858585 0.2em solid;
  border-radius: 11px;
  text-align: right;
  transition: all 0.6s ease;
}

.createbutton:hover {
  color: white;
  background-color: #323232;
  cursor: pointer;
}

.createbutton svg {
  width: 1.6em;
  margin: -0.2em 0.8em 1em;
  position: absolute;
  display: flex;
  transition: all 0.6s ease;
}

.createbutton:hover svg {
  transform: translateX(5px);
}

.text {
  margin: 0 1.5em;
}
.input {
  margin-left:5px ;
  margin-right:5px ;
  max-width: 220px;
  height: 45px;
  padding: 12px;
  border-radius: 12px;
  border: 1.5px solid lightgrey;
  outline: none;
  transition: all 0.3s cubic-bezier(0.19, 1, 0.22, 1);
  box-shadow: 0px 0px 20px -18px;
}

.input:hover {
  border: 2px solid lightgrey;
  box-shadow: 0px 0px 20px -17px;
}

.input:active {
  transform: scale(0.95);
}

.input:focus {
  border: 2px solid grey;
}

.folder {
  background-color: aliceblue;
  border-bottom: white 2px solid;
  width: 100%;
  text-align: left;
  padding: 0px 50px;
  padding-top: 10px;
  border-radius: 5px;
  height: 80px;
  padding: 20px;
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

.rename{
  margin: 2px ;
  padding: 5px;
  border-radius: 20px;
  color: white;
  background-color: #323232;
  font-weight: bold;
}
</style>