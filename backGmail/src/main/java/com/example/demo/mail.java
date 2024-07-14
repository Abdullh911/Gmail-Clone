package com.example.demo;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class mail {
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    private boolean isRead=false;
    private ArrayList<String> sentToMails;
    private String date;
    private String title;
    private String sender;
    private String message;
    private ArrayList<attachment>attachments;
    private int id;
    private String delDate;
    private int priorityLvl;

    public int getPriorityLvl() {
        return priorityLvl;
    }

    public void setPriorityLvl(int priorityLvl) {
        this.priorityLvl = priorityLvl;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public mail clone(mail x){
        mail res=new mail(x.isRead,x.sentToMails,x.date,x.title,x.sender,x.message,x.attachments,0,x.delDate,x.priorityLvl);

        return res;
    }
    public mail(){

    }
    public mail(boolean isRead, ArrayList<String> sentToMails, String date, String title, String sender, String message, ArrayList<attachment> attachments, int id, String delDate, int priorityLvl) {
        this.isRead = isRead;
        this.sentToMails = sentToMails;
        this.date = date;
        this.title = title;
        this.sender = sender;
        this.message = message;
        this.attachments = attachments;
        this.id = id;
        this.delDate = delDate;
        this.priorityLvl = priorityLvl;
    }

    @Override
    public String toString() {
        return "mail{" +
                "isRead=" + isRead +
                ", sentToMails=" + sentToMails +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", attachments=" + attachments +
                ", id=" + id +
                ", delDate='" + delDate + '\'' +
                ", priorityLvl=" + priorityLvl +
                '}';
    }

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    public ArrayList<attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<attachment> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<String> getSentToMails() {
        return sentToMails;
    }

    public void setSentToMails(ArrayList<String> sentToMails) {
        this.sentToMails = sentToMails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}