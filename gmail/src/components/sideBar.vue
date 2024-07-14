<template>
  <section class="flex-column">
    <ul class="nav flex-column">
      <li class="nav-item">
        <div class="user">
          <i class="fa-solid fa-user"></i>
          <h4>{{userEmail}}</h4>
        </div>
        <br>
        
          <div class="sendBtn">
            <button @click="reload" class="bt">
              <span class="span">↻</span>
            </button>
            <router-link class="nav-link" to="/send">
            <button class="animated-button">
              <svg
                viewBox="0 0 24 24"
                class="arr-2"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                ></path>
              </svg>
              <span class="text">Compose</span>
              <span class="circle"></span>
              <svg
                viewBox="0 0 24 24"
                class="arr-1"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M16.1716 10.9999L10.8076 5.63589L12.2218 4.22168L20 11.9999L12.2218 19.778L10.8076 18.3638L16.1716 12.9999H4V10.9999H16.1716Z"
                ></path>
              </svg>
            </button>
          </router-link>
          </div>
      </li>
      <div class="bts">
        <li>
          <button
            class="topBts"
            @click="deletechoosen"
            v-show="routename == 'inbox' || routename == 'sent'"
          >
            delete
          </button>
        </li>
        <li>
          <button
            class="topBts2"
            v-show="
              routename != 'inbox' &&
              routename != 'sent' &&
              routename != 'draft' &&
              routename != 'folder' &&
              routename != 'trash'
            "
          >
            delete
          </button>
        </li>
        <li>
          <button
            class="topBts"
            @click="removechoosen"
            v-show="routename == 'draft' || routename == 'folder'"
          >
            delete
          </button>
        </li>
        <li>
          <button
            class="topBts"
            @click="restorechoosen"
            v-show="routename == 'trash'"
          >
            restore
          </button>
        </li>
        <li>
          <button
            class="topBts"
            v-show="
              routename == 'inbox' ||
              routename == 'sent' ||
              routename == 'folder'
            "
            @click="showfs"
          >
            addtofolder
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
        </li>
        <li>
          <button
            class="topBts2"
            v-show="
              routename != 'inbox' &&
              routename != 'sent' &&
              routename != 'folder'
            "
          >
            addtofolder
          </button>
        </li>
      </div>
      <li class="nav-item">
        <router-link class="nav-link active" to="/inbox">
          <div class="row">
            <div class="col-3">
              <i class="fa-regular fa-envelope"></i>
            </div>
            <div class="col-6">Inbox</div>
            <div class="col-3">
              <div class="col-3">
                {{
                  $store.state.currUser && $store.state.currUser.inbox
                    ? $store.state.currUser.inbox.length
                    : 0
                }}
              </div>
            </div>
          </div>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link class="nav-link" to="/draft">
          <div class="row">
            <div class="col-3">
              <i class="fa-solid fa-file-contract"></i>
            </div>
            <div class="col-6">Draft</div>
            <div class="col-3">
              <div class="col-3">
                {{
                  $store.state.currUser && $store.state.currUser.draft
                    ? $store.state.currUser.draft.length
                    : 0
                }}
              </div>
            </div>
          </div>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link class="nav-link" to="/sent">
          <div class="row">
            <div class="col-3">
              <i class="fa-solid fa-envelope-open-text"></i>
            </div>
            <div class="col-6">Sent</div>
            <div class="col-3">
              <div class="col-3">
                {{
                  $store.state.currUser && $store.state.currUser.sent
                    ? $store.state.currUser.sent.length
                    : 0
                }}
              </div>
            </div>
          </div>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link class="nav-link" to="/trash">
          <div class="row">
            <div class="col-3">
              <i class="fa-solid fa-trash"></i>
            </div>
            <div class="col-6">Trash</div>
            <div class="col-3">
              <div class="col-3">
                {{
                  $store.state.currUser && $store.state.currUser.trash
                    ? $store.state.currUser.trash.length
                    : 0
                }}
              </div>
            </div>
          </div>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link class="nav-link active" to="/allfolders">
          <div class="row">
            <div class="col-3">
              <i class="fa-solid fa-folder"></i>
            </div>
            <div class="col-6">Folders</div>
            <div class="col-3">
              <div class="col-3">
                {{
                  $store.state.currUser && $store.state.currUser.inbox
                    ? $store.state.currUser.folders.length
                    : 0
                }}
              </div>
            </div>
          </div>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link to="/contacts" class="nav-link active">
          <div class="row">
            <div class="col-3">
              <i class="fa-solid fa-address-book"></i>
            </div>
            <div class="col-6">Contacts</div>
            <div class="col-3">
              <div class="col-3">
                
              </div>
            </div>
          </div>
        </router-link>
      </li>
      <li class="nav-item2">
        <div class="row">
          <div class="col-12">
            <div class="col-9">
              <div class="group">
                <svg class="icon" aria-hidden="true" viewBox="0 0 24 24">
                  <g>
                    <path
                      d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"
                    ></path>
                  </g>
                </svg>
                <div>
                  <div>
                    <input
                      placeholder="Search"
                      type="search"
                      class="input"
                      v-model="search"
                      @input="searchMsg"
                    />
                  </div>
                </div>
                <div class="col-2 dropdown">
                  <select v-model="searchType" class="menu">
                    <option value="title">title</option>
                    <option value="date">date</option>
                    <option value="sender">sender</option>
                    <option value="message">message</option>
                    <option value="attachment">attachment</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
      </li>
      <li class="radiobtns">
        <div class="container">
          <form>
            <label>
              <input type="radio" name="radio" checked="" @click="sortMsgDec" />
              <span>time desc</span>
            </label>
            <label>
              <input type="radio" name="radio" @click="sortMsgAsc" />
              <span>time asc</span>
            </label>
            <label>
              <input type="radio" name="radio" @click="sortMsgPri" />
              <span>priority</span>
            </label>
          </form>
        </div>
        
      </li>

      <li class="nav-item2">
        <div class="row">
          <div class="col-12">
            <div class="col-12">
              <button @click="signOut" class="button type1"></button>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </section>
</template>

<script>
import msgBar from "./msgBar.vue";
import $store from "../store/index.js";
export default {
  components: {
    msgBar,
  },
  data() {
    return {
      contact: "",
      messages: {
        inbox: [],
        draft: [],
        sent: [],
        trash: [],
      },
      currUser: { inbox: [], draft: [], sent: [], trash: [] },
      search: "",
      searchType: "",
      userContacts: [],
      routename: "",
      folderindex: null,
      addOrEdit: "Add",
      isDropdownOpen: false,
      willBeEdited: "",
      showfolders: false,
      folders: $store.state.currUser.folders,
      isChecked: [],
      userEmail:""
    };
  },
  mounted() {
    this.userEmail=$store.state.currUser.email;
    for (let i = 0; i < this.folders.length; i++) {
      this.isChecked.push(false);
    }
    $store.commit("clear");
    this.routename = this.$route.name;
    this.folderindex = parseInt(this.$route.path.replace("/folder/", ""));
    $store.commit("setSearch", localStorage.getItem("search"));
    this.search = $store.state.search;
    $store.commit("setSearchType", localStorage.getItem("searchType"));
    this.searchType = $store.state.searchType;
    setInterval(() => {
      if (this.$store.state.currUser && this.$store.state.currUser.inbox) {
        this.messages = { ...this.$store.state.currUser };
      } else {
        this.messages = { inbox: [], draft: [], sent: [], trash: [] };
      }
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
          this.userContacts = userData.contacts;
          console.log(this.userContacts);
          $store.commit("setCurrUser", newData);
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
      this.sortMsgDec;
    }
  },
  methods: {
    reload(){
      location.reload();
    },
    handleDocumentClick(event) {
      const isInputOrButton =
        event.target.classList.contains("inp") ||
        event.target.classList.contains("button");
      if (!isInputOrButton) {
        this.addOrEdit = "Add";
        location.reload();
      }
    },
    changeDrop() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    changeAdd(x) {
      this.willBeEdited = x;
      this.addOrEdit = "Edit";
      this.isDropdownOpen = false;
      setTimeout(() => {
        this.$refs.adding.focus();
      }, 500);
      setTimeout(() => {
        document.addEventListener("click", this.handleDocumentClick);
      }, 500);
    },
    editContact() {
      let userEmail = $store.state.currUser.email;
      if (this.contact.length !== 0) {
        console.log("contact is added");
        fetch(
          `http://localhost:8080/editContact/${userEmail}/${this.willBeEdited}/${this.contact}`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
          }
        )
          .then((response) => {
            if (!response.ok) {
              throw new Error("Failed to send mail");
            }
            this.$router.push({ name: "inbox" });
            return response.json();
          })
          .then((data) => {
            console.log("Server response:", data);
          })
          .catch((error) => {
            console.error("Error:", error.message);
          });
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
              this.userContacts = userData.contacts;
              newData = userData;
              $store.commit("setCurrUser", newData);
            })
            .catch((error) => {
              console.error("Error during login:", error);
            });
        }
      }
      this.addOrEdit = "Add";
    },
    removeContact(cont) {
      let userEmail = $store.state.currUser.email;
      console.log("contact is added");
      fetch(`http://localhost:8080/removeContact/${userEmail}/${cont}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to send mail");
          }
          this.$router.push({ name: "inbox" });
          return response.json();
        })
        .then((data) => {
          console.log("Server response:", data);
        })
        .catch((error) => {
          console.error("Error:", error.message);
        });
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
            this.userContacts = userData.contacts;
            newData = userData;
            $store.commit("setCurrUser", newData);
          })
          .catch((error) => {
            console.error("Error during login:", error);
          });
      }
    },
    getmsgbyid(id, place) {
      if (place === "inbox") {
        for (let i = 0; i < $store.state.currUser.inbox.length; i++) {
          if (id === $store.state.currUser.inbox[i].id) {
            return $store.state.currUser.inbox[i];
          }
        }
      } else if (place === "trash") {
        for (let i = 0; i < $store.state.currUser.trash.length; i++) {
          if (id === $store.state.currUser.trash[i].id) {
            return $store.state.currUser.trash[i];
          }
        }
      } else if (place === "sent") {
        for (let i = 0; i < $store.state.currUser.sent.length; i++) {
          if (id === $store.state.currUser.sent[i].id) {
            return $store.state.currUser.sent[i];
          }
        }
      } else if (place === "draft") {
        for (let i = 0; i < $store.state.currUser.draft.length; i++) {
          if (id === $store.state.currUser.draft[i].id) {
            return $store.state.currUser.draft[i];
          }
        }
      } else if (place === "folder") {
        for (
          let i = 0;
          i < $store.state.currUser.folders[this.folderindex].messages.length;
          i++
        ) {
          if (
            id ===
            $store.state.currUser.folders[this.folderindex].messages[i].id
          ) {
            return $store.state.currUser.folders[this.folderindex].messages[i];
          }
        }
      }
      return null;
    },
    showfs() {
      if (this.showfolders === true) {
        this.movechoosen();
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
    addtofolders(msg) {
      for (let i = 0; i < this.folders.length; i++) {
        if (this.isChecked[i] === true) {
          if (!this.msginfolder(msg.id, i)) {
            let msgs = JSON.parse(JSON.stringify(msg));
            $store.state.currUser.folders[i].messages.push(msgs);
          } else {
            console.log("tmam");
          }
        }
      }
    },
    movechoosen() {
      if (this.routename === "inbox") {
        for (let i = 0; i < $store.state.inboxmirror.length; i++) {
          this.addtofolders(
            this.getmsgbyid($store.state.inboxmirror[i], "inbox")
          );
        }
        $store.state.inboxmirror = [];
      } else if (this.routename === "sent") {
        for (let i = 0; i < $store.state.sentmirror.length; i++) {
          this.addtofolders(
            this.getmsgbyid($store.state.sentmirror[i], "sent")
          );
        }
        $store.state.sentmirror = [];
      } else if (this.routename === "folder") {
        for (let i = 0; i < $store.state.foldersmirror.length; i++) {
          let msg = this.getmsgbyid($store.state.foldersmirror[i], "folder");
          this.addtofolders(msg);
        }
        $store.state.draftmirror = [];
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
    removechoosen() {
      if (this.routename === "draft") {
        for (let i = 0; i < $store.state.draftmirror.length; i++) {
          $store.commit(
            "deleteMsg",
            this.getmsgbyid($store.state.draftmirror[i], "draft")
          );
        }
        $store.state.draftmirror = [];
      } else if (this.routename === "folder") {
        for (let i = 0; i < $store.state.foldersmirror.length; i++) {
          let msg = {
            index: this.folderindex,
            message: this.getmsgbyid($store.state.foldersmirror[i], "folder"),
          };
          $store.commit("deletemsgfromfolder", msg);
        }
        $store.state.draftmirror = [];
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
    restorechoosen() {
      for (let i = 0; i < $store.state.trashmirror.length; i++) {
        $store.commit(
          "restore",
          this.getmsgbyid($store.state.trashmirror[i], "trash")
        );
      }
      $store.state.trashmirror = [];
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
    deletechoosen() {
      if (this.routename === "inbox") {
        for (let i = 0; i < $store.state.inboxmirror.length; i++) {
          $store.commit(
            "moveToTrash",
            this.getmsgbyid($store.state.inboxmirror[i], "inbox")
          );
        }
        $store.state.inboxmirror = [];
      } else if (this.routename === "sent") {
        for (let i = 0; i < $store.state.sentmirror.length; i++) {
          $store.commit(
            "moveToTrash",
            this.getmsgbyid($store.state.sentmirror[i], "sent")
          );
        }
        $store.state.sentmirror = [];
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
    addContact() {
      let userEmail = $store.state.currUser.email;
      if (this.contact.length !== 0) {
        console.log("contact is added");
        fetch(`http://localhost:8080/addContact/${userEmail}/${this.contact}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Failed to send mail");
            }
            this.$router.push({ name: "inbox" });
            return response.json();
          })
          .then((data) => {
            console.log("Server response:", data);
          })
          .catch((error) => {
            console.error("Error:", error.message);
          });
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
              this.userContacts = userData.contacts;
              newData = userData;
              $store.commit("setCurrUser", newData);
            })
            .catch((error) => {
              console.error("Error during login:", error);
            });
        }
      }
    },
    goSend(x) {
      $store.commit("setWantedContact", x);
      console.log(x);
      this.$router.push({ name: "send" });
    },
    sortMsgAsc() {
      $store.commit("setSrtPriority", false);
      this.$store.commit("sortMsgAsc");
      console.log($store.state.currUser.inbox);
    },
    sortMsgDec() {
      $store.commit("setSrtPriority", false);
      this.$store.commit("sortMsgDec");
      console.log($store.state.currUser.inbox);
    },
    sortMsgPri() {
      $store.commit("setSrtPriority", true);
      this.$store.commit("sortMsgPri");
      console.log($store.state.currUser.inbox);
    },
    signOut() {
      this.$store.commit("setSignOut", true);
      localStorage.removeItem("token");
      localStorage.removeItem("userData");
      this.$store.commit("setLoginStatus", false);
      this.$store.commit("setCurrUser", null);
      
      this.$router.push({ name: "prehome" });
    },
    searchMsg() {
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
            $store.commit("setSearch", this.search);
            $store.commit("setSearchType", this.searchType);
            const fakeToken = "your-fake-token";
            localStorage.setItem("search", $store.state.search);
            localStorage.setItem("searchType", $store.state.searchType);
            localStorage.setItem("token", fakeToken);
            $store.commit("setCurrUser", newData);
            $store.commit("searchMsg", localStorage.getItem("search"));
          })
          .catch((error) => {
            console.error("Error during login:", error);
          });
      }
    },
  },
};
</script>
<style scoped>
.nav {
  background-color: rgb(167, 178, 194);
  padding: 10px;
  width: 220px;
  height: 650px;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: -3px 0px 14px 0px #00000086;
}
.nav a {
  font-weight: 700px;
  font-size: 18px;
  color: rgba(0, 0, 0, 0.315);
}
.edit:hover {
  background-color: black;
}
.cntct:hover {
  background-color: black;
}
.remove:hover {
  background-color: black;
}
.nav a.router-link-exact-active {
  color: black;
}
.bts {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-evenly;
  margin-bottom: 20px;
}
.butn {
  margin-bottom: 20px;
  background-color: rgba(255, 0, 0, 0.582);
  padding: 5px 20px;
  border: none;
  border-radius: 20px;
  color: white;
}

.nav-item2 {
  padding: 10px;
  padding-top: 15px;
  padding-bottom: 0px;
}
.button {
  height: 50px;
  width: 150px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.5s ease-in-out;
}
.flex-column {
  height: 100%;
}

.button:hover {
  box-shadow: 0.5px 0.5px 150px #252525;
}

.type1::after {
  content: "BYE :(";
  height: 50px;
  width: 150px;
  background-color: #33455b;
  color: #fff;
  position: absolute;
  top: 0%;
  left: 0%;
  transform: translateY(50px);
  font-size: 1.2rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.5s ease-in-out;
}

.type1::before {
  content: "sign out";
  height: 50px;
  width: 150px;
  background-color: rgba(255, 0, 0, 0.582);
  color: white;
  position: absolute;
  top: 0%;
  left: 0%;
  transform: translateY(0px) scale(1.2);
  font-size: 1.2rem;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.5s ease-in-out;
}

.type1:hover::after {
  transform: translateY(0) scale(1.2);
}
.menu {
  height: 40px;
  position: relative;
  bottom: 3px;
  z-index: 1;
}

.type1:hover::before {
  transform: translateY(-50px) scale(0) rotate(120deg);
}
.flex-column {
  width: 350px;
  height: max-content;
  min-height: 100vh;
}
.sendBtn {
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  margin-bottom: 20px;
}
.animated-button {
  margin-left: 25px;
  position: relative;
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 16px 36px;
  padding-top: 10px;
  padding-bottom: 10px;
  border: 4px solid;
  border-color: transparent;
  font-size: 16px;
  background-color: white;
  border-radius: 100px;
  font-weight: 600;
  color: rgba(255, 0, 0, 0.582);
  box-shadow: 0 0 0 2px rgba(255, 0, 0, 0.582);
  cursor: pointer;
  overflow: hidden;
  transition: all 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}

.animated-button svg {
  position: absolute;
  width: 24px;
  fill: rgba(255, 0, 0, 0.582);
  z-index: 9;
  transition: all 0.8s cubic-bezier(0.23, 1, 0.32, 1);
}

.animated-button .arr-1 {
  right: 16px;
}

.animated-button .arr-2 {
  left: -25%;
}

.animated-button .circle {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 20px;
  height: 20px;
  background-color: rgba(255, 0, 0, 0.582);
  border-radius: 50%;
  opacity: 0;
  transition: all 0.8s cubic-bezier(0.23, 1, 0.32, 1);
}

.animated-button .text {
  position: relative;
  z-index: 1;
  transform: translateX(-12px);
  transition: all 0.8s cubic-bezier(0.23, 1, 0.32, 1);
}

.animated-button:hover {
  box-shadow: 0 0 0 12px transparent;
  color: #212121;
  border-radius: 12px;
}

.animated-button:hover .arr-1 {
  right: -25%;
}

.animated-button:hover .arr-2 {
  left: 16px;
}

.animated-button:hover .text {
  transform: translateX(12px);
}

.animated-button:hover svg {
  fill: #212121;
}

.animated-button:active {
  scale: 0.95;
  box-shadow: 0 0 0 4px rgba(255, 0, 0, 0.582);
}

.animated-button:hover .circle {
  width: 220px;
  height: 220px;
  opacity: 1;
}
.group {
  display: flex;
  line-height: 28px;
  align-items: center;
  position: relative;
  max-width: 190px;
}

.input {
  width: 180px;
  height: 40px;
  line-height: 28px;
  padding: 0 1rem;
  padding-left: 2.5rem;
  border: 2px solid transparent;
  border-radius: 8px;
  outline: none;
  background-color: #f3f3f4;
  color: #0d0c22;
  transition: 0.3s ease;
}

.input::placeholder {
  color: #9e9ea7;
}

.input:focus,
input:hover {
  outline: none;
  border-color: rgba(234, 76, 137, 0.4);
  background-color: #fff;
  box-shadow: 0 0 0 4px rgb(234 76 137 / 10%);
}

.icon {
  position: absolute;
  left: 1rem;
  fill: #9e9ea7;
  width: 1rem;
  height: 1rem;
}
.container form {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
}

.container label {
  display: flex;
  cursor: pointer;
  font-weight: 500;
  position: relative;
  overflow: hidden;
  margin-bottom: 0.375em;
}

.container label input {
  position: absolute;
  left: -9999px;
}
.contactss {
  display: flex;
  flex-flow: row wrap;
  justify-content: left;
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: 0px;
  gap: 20px;
}
.cnt {
  font-size: xx-large;
}
.dropCnt {
  display: flex;
  flex-flow: row wrap;
  align-items: center;
}
.container label input:checked + span {
  background-color: rgba(255, 0, 0, 0.582);
  color: white;
}

.container label input:checked + span:before {
  box-shadow: inset 0 0 0 0.4375em rgba(247, 0, 0, 0.432);
}

.container label span {
  display: flex;
  align-items: center;
  padding: 0.375em 0.75em 0.375em 0.375em;
  border-radius: 99em;
  transition: 0.25s ease;
  color: rgba(255, 0, 0, 0.582);
}

.container label span:hover {
  background-color: #d6d6e5;
}

.container label span:before {
  display: flex;
  flex-shrink: 0;
  content: "";
  background-color: #fff;
  width: 1.5em;
  height: 1.5em;
  border-radius: 50%;
  margin-right: 0.375em;
  transition: 0.25s ease;
  box-shadow: inset 0 0 0 0.125em rgba(55, 0, 0, 0.582);
}

/* The design is inspired from the mockapi.io */

.popup {
  --burger-line-width: 1.125em;
  --burger-line-height: 0.125em;
  --burger-offset: 0.625em;
  --burger-bg: rgba(0, 0, 0, 0.15);
  --burger-color: #333;
  --burger-line-border-radius: 0.1875em;
  --burger-diameter: 2.125em;
  --burger-btn-border-radius: calc(var(--burger-diameter) / 2);
  --burger-line-transition: 0.3s;
  --burger-transition: all 0.1s ease-in-out;
  --burger-hover-scale: 1.1;
  --burger-active-scale: 0.95;
  --burger-enable-outline-color: var(--burger-bg);
  --burger-enable-outline-width: 0.125em;
  --burger-enable-outline-offset: var(--burger-enable-outline-width);
  /* nav */
  --nav-padding-x: 0.25em;
  --nav-padding-y: 0.625em;
  --nav-border-radius: 0.375em;
  --nav-border-color: #ccc;
  --nav-border-width: 0.0625em;
  --nav-shadow-color: rgba(0, 0, 0, 0.2);
  --nav-shadow-width: 0 1px 5px;
  --nav-bg: #eee;
  --nav-font-family: Menlo, Roboto Mono, monospace;
  --nav-default-scale: 0.8;
  --nav-active-scale: 1;
  --nav-position-left: 0;
  --nav-position-right: unset;
  /* if you want to change sides just switch one property */
  /* from properties to "unset" and the other to 0 */
  /* title */
  --nav-title-size: 0.625em;
  --nav-title-color: #777;
  --nav-title-padding-x: 1rem;
  --nav-title-padding-y: 0.25em;
  /* nav button */
  --nav-button-padding-x: 1rem;
  --nav-button-padding-y: 0.375em;
  --nav-button-border-radius: 0.375em;
  --nav-button-font-size: 12px;
  --nav-button-hover-bg: #6495ed;
  --nav-button-hover-text-color: #fff;
  --nav-button-distance: 0.875em;
  /* underline */
  --underline-border-width: 0.0625em;
  --underline-border-color: #ccc;
  --underline-margin-y: 0.3125em;
  z-index: 2;
}

/* popup settings 👆 */

.popup {
  display: inline-block;
  text-rendering: optimizeLegibility;
  position: relative;
}

.popup input {
  display: none;
}

.burger {
  display: flex;
  position: relative;
  align-items: center;
  justify-content: center;
  background: var(--burger-bg);
  width: var(--burger-diameter);
  height: var(--burger-diameter);
  border-radius: var(--burger-btn-border-radius);
  border: none;
  cursor: pointer;
  overflow: hidden;
  transition: var(--burger-transition);
  outline: var(--burger-enable-outline-width) solid transparent;
  outline-offset: 0;
}

.burger span {
  height: var(--burger-line-height);
  width: var(--burger-line-width);
  background: var(--burger-color);
  border-radius: var(--burger-line-border-radius);
  position: absolute;
  transition: var(--burger-line-transition);
}

.burger span:nth-child(1) {
  top: var(--burger-offset);
}

.burger span:nth-child(2) {
  bottom: var(--burger-offset);
}

.burger span:nth-child(3) {
  top: 50%;
  transform: translateY(-50%);
}

.popup-window {
  transform: scale(var(--nav-default-scale));
  visibility: hidden;
  opacity: 0;
  position: absolute;
  padding: var(--nav-padding-y) var(--nav-padding-x);
  background: var(--nav-bg);
  font-family: var(--nav-font-family);
  color: var(--nav-text-color);
  border-radius: var(--nav-border-radius);
  box-shadow: var(--nav-shadow-width) var(--nav-shadow-color);
  border: var(--nav-border-width) solid var(--nav-border-color);
  top: calc(
    var(--burger-diameter) + var(--burger-enable-outline-width) +
      var(--burger-enable-outline-offset)
  );
  left: var(--nav-position-left);
  right: var(--nav-position-right);
  transition: var(--burger-transition);
}

.popup-window legend {
  padding: var(--nav-title-padding-y) var(--nav-title-padding-x);
  margin: 0;
  color: var(--nav-title-color);
  font-size: var(--nav-title-size);
  text-transform: uppercase;
}

.popup-window ul {
  margin: 0;
  padding: 0;
  list-style-type: none;
}

.popup-window ul button {
  outline: none;
  width: 100%;
  border: none;
  background: none;
  display: flex;
  align-items: center;
  color: var(--burger-color);
  font-size: var(--nav-button-font-size);
  padding: var(--nav-button-padding-y) var(--nav-button-padding-x);
  white-space: nowrap;
  border-radius: var(--nav-button-border-radius);
  cursor: pointer;
  column-gap: var(--nav-button-distance);
}

.popup-window ul li:nth-child(1) svg,
.popup-window ul li:nth-child(2) svg {
  color: cornflowerblue;
}

.popup-window ul li:nth-child(4) svg,
.popup-window ul li:nth-child(5) svg {
  color: rgb(153, 153, 153);
}

.popup-window ul li:nth-child(7) svg {
  color: red;
}

.popup-window hr {
  margin: var(--underline-margin-y) 0;
  border: none;
  border-bottom: var(--underline-border-width) solid
    var(--underline-border-color);
}

/* actions */

.popup-window ul button:hover,
.popup-window ul button:focus-visible,
.popup-window ul button:hover svg,
.popup-window ul button:focus-visible svg {
  color: var(--nav-button-hover-text-color);
  background: var(--nav-button-hover-bg);
}

.burger:hover {
  transform: scale(var(--burger-hover-scale));
}

.burger:active {
  transform: scale(var(--burger-active-scale));
}

.burger:focus:not(:hover) {
  outline-color: var(--burger-enable-outline-color);
  outline-offset: var(--burger-enable-outline-offset);
}

.popup input:checked + .burger span:nth-child(1) {
  top: 50%;
  transform: translateY(-50%) rotate(45deg);
}

.popup input:checked + .burger span:nth-child(2) {
  bottom: 50%;
  transform: translateY(50%) rotate(-45deg);
}

.popup input:checked + .burger span:nth-child(3) {
  transform: translateX(
    calc(var(--burger-diameter) * -1 - var(--burger-line-width))
  );
}

.popup input:checked ~ nav {
  transform: scale(var(--nav-active-scale));
  visibility: visible;
  opacity: 1;
}

.dropdown {
  margin-top: 5px;
  margin-left: 5px;
}
.menu {
  border-radius: 10px;
  outline: none;
  border-color: rgba(234, 76, 137, 0.4);
  background-color: #fff;
  box-shadow: 0 0 0 4px rgb(234 76 137 / 10%);
}

.input-container {
  display: flex;
  background: white;
  border-radius: 1rem;
  background: linear-gradient(45deg, #c5c5c5 0%, #ffffff 100%);
  box-shadow: 20px 20px 20px #d8d8d8, -10px -10px 20px #f8f8f8;
  padding: 0.3rem;
  gap: 0.3rem;
}

.input-container input {
  border-radius: 0.8rem 0 0 0.8rem;
  background: #e8e8e8;
  box-shadow: inset 13px 13px 10px #dcdcdc, inset -13px -13px 10px #f4f4f4;
  width: 100%;
  flex-basis: 75%;
  padding: 1rem;
  border: none;
  border-left: 2px solid #4998ff;
  color: #5e5e5e;
  transition: all 0.2s ease-in-out;
}

.input-container input:focus {
  border-left: 2px solid #4998ff;
  outline: none;
  box-shadow: inset 13px 13px 10px #bff0fa, inset -13px -13px 10px #f4f4f4;
}

.input-container button {
  flex-basis: 25%;
  padding: 1rem;
  background: linear-gradient(135deg, #bff0fa 0%, #4998ff 100%);
  font-weight: 900;
  letter-spacing: 0.3rem;
  text-transform: uppercase;
  color: white;
  border: none;
  width: 100%;
  border-radius: 0 1rem 1rem 0;
  transition: all 0.2s ease-in-out;
}

.input-container button:hover {
  background: linear-gradient(135deg, #bff0fa 0%, #4998ffc4 100%);
}

@media (max-width: 500px) {
  .input-container {
    flex-direction: column;
  }

  .input-container input {
    border-radius: 0.8rem;
  }

  .input-container button {
    padding: 1rem;
    border-radius: 0.8rem;
  }
}
.topBts {
  font-weight: bold;
  color: white;
  border: 2px solid rgba(255, 0, 0, 0.582);
  background-color: rgba(255, 0, 0, 0.582);
  border-radius: 0.9em;
  padding: 0.8em 1.2em 0.8em 1em;
  transition: all ease-in-out 0.2s;
  font-size: 16px;
}

.topBts span {
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  font-weight: 600;
}

.topBts:hover {
  background-color: white;
  color: rgba(255, 0, 0, 0.582);
}
.folders {
  position: absolute;
  height: max-content;
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

/* checkbox */
.checkcontainer {
  max-height: 20px;
  margin: 5px;
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

.linktocont:hover {
  color: #212121;
}
.topBts2 {
  font-weight: bold;
  color: white;
  border: 2px solid rgba(60, 60, 60, 0.582);
  background-color: rgba(60, 60, 60, 0.582);
  border-radius: 0.9em;
  padding: 0.8em 1.2em 0.8em 1em;
  transition: all ease-in-out 0.2s;
  font-size: 16px;
}

.topBts2 span {
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  font-weight: 600;
}
.user{
  display: flex;
  flex-flow: row wrap;
  align-items: center;
  gap: 20px;
  justify-content: center;
}
.bt{
  border-radius: 50%;
  width: 50px;
  height: 50px;
  background-color: rgba(255, 0, 0, 0.582);
}
</style>