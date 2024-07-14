import { createStore } from 'vuex'
export default createStore({
    state: {
        token: localStorage.getItem('token') || null,
        currUser: JSON.parse(localStorage.getItem('userData')) || null,
        isLoggedIn: localStorage.getItem('token') !== null,
        loginStatus: false,
        forgetUser: null,
        currDraftMsg: {
            sentToMails: [],
            date: "",
            title: "",
            sender: "",
            message: "",
            id: 0,
            isRead: false,
        },
        holdDraft: false,
        selectedMsg: -1,
        search: "",
        searchType: "",
        selectedMsg: -1,
        sendClicked: false,
        wantedContact: "",
        inboxmirror: [],
        sentmirror: [],
        draftmirror: [],
        trashmirror: [],
        foldersmirror: [],
        willBeSentId: 0,
        srtPriority: false,
        signOutClicked:false,
    },
    mutations: {
        setSrtPriority(state, status) {
            state.srtPriority = status
        },
        setSignOut(state, status) {
            state.signOutClicked = status
        },
        setWillBeSentId(state, status) {

        },
        setWantedContact(state, status) {
            state.wantedContact = status;
        },
        setCurrUser(state, user) {
            state.currUser = user;
            if (state.currUser !== null) {
                state.currUser.inbox.sort((a, b) => {
                    return b.id - a.id;
                })
                state.currUser.draft.sort((a, b) => {
                    return b.id - a.id;
                })
                state.currUser.trash.sort((a, b) => {
                    return b.id - a.id;
                })
                state.currUser.sent.sort((a, b) => {
                    return b.id - a.id;
                })
            }
        },
        setSearch(state, search) {
            state.search = search;
        },
        setSendClicked(state, status) {
            state.sendClicked = status
        },
        setSearchType(state, searchType) {
            state.searchType = searchType
        },
        setCurrMsg(state, status) {
            state.currDraftMsg = status
        },
        updatepriority(state, payload) {
            const { msg, usersRate, route, index } = payload;
            switch (route) {
                case "inbox":
                    for(let i = 0 ; i < state.currUser.inbox.length ; i++){
                        if(state.currUser.inbox[i].id === msg.id){
                            state.currUser.inbox[i].priorityLvl = usersRate ;
                        }
                    }
                    break;
                case "sent":
                    for(let i = 0 ; i < state.currUser.sent.length ; i++){
                        if(state.currUser.sent[i].id === msg.id){
                            state.currUser.sent[i].priorityLvl = usersRate ;
                        }
                    }
                    break;
                case "folder":
                    for(let i = 0 ; i < state.currUser.folders[index].messages.length ; i++){
                        if(state.currUser.folders[index].messages[i].id === msg.id){
                            state.currUser.folders[index].messages[i].priorityLvl = usersRate ;
                        }
                    }
                    break;
            }
        },
        setHoldDraft(state, status) {
            state.holdDraft = status
        },
        setCurrDraftMsg(state, status) {
            state.currDraftMsg = status;
        },
        setSelectedMsg(state, status) {
            state.selectedMsg = status
        },
        setLoginStatus(state, status) {
            state.loginStatus = status;
        },
        setForgetUser(state, user) {
            state.forgetUser = user;
        },
        moveToTrash(state, msg) {
            state.currUser.trash.push(msg);
            state.currUser.inbox = state.currUser.inbox.filter(m => m.id !== msg.id);
            state.currUser.sent = state.currUser.sent.filter(m => m.id !== msg.id);
        },
        updateMsg(state, msg) {
            if (state.currUser.inbox.some(m => m.id === msg.id)) {
                state.currUser.inbox = state.currUser.inbox.filter(m => m.id !== msg.id);
                state.currUser.inbox.push(msg);
            }
            else if (state.currUser.sent.some(m => m.id === msg.id)) {
                state.currUser.sent = state.currUser.sent.filter(m => m.id !== msg.id);
                state.currUser.sent.push(msg);
            }
            else if (state.currUser.trash.some(m => m.id === msg.id)) {
                state.currUser.trash = state.currUser.trash.filter(m => m.id !== msg.id);
                state.currUser.trash.push(msg);
            }
            else {
                for (let i = 0; i < state.currUser.folders.length; i++) {
                    if (state.currUser.folders[i].messages.some(m => m.id === msg.id)) {
                        state.currUser.folders[i].messages = state.currUser.folders[i].messages.filter(m => m.id !== msg.id);
                        state.currUser.folders[i].messages.push(msg);
                    }
                }
            }
        },
        sortMsgAsc(state) {
            state.currUser.inbox.sort((a, b) => {
                return a.id - b.id;
            });
            state.currUser.trash.sort((a, b) => {
                return a.id - b.id;
            });
            state.currUser.draft.sort((a, b) => {
                return a.id - b.id;
            });
            state.currUser.sent.sort((a, b) => {
                return a.id - b.id;
            });
            for (let i = 0; i < state.currUser.folders.length; i++) {
                state.currUser.folders[i].messages.sort((a, b) => {
                    return a.id - b.id;
                });
            }
        },
        sortMsgDec(state) {
            state.currUser.inbox.sort((a, b) => {
                return b.id - a.id;
            });
            state.currUser.trash.sort((a, b) => {
                return b.id - a.id;
            });
            state.currUser.draft.sort((a, b) => {
                return b.id - a.id;
            });
            state.currUser.sent.sort((a, b) => {
                return b.id - a.id;
            });
            for (let i = 0; i < state.currUser.folders.length; i++) {
                state.currUser.folders[i].messages.sort((a, b) => {
                    return b.id - a.id;
                });
            }
        },
        sortMsgPri(state) {
            state.currUser.inbox.sort((a, b) => {
                return b.priorityLvl - a.priorityLvl;
            });
            state.currUser.trash.sort((a, b) => {
                return b.priorityLvl - a.priorityLvl;
            });
            state.currUser.draft.sort((a, b) => {
                return b.priorityLvl - a.priorityLvl;
            });
            state.currUser.sent.sort((a, b) => {
                return b.priorityLvl - a.priorityLvl;
            });
            for (let i = 0; i < state.currUser.folders.length; i++) {
                state.currUser.folders[i].messages.sort((a, b) => {
                    return b.priorityLvl - a.priorityLvl;
                });
            }
        },
        restore(state, msg) {
            state.currUser.trash = state.currUser.trash.filter(m => m.id !== msg.id);
            if (msg.sender === state.currUser.email) {
                state.currUser.sent.push(msg);
                state.currUser.inbox.push(msg);
            }
            else {
                state.currUser.inbox.push(msg);
            }
        },
        deleteMsg(state, msg) {
            state.currUser.draft = state.currUser.draft.filter(m => m !== msg);
        },
        searchMsg(state, search) {
            if (state.searchType == "title") {
                state.currUser.inbox = state.currUser.inbox.filter((m) => m.title.toLowerCase().includes(search.toLowerCase()));
                state.currUser.trash = state.currUser.trash.filter((m) => m.title.toLowerCase().includes(search.toLowerCase()));
                state.currUser.draft = state.currUser.draft.filter((m) => m.title.toLowerCase().includes(search.toLowerCase()));
                state.currUser.sent = state.currUser.sent.filter((m) => m.title.toLowerCase().includes(search.toLowerCase()));
            }
            else if (state.searchType == "date") {
                state.currUser.inbox = state.currUser.inbox.filter((m) => m.date.toLowerCase().includes(search.toLowerCase()));
                state.currUser.trash = state.currUser.trash.filter((m) => m.date.toLowerCase().includes(search.toLowerCase()));
                state.currUser.draft = state.currUser.draft.filter((m) => m.date.toLowerCase().includes(search.toLowerCase()));
                state.currUser.sent = state.currUser.sent.filter((m) => m.date.toLowerCase().includes(search.toLowerCase()));
            }
            else if (state.searchType == "message") {
                state.currUser.inbox = state.currUser.inbox.filter((m) => m.message.toLowerCase().includes(search.toLowerCase()));
                state.currUser.trash = state.currUser.trash.filter((m) => m.message.toLowerCase().includes(search.toLowerCase()));
                state.currUser.draft = state.currUser.draft.filter((m) => m.message.toLowerCase().includes(search.toLowerCase()));
                state.currUser.sent = state.currUser.sent.filter((m) => m.message.toLowerCase().includes(search.toLowerCase()));
            }
            else if (state.searchType == "sender") {
                state.currUser.inbox = state.currUser.inbox.filter((m) => m.sender.toLowerCase().includes(search.toLowerCase()));
                state.currUser.trash = state.currUser.trash.filter((m) => m.sender.toLowerCase().includes(search.toLowerCase()));
                state.currUser.draft = state.currUser.draft.filter((m) => m.sender.toLowerCase().includes(search.toLowerCase()));
                state.currUser.sent = state.currUser.sent.filter((m) => m.sender.toLowerCase().includes(search.toLowerCase()));
            }
            else if (state.searchType == "attachment") {
                state.currUser.inbox = state.currUser.inbox.filter((m) => m.attachments && m.attachments.some(att => att.attName.toLowerCase().includes(search.toLowerCase())));
                state.currUser.trash = state.currUser.trash.filter((m) => m.attachments && m.attachments.some(att => att.attName.toLowerCase().includes(search.toLowerCase())));
                state.currUser.draft = state.currUser.draft.filter((m) => m.attachments && m.attachments.some(att => att.attName.toLowerCase().includes(search.toLowerCase())));
                state.currUser.sent = state.currUser.sent.filter((m) => m.attachments && m.attachments.some(att => att.attName.toLowerCase().includes(search.toLowerCase())));
            }
        },
        createfolder(state, newfolder) {
            state.currUser.folders.push(newfolder);
        },
        deletefolder(state, folder) {
            state.currUser.folders = state.currUser.folders.filter(f => f.foldername !== folder.foldername);
            for (let i = 0; i < state.currUser.folders.length; i++) {
                state.currUser.folders[i].folderindex = i;
            }
        },
        deletemsgfromfolder(state, msg) {
            state.currUser.folders[msg.index].messages = state.currUser.folders[msg.index].messages.filter(m => m !== msg.message);
        },
        choose(state, payload) {
            const { msg, route, index } = payload;
            switch (route) {
                case "inbox":
                    state.inboxmirror.push(msg.id);
                    break;
                case "sent":
                    state.sentmirror.push(msg.id);
                    break;
                case "trash":
                    state.trashmirror.push(msg.id);
                    break;
                case "draft":
                    state.draftmirror.push(msg.id);
                    break;
                case "folder":
                    state.foldersmirror.push(msg.id);
                    break;
            }
        },
        unchoose(state, payload) {
            const { msg, route, index } = payload;
            switch (route) {
                case "inbox":
                    state.inboxmirror = state.inboxmirror.filter(m => m !== msg.id);
                    break;
                case "sent":
                    state.sentmirror = state.sentmirror.filter(m => m !== msg.id);
                    break;
                case "trash":
                    state.trashmirror = state.trashmirror.filter(m => m !== msg.id);
                    break;
                case "draft":
                    state.draftmirror = state.draftmirror.filter(m => m !== msg.id);
                    break;
                case "folder":
                    state.foldersmirror = state.foldersmirror.filter(m => m !== msg.id);
                    break;
            }
        },
        rename(state, fld) {
            state.currUser.folders[fld.folderindex].foldername = fld.foldername;
        },
        clear(state){
            state.inboxmirror = [] ;
            state.draftmirror = [] ;
            state.sentmirror = [] ;
            state.trashmirror = [] ;
            state.foldersmirror = [] ;
        },
    },
    actions: {
    },
    modules: {}
})