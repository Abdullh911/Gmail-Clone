<template>
  <div class="all">
    <section class="d-flex">
      <div class="img">
        
        <img src="./2308-w030-n003-822B-p15-822.jpg" alt="sad">
        <h1 class="logo">PostMail</h1>
      </div>
      <div class="form">
        <form @submit.prevent="signUp">
          <router-link to="/" class="center">
            <h2>
              <i class="fa-solid fa-arrow-left" style="color: #000000"></i>
            </h2>
          </router-link>
          <h3>New member? Welcome!</h3>
          <div
            v-show="this.userDuplicate"
            class="alert alert-danger"
            role="alert"
          >
            <h5>User Already Exist</h5>
          </div>
          <div
            v-show="this.missingData"
            class="alert alert-danger"
            role="alert"
          >
            <h5>Some Data Is Missing</h5>
          </div>
          <div class="row justify-content-between">
            <div class="col-6">
              <label>First name</label>
              <input
                type="text"
                required
                placeholder="First name"
                v-model="firstName"
              />
            </div>
            <div class="col-6">
              <label>Last name</label>
              <input
                type="text"
                required
                placeholder="Last name"
                v-model="lastName"
              />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <label>Email</label>
              <input
                type="email"
                placeholder="Enter your email"
                v-model="email"
                required
              />
            </div>
          </div>
          <div class="row">
            <div class="col">
              <label>Create password</label>
              <input
                type="password"
                placeholder="Enter your password"
                required
                v-model="password"
                @input="checkPassword(this.password)"
              />
              <div
                v-show="!passwordValid && password.length > 0"
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
              <label>Confirm password</label>
              <input
                type="password"
                placeholder="Confirm your password"
                required
                v-model="confirmPassword"
                @blur="matchPassword(this.password, this.confirmPassword)"
              />
              <div
                v-show="
                  !matchPassword(this.password, this.confirmPassword) &&
                  confirmPassword.length > 0
                "
                class="alert alert-danger"
                role="alert"
              >
                <h5>Password Doesn't Match</h5>
              </div>
            </div>
          </div>
          <div class="row">
            <label>Security Question</label>
            <select v-model="securityQuestion" required>
              <option
                class="selectOpt"
                value="What was the name of your first pet?"
              >
                What was the name of your first pet?
              </option>
              <option class="selectOpt" value="In which city were you born?">
                In which city were you born?
              </option>
              <option
                class="selectOpt"
                value="What is your favorite book or movie?"
              >
                What is your favorite book or movie?
              </option>
              <option
                class="selectOpt"
                value="What is the name of your favorite childhood friend?"
              >
                What is the name of your favorite childhood friend?
              </option>
              <option
                class="selectOpt"
                value="In which year did you graduate from high school or college?"
              >
                In which year did you graduate from high school or college?
              </option>
            </select>
          </div>
          <div v-show="securityQuestion" class="row">
            <div class="col">
              <label>Answer</label>
              <input type="text" required v-model="securityAnswer" />
            </div>
          </div>
          <div class="row justify-content-between">
            <label>Birthday date</label>
            <div class="col-4">
              <input
                type="text"
                placeholder="DD"
                v-model="day"
                @input="formatDatePart('day')"
              />
            </div>
            <div class="col-4">
              <input
                type="text"
                placeholder="MM"
                v-model="month"
                @input="formatDatePart('month')"
              />
            </div>
            <div class="col-4">
              <input
                type="text"
                placeholder="YYYY"
                v-model="year"
                @input="formatDatePart('year')"
              />
            </div>
          </div>
          <div class="row">
            <label>Gender</label>
            <select v-model="gender" required>
              <option class="selectOpt" value="male">Male</option>
              <option class="selectOpt" value="female">female</option>
            </select>
          </div>
            <button class="btn butn">Submit</button>
          <div class="row">
            <router-link to="/login" class="center">
              <h6>Already have an account? Login</h6>
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
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
      securityQuestion: "",
      securityAnswer: "",
      year: "",
      month: "",
      day: "",
      gender: "",
      passwordLength: false,
      passwordCapital: false,
      passwordNumber: false,
      passwordsmall: false,
      passwordValid: false,
      isChecked: false,
      termsDisplay: false,
      submitState: false,
      userDuplicate: false,
      missingData: false,
    };
  },

  methods: {
    formatDatePart(part) {
      let formattedValue = "";

      const today = new Date();
      const currentYear = today.getFullYear();
      const currentMonth = today.getMonth() + 1;
      const currentDay = today.getDate();

      switch (part) {
        case "day":
          formattedValue = this.day.replace(/\D/g, "").slice(0, 2);
          this.day =
            formattedValue && formattedValue <= 31 ? formattedValue : "";
          break;

        case "month":
          formattedValue = this.month.replace(/\D/g, "").slice(0, 2);
          this.month =
            formattedValue && formattedValue <= 12 ? formattedValue : "";
          break;

        case "year":
          formattedValue = this.year.replace(/\D/g, "").slice(0, 4);
          const minYear = 1900;
          this.year =
            formattedValue && formattedValue >= minYear
              ? formattedValue
              : this.year;
          this.year =
            this.year && this.year <= currentYear
              ? this.year
              : currentYear.toString();
          break;

        default:
          break;
      }
      const selectedMonth = parseInt(this.month, 10);
      if (selectedMonth === 2) {
        const selectedYear = parseInt(this.year, 10);
        const isLeapYear =
          (selectedYear % 4 === 0 && selectedYear % 100 !== 0) ||
          selectedYear % 400 === 0;

        const maxDays = isLeapYear ? 29 : 28;
        this.day = this.day && this.day <= maxDays ? this.day : "";
      }
      if (
        this.year == currentYear &&
        this.month == currentMonth &&
        this.day > currentDay
      ) {
        this.day = "";
      }
    },

    matchPassword(password, confirmPassword) {
      if (this.password !== this.confirmPassword) {
        return false;
      } else {
        return true;
      }
    },
    checkPassword(password) {
      if (this.password.length >= 8) {
        this.passwordLength = true;
      } else {
        this.passwordLength = false;
      }
      if (this.password.match(/[A-Z]/)) {
        this.passwordCapital = true;
      } else {
        this.passwordCapital = false;
      }
      if (this.password.match(/[0-9]/)) {
        this.passwordNumber = true;
      } else {
        this.passwordNumber = false;
      }
      if (this.password.match(/[a-z]/)) {
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
    checkMissing() {
      if (
        this.firstName.length == 0 ||
        this.lastName.length == 0 ||
        this.email.length == 0 ||
        this.password.length == 0 ||
        this.confirmPassword.length == 0 ||
        this.securityQuestion.length == 0 ||
        this.securityAnswer.length == 0 ||
        this.gender.length == 0
      ) {
        this.missingData = true;
      } else {
        this.missingData = false;
      }
    },
    signUp(event) {
  this.checkMissing();
  this.submitState = !this.submitState;
  this.userDuplicate = false;

  if (
    !this.checkPassword(this.password) ||
    !this.matchPassword(this.password, this.confirmPassword)
  ) {
    event.preventDefault();
  } else {
    let userObj = {
      index: 0,
      email: this.email,
      password: this.password,
      securityQuestion: this.securityQuestion,
      securityAnswer: this.securityAnswer,
      firstName: this.firstName,
      lastName: this.lastName,
      gender: this.gender,
      inbox: [],
      sent: [],
      trash: [],
      draft: [],
      msgId:0,
      contacts:[],
      favourites:[],
      folders:[],
    };

    fetch(`http://localhost:8080/checkExist`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(this.email),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        this.userDuplicate = data;

        if (!this.userDuplicate) {
          fetch("http://localhost:8080/users/add", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userObj),
          })
            .then(() => {
              console.log("done");
              this.$router.push("/login");
            })
            .catch((error) => {
              console.error("Error during signup:", error);
            });
        }
      })
      .catch((error) => {
        console.error("Error during Signup:", error);
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
  background-color: #c84747;
  text-align: start;
  margin: 50px 0px;
  border-top-right-radius: 30px;
  border-bottom-right-radius: 30px;
    box-shadow: rgba(0, 0, 0, 0.418) 8px 1px 10px 2px;

}
a:hover{
  color: white;
}
section {
  display: flex;
  flex-flow: row;
  justify-content: center;
  width: 80%;
  margin: auto;
  border-radius: 50px;
  height: max-content;
}

.all {
  height: 100%;
  margin-top: -60px;
  background-image: url("view-white-crumpled-paper.jpg");
}
.butn {
  position: relative;
  background: linear-gradient(to bottom, white, #de5a50);
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
  background-color: #c84747;
  border-bottom: 2px solid #ddd;
  transition: all 0.1s ease-in;
  color: rgba(0, 0, 0, 0.623);
}
input::placeholder {
  color: #000000; 
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
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7); /* Semi-transparent grayish background */
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

a.router-link-exact-active {
  color: black;
  text-decoration: none;
}
img{
  width: 500px;
  height: 500px;
  position: relative;
  right: 20px;
}
.img {
  width: 50%;
  background-color: #ffffff;
  margin: 50px 0px;
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
    box-shadow: rgba(0, 0, 0, 0.418) -8px 1px 10px 2px;
  display: flex;
  flex-flow: column wrap;
  align-items: center;
  justify-content: center;
}
.logo{
  font-family: Rubik Doodle Shadow;
  font-size: 100px;
}
</style>