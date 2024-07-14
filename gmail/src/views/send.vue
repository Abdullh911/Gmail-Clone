<template>
  <div class="all">
    <section class="d-flex window">
      <sideBar />
      <div class="body flex-column">
        <div class="row">
          <label>Title:</label>
          <input v-model="mail.title" type="text" />
        </div>
        <div class="row">
          <label>sent To:</label>
          <input v-model="sentto" type="text" />
        </div>
        <div class="row">
          <label>Message:</label>
          <textarea v-model="mail.message"></textarea>
        </div>
        <div class="d-flex attch">
          <label><i class="fa-solid fa-paperclip"></i></label>
          <input
            style="color: transparent"
            type="file"
            multiple
            ref="fileInput"
            @change="handleFileChange"
          />
        </div>
        <div
          class="d-flex attch"
          v-for="attch in attachmentOBJ"
          :key="attch.attName"
        >
          <p>{{ attch.attName }}</p>
          <button @click="remove(attch)">x</button>
        </div>
        <div>
          <div class="bttn d-flex flex-row-reverse row">
            <button @click="sendMail">
              <p>Send</p>
              <svg
                stroke-width="4"
                stroke="currentColor"
                viewBox="0 0 24 24"
                fill="none"
                class="h-6 w-6"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M14 5l7 7m0 0l-7 7m7-7H3"
                  stroke-linejoin="round"
                  stroke-linecap="round"
                ></path>
              </svg>
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
  created() {
    if ($store.state.currUser) {
      this.sender = $store.state.currUser.email;
    }
  },
  mounted() {
    $store.commit("setSendClicked", false);
    if ($store.state.selectedMsg === -1) {
      this.sentto = $store.state.wantedContact;
    }
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
    this.getCurrentDate();
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
  components: {
    sideBar,
  },
  beforeUnmount() {
    $store.commit("setWantedContact", "");
    console.log(!$store.state.sendClicked);
    if (
      !$store.state.holdDraft &&
      $store.state.currUser !== null &&
      (this.mail.title.length !== 0 ||
        this.mail.sentToMails.length !== 0 ||
        this.mail.message.length !== 0) &&
      !$store.state.sendClicked
    ) {
      this.addToDraft();
      console.log("yoooooooooooo");
    } else if ($store.state.holdDraft && !$store.state.sendClicked) {
      console.log(localStorage.getItem("willBeSentId"));
      let emails = [];
      emails = this.sentto.split(",");
      let attachedfile = null;
      let attachmenttype = null;
      let attachmentName = null;

      let SentObj = null;
      if (this.selectedFile !== null) {
        SentObj = this.attachmentOBJ;
        console.log("yo");
      }
      let z = localStorage.getItem("willBeSentId");
      const mailObject = {
        sentToMails: emails,
        date: this.mail.date,
        title: this.mail.title,
        sender: this.sender,
        message: this.mail.message,
        delDate : this.mail.delDate,
        attachments: SentObj,
        id: z,
        isRead: false,
        priorityLvl: 0,
      };
      fetch(
        `http://localhost:8080/renewDraft/${localStorage.getItem(
          "willBeSentId"
        )}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(mailObject),
        }
      )
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to send mail");
          }
          return response.json();
        })
        .then((data) => {
          console.log("Server response:", data);
        })
        .catch((error) => {
          console.error("Error:", error.message);
        });
    }

    let currDraftMsg = {
      sentToMails: [],
      date: "",
      title: "",
      sender: "",
      message: "",
      id: 0,
      isRead: false,
    };
    $store.commit("setCurrMsg", currDraftMsg);
    $store.commit("setHoldDraft", false);
  },
  data() {
    return {
      selectedFile: null,
      sentto: "",
      mail: {
        sentToMails: $store.state.currDraftMsg.sentToMails,
        date: $store.state.currDraftMsg.date,
        title: $store.state.currDraftMsg.title,
        sender: $store.state.currDraftMsg.sender,
        message: $store.state.currDraftMsg.message,
        id: 0,
        isRead: false,
        delDate: "",
      },
      attachmentOBJ: [],
    };
  },
  methods: {
    remove(attch) {
      this.attachmentOBJ = this.attachmentOBJ.filter(
        (m) => m.attName !== attch.attName
      );
    },
    getCurrentDate() {
      const now = new Date();
      const timeZone = "Europe/Athens";
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
      };
      const formatter = new Intl.DateTimeFormat(undefined, {
        ...options,
        timeZone,
      });
      this.mail.date = formatter.format(now);
      let delDateDay = now.getDate();
      let delDateMonth = now.getMonth() + 1;
      let delDateYear = now.getFullYear();
      this.mail.delDate = delDateMonth + "/" + delDateDay + "/" + delDateYear;
    },

    async handleFileChange() {
      this.selectedFile = this.$refs.fileInput.files;
      for (let i = 0; i < this.selectedFile.length; i++) {
        let tempOBJ = {
          attachment: await this.convertFileToBase64(this.selectedFile[i]),
          attType: this.selectedFile[i].type,
          attName: this.selectedFile[i].name,
        };
        this.attachmentOBJ.push(tempOBJ);
      }
      console.log(this.attachmentOBJ);
      console.log(this.selectedFile);
    },
    sendMail() {
      let emails = [];
      console.log(this.sentto);
      emails = this.sentto.split(",");
      let attachedfile = null;
      let attachmenttype = null;
      let attachmentName = null;

      let SentObj = null;
      if (this.selectedFile !== null) {
        SentObj = this.attachmentOBJ;
        console.log("yo");
      }
      let z = localStorage.getItem("willBeSentId");
      const mailObject = {
        sentToMails: emails,
        date: this.mail.date,
        title: this.mail.title,
        sender: this.sender,
        message: this.mail.message,
        delDate: this.mail.delDate,
        attachments: SentObj,
        id: z,
        isRead: false,
        priorityLvl: 0,
      };
      console.log(mailObject);
      fetch(`http://localhost:8080/sendMail`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(mailObject),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to send mail");
      }
      const contentType = response.headers.get("content-type");
      if (contentType && contentType.includes("application/json")) {
        return response.json();
      } else {
        return { message: "Mail sent successfully" };
      }
    })
    .then((data) => {
      console.log("Server response:", data.message);
    })
    .catch((error) => {
      console.error("Error:", error.message);
    });
      console.log($store.state.selectedMsg);
      if ($store.state.selectedMsg > 0) {
        let x = $store.state.selectedMsg;
        console.log(x);
        mailObject.id = x;
        fetch(`http://localhost:8080/removeDraft/${x}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(mailObject),
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Failed to send mail");
            }
            this.$router.push({ name: "inbox" });
            // return response.json();
          })
          .then((data) => {
            console.log("Server response:", data);
          })
          .catch((error) => {
            console.error("Error:", error.message);
          });
      }
      $store.commit("setSendClicked", true);
      $store.commit("setSelectedMsg", -1);
      console.log($store.state.selectedMsg);
      this.$router.push({ name: "inbox" });
    },
    addToDraft() {
      let emails = [];
      emails = this.sentto.split(",");
      let attachedfile = null;
      let attachmenttype = null;
      let attachmentName = null;

      let SentObj = null;
      if (this.selectedFile !== null) {
        SentObj = this.attachmentOBJ;
        console.log("yo");
      }
      const mailObject = {
        sentToMails: emails,
        date: this.mail.date,
        title: this.mail.title,
        sender: this.sender,
        message: this.mail.message,
        delDate: this.mail.delDate,
        attachments: SentObj,
        id: 0,
        isRead: false,
        priorityLvl: 0,
      };
      console.log(mailObject, "hya di");
      fetch(`http://localhost:8080/draft`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(mailObject),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to send mail");
          }
          // return response.json();
        })
        .then((data) => {
          console.log("Server response:", data);
        })
        .catch((error) => {
          console.error("Error:", error.message);
        });
    },
    convertFileToBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result.split(",")[1]);
        reader.onerror = (error) => reject(error);
      });
    },
    // getusers() {
    //   fetch(`http://localhost:8080/users`, {
    //     method: "GET",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //   })
    //     .then((response) => {
    //       if (!response.ok) {
    //         throw new Error("Failed to send mail");
    //       }
    //       return response.json();
    //     })
    //     .then((data) => {
    //       console.log(data);
    //       console.log(
    //         data[0].sent[1].attachment,
    //         "new",
    //         data[0].sent[1].attType
    //       );
    //       this.downloadAttachment(
    //         data[0].sent[1].attachment,
    //         "new",
    //         data[0].sent[1].attType
    //       );
    //     })
    //     .catch((error) => {
    //       console.error("Error:", error.message);
    //     });
    // },

    // async downloadAttachment(attachmentData, fileName, fileType) {
    //   try {
    //     const binaryData = atob(attachmentData);

    //     const arrayBuffer = new ArrayBuffer(binaryData.length);
    //     const view = new Uint8Array(arrayBuffer);
    //     for (let i = 0; i < binaryData.length; i++) {
    //       view[i] = binaryData.charCodeAt(i);
    //     }

    //     const blob = new Blob([arrayBuffer], {
    //       type: fileType || "application/octet-stream",
    //     });

    //     const link = document.createElement("a");
    //     link.href = URL.createObjectURL(blob);
    //     link.download = fileName;

    //     document.body.appendChild(link);
    //     link.click();

    //     document.body.removeChild(link);
    //   } catch (error) {
    //     console.error("Error downloading attachment:", error);
    //   }
    // },
  },
};
</script>

<style scoped>
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
  height: 100vh;
}
.row {
  margin: 10px;
}
label {
  text-align: left;
  margin-right: 10px;
}
textarea {
  width: 100%;
  min-height: 200px;
  max-height: max-content;
}
.fa-brands {
  margin: 10px;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  font-size: 40px;
}
textarea,
input {
  border-radius: 10px;
  margin-right: 5px;
  display: block;
  padding: 2px 1px;
  width: 100%;
  border: none;
  border-bottom: 2px solid #ddd;
  transition: all 0.1s ease-in;
  color: black;
}
textarea,
input:focus {
  border-bottom: 5px solid #ddd;
  outline: none;
}
.attch {
  margin: 10px;
  width: max-content;
}
.bttn {
  max-width: 80px;
}
button {
  padding: 0;
  margin: 0;
  border: none;
  background: none;
}
.window {
  height: 100vh;
}
.flex-column {
  height: 100vh;
}
button {
  --primary-color: #111;
  --hovered-color: #c84747;
  position: relative;
  display: flex;
  font-weight: 600;
  font-size: 20px;
  gap: 0.5rem;
  align-items: center;
}

button p {
  margin: 0;
  position: relative;
  font-size: 20px;
  color: var(--primary-color);
}

button::after {
  position: absolute;
  content: "";
  width: 0;
  left: 0;
  bottom: -7px;
  background: var(--hovered-color);
  height: 2px;
  transition: 0.3s ease-out;
}

button p::before {
  position: absolute;
  content: "Send";
  width: 0%;
  inset: 0;
  color: var(--hovered-color);
  overflow: hidden;
  transition: 0.3s ease-out;
}

button:hover::after {
  width: 100%;
}

button:hover p::before {
  width: 100%;
}

button:hover svg {
  transform: translateX(4px);
  color: var(--hovered-color);
}

button svg {
  color: var(--primary-color);
  transition: 0.2s;
  position: relative;
  width: 15px;
  transition-delay: 0.2s;
}
</style>
