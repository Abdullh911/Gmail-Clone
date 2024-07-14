<template>
  <div class="all">
    <section class="d-flex">
      <div class="img">
        <img src="./2308-w030-n003-822B-p15-822.jpg" alt="sad">
        <h1 class="logo">PostMail</h1>
      </div>
      <div class="form">
        <form @submit.prevent="logIn" class="container" >
          <router-link to="/">
            <h2>
              <i class="fa-solid fa-arrow-left" style="color: #000000"></i></h2
          ></router-link>
          
          <div
            v-show="this.missingData"
            class="alert alert-danger"
            role="alert"
          >
            <h5>Some Data Is Missing</h5>
          </div>
          <div class="row">
              <div class="input-container">
                <div class="input-content">
                  <div class="input-dist">
                    <div class="input-type">
                      <input class="input-is" type="email" required v-model="email" placeholder="Email" />
                      <input
                        class="input-is"
                        type="password"
                        required=""
                        placeholder="Password"
                        v-model="password"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div
              v-show="this.userNotFound"
              class="alert alert-danger"
              role="alert"
            >
              <h5>Wrong Email Or Paassword</h5>
            </div>            
            <router-link to="/forgetPassword" class="nav-link links">
              <h6>Forget Your Password</h6>
            </router-link>
          </div>

          <button @click="checkMissingData" class="comic-button">Log in</button>
          <div class="row">
            <router-link to="/signup" class="nav-link links">
              <h6 class="center">Don't have an account? Sign up</h6>
            </router-link>
          </div>
        </form>
      </div>
    </section>
  </div>
</template>
<script>
import $store from "../store/index.js";

export default {
  data() {
    return {
      email: "",
      password: "",
      userNotFound: false,
      missingData: false,
    };
  },
  created() {
    this.checkLoggedIn();
  },
  methods: {
    logIn(event) {
      this.checkMissingData();
      this.userNotFound = false;
      const email = this.email;
      const password = this.password;
      const apiUrl = `http://localhost:8080/checkPass/${email}`;

      fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(password),
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          } else if (response.status === 404) {
            return null;
          } else {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
        })
        .then((userData) => {
          if (userData !== null) {
            console.log("Login successful");
            const fakeToken = "your-fake-token";
            const userDataString = JSON.stringify(userData);
            localStorage.setItem("userData", userDataString);
            localStorage.setItem("token", fakeToken);
            $store.commit("setLoginStatus", true);
            $store.commit("setCurrUser", userData);
            console.log(userData);
            this.$store.commit("setSignOut", false);
            this.$router.push({ name: "inbox" });
          } else {
            this.userNotFound = true;
          }
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
    },
    checkLoggedIn() {
      const token = localStorage.getItem("token");
      if (token) {
        const userDataString = localStorage.getItem("userData");
        const userData = JSON.parse(userDataString);
        $store.commit("setCurrUser", userData);
        $store.commit("setLoginStatus", true);
      }
    },
    checkMissingData() {
      if (this.email.length == 0 || this.password.length == 0) {
        this.missingData = true;
      } else {
        this.missingData = false;
      }
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
.form {
  padding: 50px;
  width: 50%;
  background-color: white;
  text-align: start;
  margin: 50px 0px;
  border-top-right-radius: 30px;
  border-bottom-right-radius: 30px;
  box-shadow: rgba(0, 0, 0, 0.418) 8px 1px 10px 2px;
}
section {
  display: flex;
  flex-flow: row;
  justify-content: center;
  width: 80%;
  margin: auto;
  border-radius: 50px;
  height: 700px;
}
.img {
  width: 50%;
  background-position: center;
  background-size: cover;
  margin: 50px 0px;
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
  box-shadow: rgba(0, 0, 0, 0.418) -8px 1px 10px 2px;
}
.all {
  height: 100vh;
  background-image: url("view-white-crumpled-paper.jpg");
}
img {
  height: 100%;
}
.butn {
  position: relative;
  background: linear-gradient(to bottom, white, #53c6ba);
  border-radius: 50px;
  margin-top: 30px;
  padding: 10px 40px;
  left: 40%;
}
.alert {
  margin-top: 10px;
  padding: 7px 10px;
}
h5 {
  color: red;
  font-size: 14px;
  margin-top: 5px;
}
label {
  margin: 25px 0px 15px;
  font-size: 16px;
  letter-spacing: 1px;
  font-weight: bold;
  color: white;
}
input,
select {
  margin-right: 5px;
  display: block;
  padding: 2px 1px;
  width: 100%;
  border: none;
  background-color: #33455b;
  border-bottom: 2px solid #ddd;
  transition: all 0.1s ease-in;
  color: rgba(255, 255, 255, 0.623);
}
input:focus {
  border-bottom: 5px solid #ddd;
  outline: none;
}
select:focus {
  border-bottom: 5px solid #ddd;
  outline: none;
}
select {
  width: 92%;
  margin-left: 12px;
}

h3 {
  text-align: center;
  color: rgba(255, 255, 255, 0.623);
}
h2 {
  text-align: start;
  margin-bottom: 20px;
  cursor: pointer;
}
h6 {
  direction: rtl;
  cursor: pointer;
  padding: 20px;
  text-decoration: underline;
}
h6:hover{
  color: #c84747;
}
.center {
  margin: 0 auto;
  cursor: pointer;
  text-align: center;
}
.check {
  display: flex;
  flex-flow: row;
  align-items: space-evenly;
}
#myCheckbox {
  margin-right: 10px;
  width: auto;
  height: auto;
}
.popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 700px;
  height: 600px;
  font: 13.5px/1.5 Helvetica, Verdana, sans-serif;
  padding: 20px;
  padding-left: 30px;
  background-color: #fff;
  /* background-image: url("../assets/potatoterms.png"); */
  background-size: cover;
  /* filter: blur(20px); */
  border: 1px solid #ccc;
  border-radius: 30px;
  z-index: 2;
}
.popup button {
  position: absolute;
  top: 10px;
  right: 14px;
  font-size: 42px;
  line-height: 18px;
  padding: 0;
  margin: 0;
  border: none;
  background: transparent;
  cursor: pointer;
  color: #333;
}
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  z-index: 1;
}
.alert {
  margin: 0;
}
a {
  font-weight: bold;
  color: black;
  text-decoration: none;
}

a:hover {
  color: white;
}

a.router-link-exact-active {
  color: black;
  text-decoration: none;
}
/*The eye icon on the password can be stylize with any tool you want
to use (the only way i know to do this correctly is using JS)
this is meant to be wide supported and it really depends on you browsers
if i realize any way to stylize the eye it could be added in the future
in other input type*/

.container {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  font-style: italic;
  font-weight: bold;
  display: flex;
  margin: auto;
  aspect-ratio: 16/9;
  align-items: center;
  justify-items: center;
  justify-content: center;
  flex-wrap: nowrap;
  flex-direction: column;
  gap: 1em;
  
}

.input-container {
  filter: drop-shadow(46px 36px 24px #4090b5)
    drop-shadow(-55px -40px 25px #9e30a9);
  animation: blinkShadowsFilter 8s ease-in infinite;
}

.input-content {
  display: grid;
  align-content: center;
  justify-items: center;
  align-items: center;
  text-align: center;
  padding-inline: 1em;
  width: 30vw;
  height: 30vh;
}

.input-content::before {
  content: "";
  position: absolute;
  width: 100%;
  height: 100%;
  
  background: #c84747;
  transition: all 1s ease-in-out;
}

.input-content::after {
  content: "";
  position: absolute;
  width: 98%;
  height: 98%;
  
 
}

.input-dist {
  z-index: 80;
  display: grid;
  align-items: center;
  text-align: center;
  width: 100%;
  padding-inline: 1em;
  padding-block: 1.2em;
  grid-template-columns: 1fr;
}

.input-type {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  gap: 1em;
  font-size: 1.1rem;
  background-color: transparent;
  width: 100%;
  border: none;
}

.input-is {
  color: #fff;
  font-size: 0.9rem;
  background-color: transparent;
  width: 100%;
  box-sizing: border-box;
  padding-inline: 0.5em;
  padding-block: 0.7em;
  border: none;
  transition: all 1s ease-in-out;
  border-bottom: 1px solid hsl(221, 86%, 9%);
}

.input-is:hover {
  transition: all 1s ease-in-out;
  
}

.input-content:focus-within::before {
  transition: all 1s ease-in-out;
  
}

.input-is:focus {
  outline: none;
  border-bottom: 1px solid hsl(0, 84%, 70%);
  color: hsl(192, 100%, 88%);
  
}

.input-is::-moz-placeholder {
  color: hsla(192, 100%, 88%, 0.806);
}

.input-is::placeholder {
  color: hsla(192, 100%, 88%, 0.806);
}

.submit-button {
  width: 50%;
  border: none;
  color: hsla(192, 100%, 88%, 0.806);
  background: linear-gradient(
    90deg,
    transparent 0%,
    rgba(102, 224, 255, 0.2) 27%,
    rgba(102, 224, 255, 0.2) 63%,
    transparent 100%
  );
  clip-path: polygon(0 0, 85% 0%, 100% 0, 100% 15%, 100% 90%, 91% 100%, 0 100%);
  padding: 0.5em;
  animation: blinkShadowsFilter 0.5s ease-in infinite;
  transition: all 500ms;
}

.submit-button:hover {
  color: hsl(0, 0%, 100%);
  cursor: pointer;
  font-size: medium;
  font-weight: bold;
}
img{
  width: 300px;
  height: 300px;
  position: relative;
  right: 20px;
}
.img {
  width: 50%;
  background-color: #ffffff;
  
  display: flex;
  flex-flow: column wrap;
  align-items: center;
  justify-content: center;
}
.logo{
  font-family: Rubik Doodle Shadow;
  font-size: 100px;
}

@keyframes backglitch {
  0% {
    box-shadow: inset 0px 20px 20px 30px #212121;
  }

  50% {
    box-shadow: inset 0px -20px 20px 30px hsl(297, 42%, 10%);
  }

  to {
    box-shadow: inset 0px 20px 20px 30px #212121;
  }
}

@keyframes rotate {
  0% {
    transform: rotate(0deg) translate(-50%, 20%);
  }

  50% {
    transform: rotate(180deg) translate(40%, 10%);
  }

  to {
    transform: rotate(360deg) translate(-50%, 20%);
  }
}

@keyframes blinkShadowsFilter {
  0% {
    filter: drop-shadow(46px 36px 28px rgba(64, 144, 181, 0.3411764706))
      drop-shadow(-55px -40px 28px #c84747);
  }

  25% {
    filter: drop-shadow(46px -36px 24px rgba(64, 144, 181, 0.8980392157))
      drop-shadow(-55px 40px 24px #c84747);
  }

  50% {
    filter: drop-shadow(46px 36px 30px rgba(64, 144, 181, 0.8980392157))
      drop-shadow(-55px 40px 30px rgba(159, 48, 169, 0.2941176471));
  }

  75% {
    filter: drop-shadow(20px -18px 25px rgba(64, 144, 181, 0.8980392157))
      drop-shadow(-20px 20px 25px rgba(159, 48, 169, 0.2941176471));
  }

  to {
    filter: drop-shadow(46px 36px 28px rgba(64, 144, 181, 0.3411764706))
      drop-shadow(-55px -40px 28px #c84747);
  }
}
.comic-button {
  display: inline-block;
  padding: 10px 20px;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  text-decoration: none;
  color: #fff;
  background-color: #ff5252;
  border: 2px solid #000;
  border-radius: 10px;
  box-shadow: 5px 5px 0px #000;
  transition: all 0.3s ease;
  cursor: pointer;
}

.comic-button:hover {
  background-color: #fff;
  color: #ff5252;
  border: 2px solid #ff5252;
  box-shadow: 5px 5px 0px #ff5252;
}

.comic-button:active {
  background-color: #fcf414;
  box-shadow: none;
  transform: translateY(4px);
}


</style>

