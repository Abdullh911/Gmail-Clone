<template>
  <div class="all">
    <section class="d-flex window">
      <sideBar />
      <div v-if="this.msg" class="body d-flex flex-column align-items-start">
        <h3>Audio message</h3>
        <br>
        <button class="audButton" @click="showAudio">
          <span>View Audio message</span>
        </button>
        <br>
        <div v-show="showAud">
          <audio controls ref="audioPlayer">
            Your browser does not support the audio element.
          </audio>
        </div>
        <h2>{{ this.msg.title }}</h2>
        <div class="info d-flex flex-row justify-content-between">
          <h5>{{ this.msg.sender }}</h5>
          <h5>{{ this.msg.date }}</h5>
        </div>
        <p>{{ this.msg.message }}</p>
        <div class="d-flex justify-content-evenly">
          <button
            v-for="attch in this.msg.attachments"
            :key="attch.attName"
            v-show="this.msg.attachments"
            @click="download(attch)"
            class="download-button"
          >
            <div class="docs">
              <svg
                class="css-i6dzq1"
                stroke-linejoin="round"
                stroke-linecap="round"
                fill="none"
                stroke-width="2"
                stroke="currentColor"
                height="20"
                width="20"
                viewBox="0 0 24 24"
              >
                <path
                  d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"
                ></path>
                <polyline points="14 2 14 8 20 8"></polyline>
                <line y2="13" x2="8" y1="13" x1="16"></line>
                <line y2="17" x2="8" y1="17" x1="16"></line>
                <polyline points="10 9 9 9 8 9"></polyline>
              </svg>
              {{ attch.attName }}
            </div>
            <div class="download">
              <svg
                class="css-i6dzq1"
                stroke-linejoin="round"
                stroke-linecap="round"
                fill="none"
                stroke-width="2"
                stroke="currentColor"
                height="24"
                width="24"
                viewBox="0 0 24 24"
              >
                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                <polyline points="7 10 12 15 17 10"></polyline>
                <line y2="3" x2="12" y1="15" x1="12"></line>
              </svg>
            </div>
          </button>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import $store from "../store/index.js";
import sideBar from "@/components/sideBar.vue";
export default {
  props: ["id"],
  components: {
    sideBar,
  },
  data() {
    return {
      msg: {
        id: 0,
        title: "",
        sender: "",
        date: "",
        message: "",
      },
      showAud:false,
    };
  },
  methods: {
    showAudio(){
      this.showAud=!this.showAud;
    },
    async getAudio() {
  let rsala = this.msg.message;
  rsala = rsala.split(' ').join('%20');
  console.log(this.msg);
  const url = `https://voicerss-text-to-speech.p.rapidapi.com/?key=a3801a6c985d4c6583e980fb4ba8bfe7&src=${rsala}&hl=en-us&r=0&c=mp3&f=8khz_8bit_mono`;
  const options = {
    method: 'GET',
    headers: {
      'X-RapidAPI-Key': 'fe9219690cmshc641c468ac1874dp156f63jsna03d6a220172',
      'X-RapidAPI-Host': 'voicerss-text-to-speech.p.rapidapi.com'
    }
  };

  try {
    const response = await fetch(url, options);
    const blob = await response.blob();
    const audioUrl = URL.createObjectURL(blob);

    // Update the source of the audio element
    this.$refs.audioPlayer.src = audioUrl;

    
  } catch (error) {
    console.error(error);
  }
},


    download(attch) {
      this.downloadAttachment(attch);
    },

    async downloadAttachment(attch) {
      try {
        const binaryData = atob(attch.attachment);
        const arrayBuffer = new ArrayBuffer(binaryData.length);
        const view = new Uint8Array(arrayBuffer);
        for (let i = 0; i < binaryData.length; i++) {
          view[i] = binaryData.charCodeAt(i);
        }

        const blob = new Blob([arrayBuffer], {
          type: attch.attType || "application/octet-stream",
        });

        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = attch.attName;

        document.body.appendChild(link);
        link.click();

        document.body.removeChild(link);
      } catch (error) {
        console.error("Error downloading attachment:", error);
      }
    },
  },
  created() {
    const userDataString = localStorage.getItem("userData");
    let em = JSON.parse(userDataString).email;
    fetch(`http://localhost:8080/getMail/${em}/${this.id}`, {
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
      .then((msgData) => {
        console.log(msgData);
        this.msg = msgData;
        this.getAudio();
      })
      .catch((error) => {
        console.error("Error during login:", error);
      });
      
      console.log(11111);
  },
  mounted() {
    console.log(this.id);
    console.log(this.msg);
    setInterval(() => {
      this.messages =
        $store.state.currUser && $store.state.currUser.inbox
          ? $store.state.currUser.inbox
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
          $store.commit("setCurrUser", newData);
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
    }
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
  height: 635px;
  border-top-right-radius: 20px;
  border-bottom-right-radius: 20px;
  box-shadow: 3px 0px 14px 0px #00000086;
  padding: 20px 40px;
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
  height: 700px;
}
p {
  text-align: left;
  padding: 30px;
}
h2 {
  font-size: 40px;
  padding: 30px;
}
h5 {
  font-size: 20px;
}
.info {
  padding: 20px;
  width: 100%;
}
.butn button {
  padding: 10px 20px;
  border-radius: 20px;
  border: none;
  background-color: #53c6ba;
}
.butn {
  padding: 40px;
  width: 100%;
}
.butn button:hover {
  background-color: #53c6ba;
  color: white;
}
.window {
  height: 100vh;
}
.flex-column {
  height: 100vh;
}
.download-button {
  position: relative;
  border-width: 0;
  color: white;
  font-size: 15px;
  font-weight: 600;
  border-radius: 4px;
  z-index: 1;
}

.download-button .docs {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  min-height: 40px;
  padding: 0 10px;
  border-radius: 4px;
  z-index: 1;
  background-color: #242a35;
  border: solid 1px #e8e8e82d;
  transition: all 0.5s cubic-bezier(0.77, 0, 0.175, 1);
}

.download-button:hover {
  box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px,
    rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px,
    rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;
}

.download {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  max-width: 90%;
  margin: 0 auto;
  z-index: -1;
  border-radius: 4px;
  transform: translateY(0%);
  background-color: rgba(255, 0, 0, 0.582);
  border: solid 1px #01e0572d;
  transition: all 0.5s cubic-bezier(0.77, 0, 0.175, 1);
}

.download-button:hover .download {
  transform: translateY(100%);
}

.download svg polyline,
.download svg line {
  animation: docs 1s infinite;
}

@keyframes docs {
  0% {
    transform: translateY(0%);
  }

  50% {
    transform: translateY(-15%);
  }

  100% {
    transform: translateY(0%);
  }
}




.audButton {
  --fs: 1.25em;
  --col1: honeydew;
  --col2: rgba(240, 128, 128, 0.603);
  --col3: indianred;
  --col4: maroon;
  --pd: .5em .65em;
  display: grid;
  align-content: baseline;
  appearance: none;
  border: 0;
  grid-template-columns: min-content 1fr;
  padding: var(--pd);
  font-size: var(--fs);
  color: var(--col1);
  background-color: var(--col3);
  border-radius: 6px;
  text-shadow: 1px 1px var(--col4);
  box-shadow: inset -2px 1px 1px var(--col2),
    inset 2px 1px 1px var(--col2);
  position: relative;
  transition: all .75s ease-out;
  transform-origin: center;
}

.audButton:hover {
  color: var(--col4);
  box-shadow: inset -2px 1px 1px var(--col2),
    inset 2px 1px 1px var(--col2),
    inset 0px -2px 20px var(--col4),
    0px 20px 30px var(--col3),
    0px -20px 30px var(--col2),
    1px 2px 20px var(--col4);
  text-shadow: 1px 1px var(--col2);
}

.audButton:active {
  animation: offset 1s ease-in-out infinite;
  outline: 2px solid var(--col2);
  outline-offset: 0;
}

.audButton::after,
.audButton::before {
  content: '';
  align-self: center;
  justify-self: center;
  height: .5em;
  margin: 0 .5em;
  grid-column: 1;
  grid-row: 1;
  opacity: 1;
}

.audButton::after {
  position: relative;
  border: 2px solid var(--col4);
  border-radius: 50%;
  transition: all .5s ease-out;
  height: .1em;
  width: .1em;
}

.audButton:hover::after {
  border: 2px solid var(--col3);
  transform: rotate(-120deg) translate(10%, 140%);
}

.audButton::before {
  border-radius: 50% 0%;
  border: 4px solid var(--col4);
  box-shadow: inset 1px 1px var(--col2);
  transition: all 1s ease-out;
  transform: rotate(45deg);
  height: .45em;
  width: .45em;
}

.audButton:hover::before {
  border-radius: 50%;
  border: 4px solid var(--col1);
  transform: scale(1.25) rotate(0deg);
  animation: blink 1.5s ease-out 1s infinite alternate;
}

.audButton:hover > span {
  filter: contrast(150%);
}

@keyframes blink {
  0% {
    transform: scale(1, 1) skewX(0deg);
    opacity: 1;
  }

  5% {
    transform: scale(1.5, .1) skewX(10deg);
    opacity: .5;
  }

  10%,
  35% {
    transform: scale(1, 1) skewX(0deg);
    opacity: 1;
  }

  40% {
    transform: scale(1.5, .1) skewX(10deg);
    opacity: .25;
  }

  45%,
  100% {
    transform: scale(1, 1) skewX(0deg);
    opacity: 1;
  }
}

@keyframes offset {
  50% {
    outline-offset: .15em;
    outline-color: var(--col1);
  }

  55% {
    outline-offset: .1em;
    transform: translateY(1px);
  }

  80%,
  100% {
    outline-offset: 0;
  }
}
</style>