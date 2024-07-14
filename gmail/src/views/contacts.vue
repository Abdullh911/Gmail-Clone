<template>
  <div class="all">
    <section class="d-flex window">
      <sideBar />
      <div class="body">
        <div class="search">
          <input @input="searchContacts" placeholder="Search..." v-model="searched" class="inputt" name="text" type="text">
          <button @click="sortDescending" class="swallow__icon">
            <span></span>
          </button>
        </div>
        
        <div class="input-container">
          <input class="inp" ref="adding" v-model="name" placeholder="Name" type="text" />
          <input class="inp" ref="adding" v-model="contact" placeholder="email,email,email..." type="text" />
          <button @click="addOrEdit==='Add' ? addContact():editContact()" class="button">{{addOrEdit}}</button>
        </div>
        <ul>
          <li v-for="contact in userContacts" :key="contact">
            <button class="contactsPanel" >
              <div class="name col-4">
                <span class="cntct" @click="goSend(contact)">{{ contact.name }}  |   </span>
              </div>
              <div class="col-4">
                <span  v-for="cont in contact.emails" :key="cont">{{cont}}, </span> 
              </div>
              <div class="col-4">
                <span class="edit" @click="changeAdd(contact)">✏️</span>
                <span @click="removeContact(contact)" class="remove">🗙</span>
              </div>
            </button>
          </li>
        </ul>
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
  mounted(){  
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
          this.userContacts = userData.contacts.sort((a, b) =>
          a.name.localeCompare(b.name)
        );
          this.userContacts = userData.contacts;
          this.temp=userData.contacts
          console.log(this.userContacts);
          $store.commit("setCurrUser", newData);
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
       
      }
  },

  data() {
    return {
      searched:"",
      editName:"",
      name:"",
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
      addOrEdit:"Add",
      isDropdownOpen: false,
      willBeEdited:"",
      temp:[],
      sortByNameOrder: 1,
    };
  },
  methods:{
    searchContacts() {
        this.userContacts = this.temp.filter((x) => {
        return x.name.toLowerCase().includes(this.searched.toLowerCase());
      });
      
},
    sortDescending() {
      this.userContacts.reverse();
    },
    handleDocumentClick(event) {
      const isInputOrButton = event.target.classList.contains('inp') || event.target.classList.contains('button');
      if (!isInputOrButton) {
        this.addOrEdit="Add"
        location.reload();
      }
    },
    changeDrop(){
      this.isDropdownOpen=!this.isDropdownOpen;
    },
    changeAdd(x){
      this.willBeEdited=x;
      this.addOrEdit="Edit";
      setTimeout(() =>{
        this.$refs.adding.focus();
      },500)
      setTimeout(() =>{
        document.addEventListener('click', this.handleDocumentClick);
      },500)
      
    },
    editContact() {
      let userEmail = $store.state.currUser.email;
      if (this.contact.length !== 0) {
        this.name=this.willBeEdited.name;
        let email = this.contact.split(",");
        let cont={
          emails:email,
          name:this.willBeEdited.name,
        }
        console.log("contact is added");
        fetch(`http://localhost:8080/editContact/${userEmail}/${this.willBeEdited.name}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body:JSON.stringify(cont)
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Failed to send mail");
            }
            location.reload();
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
      this.addOrEdit="Add";
    },
    removeContact(cont) {
      let userEmail = $store.state.currUser.email;
      
        console.log("contact is added");
        fetch(`http://localhost:8080/removeContact/${userEmail}/${cont.name}`, {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Failed to send mail");
            }
            //this.$router.push({ name: "inbox" });
            location.reload();
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
    addContact() {
      let userEmail = $store.state.currUser.email;
      if (this.contact.length !== 0) {
        let email = this.contact.split(",");
        let cont={
          emails:email,
          name:this.name
        }
        console.log("contact is added");
        fetch(`http://localhost:8080/addContact/${userEmail}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body:JSON.stringify(cont)
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Failed to send mail");
            }
            // this.$router.push({ name: "inbox" });
            location.reload();
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
      let z=""
      for(let i=0;i<x.emails.length;i++){
        if(i===x.emails.length-1){
          z+=x.emails[i];
          break;
        }
        z+=x.emails[i]+",";
      }
      $store.commit("setWantedContact", z);
      console.log(x);
      this.$router.push({ name: "send" });
    },
  }
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
  overflow-x: scroll;
  
}
.all {
  height: 100%;
  background-color: rgb(203, 220, 247);
  width: 100%;
}
.search{
  display: flex;
  flex-flow: row wrap;
  justify-content:left;
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
.body{
  width: 100%;
  height: 100%;
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
.nav {
  background-color: rgb(167, 178, 194);
  padding: 10px;
  width: 220px;
  height: 650px;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  box-shadow: -3px 0px 14px 0px #00000086;
  overflow-y: scroll;
}
.nav a {
  font-weight: 700px;
  font-size: 18px;
  color: rgba(0, 0, 0, 0.315);
}
.edit:hover{
  background-color: #d67272;
  border-radius:50% ;
}
.cntct:hover{
  background-color: #d67272;
  border-radius:15px ;
}
.remove:hover{
  background-color: #d67272;
  border-radius:50% ;
}
.name{
  display: flex;
  flex-flow: row wrap;
  justify-content: left;
}
.contactsPanel{
  width: 100%;
  border-radius: 15px;
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  height: 40px;
  align-content: center;
}
.nav a.router-link-exact-active {
  color: black;
}
.bts{
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
  padding-bottom: 15px;
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
  height: 100vh;
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
.menu{
  height: 40px;
  position: relative;
  bottom: 3px;
  
}

.type1:hover::before {
  transform: translateY(-50px) scale(0) rotate(120deg);
}
.flex-column {
  width: 350px;
}
.sendBtn{
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
.contactss{
  display: flex;
  flex-flow: row wrap;
  justify-content: left;
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: 0px;
  gap: 20px;
}
.cnt{
  font-size: xx-large;
}
.dropCnt{
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
  border-left: 2px solid #c84747;
  color: #5e5e5e;
  transition: all 0.2s ease-in-out;
}

.input-container input:focus {
  border-left: 2px solid #c84747;
  outline: none;
  box-shadow: inset 13px 13px 10px #c84747, inset -13px -13px 10px #f4f4f4;
}

.input-container button {
  flex-basis: 25%;
  padding: 1rem;
  background: linear-gradient(135deg, #bff0fa 0%, #c84747 100%);
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
  background: linear-gradient(135deg, #c84747 0%, #c84747 100%);
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
  border: 2px solid #c84747;
  background-color: #c84747;
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
  background-color: #c84747;
 }
 .inputt {
  padding: 10px;
  width: 120px;
  border: none;
  outline: none;
  border-radius:5px 0px 0px 5px;
  box-shadow: 0 1px  gray;
  font-size: 18px;
  transition: width 0.3s;
  font-family: Consolas,monaco,monospace;
  margin-bottom: 20px;
  margin-left: 20px;
}

.inputt:focus {
  outline: 1px solid blue;
  box-shadow: none;
  width: 230px;
}

.inputt::placeholder {
  color: #c84747;
}


/* <reset-style> ============================ */
  .swallow__icon {
    border: none;
    background: none;
    padding: 0;
    margin: 0;
    cursor: pointer;
    font-family: inherit;
    height: 27px;
    border-radius:0 5px 5px 0px;
  }
  /* <main-style> ============================ */
  .swallow__icon {
    width: 2rem;
    height: 48px;
    background-color: #c84747;
    backdrop-filter: saturate(180%) blur(20px);
    border: solid 1px rgba(66,66,69,0.7);
    position: relative;
  }
  
  .swallow__icon span {
    width: 1.5rem;
    height: 1rem;
    position: absolute;
    top: calc(.25rem + .563rem - 1px);
    left: calc(.25rem - 1px);
    transition: transform 1s cubic-bezier(.86, 0, .07, 1),
                transform-origin 1s cubic-bezier(.86, 0, .07, 1);
  }
  
  .swallow__icon span:before,
  .swallow__icon span:after {
    content: "";
    width: .75rem;
    height: .125rem;
    background-color: rgb(245, 245, 247);
    position: absolute;
    bottom: 0;
    transition: transform 1s cubic-bezier(.86, 0, .07, 1),
                transform-origin 1s cubic-bezier(.86, 0, .07, 1);
  }
  
  .swallow__icon span:before {
    right: 50%;
    border-radius: 2px 0 0 2px;
    transform-origin: 100% 100%;
    transform: rotate(40deg);
  }
  
  .swallow__icon span:after {
    left: 50%;
    border-radius: 0 2px 2px 0;
    transform-origin: 0 100%;
    transform: rotate(-40deg);
  }
  
  .swallow__icon:hover span {
    transform: translateY(-8px);
  }
  
  .swallow__icon:hover span:before {
    transform-origin: 100% 0;
    transform: rotate(-40deg);
  }
  
  .swallow__icon:hover span:after {
    transform-origin: 0 0;
    transform: rotate(40deg);
  }
</style>