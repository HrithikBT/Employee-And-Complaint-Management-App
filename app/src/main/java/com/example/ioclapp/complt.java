package com.example.ioclapp;

import java.sql.Date;
import java.sql.Timestamp;

public class complt {
    private String complainttext;
    private String Status;
    private String Uid;
    private String ts;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public complt(String text, String status, String uid, String ts) {
        this.complainttext = text;
        this.Status = status;
        this.Uid = uid;
        this.ts =ts;

    }
    public complt(){}

    public String getComplainttext() {
        return complainttext;
    }

    public void setComplainttext(String text) {
        this.complainttext = text;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }
}
