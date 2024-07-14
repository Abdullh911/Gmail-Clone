<template>
  <div class="all">
    <div class="container">
      <section>
        <div class="img"></div>
        <div class="form">
          <h3>Here We Go!</h3>
          <div class="row">
            <div class="col">
              <label>Enter New Password</label>
              <input
                type="password"
                required
                v-model="newPassword"
                @input="checkPassword(this.newPassword)"
              />
              <div
                v-show="!passwordValid && newPassword.length > 0"
                class="alert alert-danger"
                role="alert"
              >
                <h5>
                  Password Should Contain Capital Letter,Small Letter and Number
                  and over 8 lentgh
                </h5>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col">
              <label>Confirm New Password</label>
              <input
                type="password"
                required
                v-model="confirmNewPassword"
                @blur="matchPassword(this.newPassword, this.confirmNewPassword)"
              />
              <div
                v-show="
                  !matchPassword(this.newPassword, this.confirmNewPassword) &&
                  confirmNewPassword.length > 0
                "
                class="alert alert-danger"
                role="alert"
              >
                <h5>Password Doesn't Match</h5>
              </div>
            </div>
          </div>
            <button @click="changePasss" class="butn">Submit</button>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import $store from "../store/index.js";

export default {
  data() {
    return {
      email: $store.state.forgetUser.email,
      newPassword: "",
      confirmNewPassword: "",
      passwordLength: false,
      passwordCapital: false,
      passwordNumber: false,
      passwordsmall: false,
      passwordValid: false,
    };
  },
  methods: {
    matchPassword(password, confirmPassword) {
      if (this.newPassword !== this.confirmNewPassword) {
        return false;
      } else {
        return true;
      }
    },
    checkPassword(password) {
      if (this.newPassword.length >= 8) {
        this.passwordLength = true;
      } else {
        this.passwordLength = false;
      }
      if (this.newPassword.match(/[A-Z]/)) {
        this.passwordCapital = true;
      } else {
        this.passwordCapital = false;
      }
      if (this.newPassword.match(/[0-9]/)) {
        this.passwordNumber = true;
      } else {
        this.passwordNumber = false;
      }
      if (this.newPassword.match(/[a-z]/)) {
        this.passwordsmall = true;
      } else {
        this.passwordsmall = false;
      }
      if (
        this.passwordLength &&
        this.passwordCapital &&
        this.passwordNumber &&
        this.passwordsmall
      ) {
        this.passwordValid = true;
        return true;
      } else {
        this.passwordValid = false;
        return false;
      }
    },
    changePasss(event) {
      if (
        !this.checkPassword(this.newPassword) ||
        !this.matchPassword(this.newPassword, this.confirmNewPassword)
      ) {
        event.preventDefault();
      } else {
        fetch(`http://localhost:8080/updatePassword/${this.email}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(this.newPassword),
        })
          .then((res) => {
            if (res.ok) {
              this.$router.push("/login");
              return res.json();
            } else if (res.status === 404) {
              throw new Error("User not found");
            } else {
              throw new Error(`Error: ${res.status}`);
            }
          })
          .then((res) => {
            console.log("Password updated:", res);
          })
          .catch((error) => {
            console.error("Error during password update:", error);
          });
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
  background-color: #33455b;
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
  background-color: #53c6ba;
  margin: 50px 0px;
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
  box-shadow: rgba(0, 0, 0, 0.418) -8px 1px 10px 2px;
}
.all {
  height: 700px;
  margin-top: -60px;
  background-color: rgb(203, 220, 247);
}
img {
  height: 100%;
}
.butn {
  position: relative;
  background: linear-gradient(to bottom, white, #53c6ba);
  border-radius: 50px;
  margin-top: 70px;
  padding: 10px 40px;
  left: 25%;
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
</style>
