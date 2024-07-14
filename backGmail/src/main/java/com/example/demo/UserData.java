package com.example.demo;

import javax.crypto.SecretKey;
import java.util.ArrayList;

public class UserData {
    public UserData(){

    }
    public UserData(int index, String email, String password, String securityQuestion, String securityAnswer,
                    String firstName, String lastName, String gender, ArrayList<mail> inbox, ArrayList<mail> sent,
                    ArrayList<mail> draft, ArrayList<mail> trash, ArrayList<mail> favourites, ArrayList<Object> folders) {
        this.index = index;
        this.email = email;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.inbox = inbox;
        this.sent = sent;
        this.draft = draft;
        this.trash = trash;
        this.favourites = favourites;
        this.folders = folders;
        this.contacts = new ArrayList<>(); // Initialize the contacts field
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<mail> getInbox() {
        return inbox;
    }

    public void setInbox(ArrayList<mail> inbox) {
        this.inbox = inbox;
    }

    public ArrayList<mail> getSent() {
        return sent;
    }

    public void setSent(ArrayList<mail> sent) {
        this.sent = sent;
    }

    public ArrayList<mail> getDraft() {
        return draft;
    }

    public void setDraft(ArrayList<mail> draft) {
        this.draft = draft;
    }

    public ArrayList<mail> getTrash() {
        return trash;
    }

    public void setTrash(ArrayList<mail> trash) {
        this.trash = trash;
    }
    private int index;
    private String email;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private String firstName;
    private String lastName;
    private String gender;
    private ArrayList<Contact>contacts;


    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<mail> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<mail> favourites) {
        this.favourites = favourites;
    }

    private ArrayList<mail>inbox;
    private ArrayList<mail>sent;
    private ArrayList<mail>draft;
    private ArrayList<mail>trash; // add spam
    private ArrayList<mail> favourites ;
    private ArrayList<Object> folders ;

    public ArrayList<Object> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Object> folders) {
        this.folders = folders;
    }

    private int msgId=0;

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public void addMailInbox(mail x){
        inbox.add(x);
    }
    public void addMailDraft(mail x){
        draft.add(x);
    }
    public void addMailTrash(mail x){
        trash.add(x);
    }
    public void addMailSent(mail x){
        sent.add(x);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "index=" + index +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", contacts=" + contacts +
                ", inbox=" + inbox +
                ", sent=" + sent +
                ", draft=" + draft +
                ", trash=" + trash +
                ", favourites=" + favourites +
                ", folders=" + folders +
                ", msgId=" + msgId +
                '}';
    }
}