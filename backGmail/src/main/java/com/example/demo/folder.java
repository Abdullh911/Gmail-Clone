package com.example.demo;

import java.util.ArrayList;

public class folder {
    private ArrayList<mail> messages ;
    private String foldername ;
    private int folderindex ;

    public folder(ArrayList<mail> messages, String foldername, int folderindex) {
        this.messages = messages;
        this.foldername = foldername;
        this.folderindex = folderindex;
    }

    public int getFolderindex() {
        return folderindex;
    }

    public void setFolderindex(int folderindex) {
        this.folderindex = folderindex;
    }

    public ArrayList<mail> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<mail> messages) {
        this.messages = messages;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }
}
