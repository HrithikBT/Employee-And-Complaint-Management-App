package com.example.ioclapp;

public class UserDetails {
    private String Empname;
    private String Empemail;
    private String Empage;
    private String Department;
    private String Floor;
    private String Asset1;
    private String Asset2;
    private String Asset3;
    private String Access;

    public UserDetails(String empname, String empage, String department, String floor, String asset1, String asset2, String asset3) {
        this.Empname = empname;
        this.Empage = empage;
        this.Department = department;
        this.Floor = floor;
        this.Asset1 = asset1;
        this.Asset2 = asset2;
        this.Asset3 = asset3;
    }

    public String getEmpname() {
        return Empname;
    }

    public void setEmpname(String empname) {
        Empname = empname;
    }

    public String getEmpemail() {
        return Empemail;
    }

    public void setEmpemail(String empemail) {
        Empemail = empemail;
    }

    public String getEmpage() {
        return Empage;
    }

    public void setEmpage(String empage) {
        Empage = empage;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getAsset1() {
        return Asset1;
    }

    public void setAsset1(String asset1) {
        Asset1 = asset1;
    }

    public String getAsset2() {
        return Asset2;
    }

    public void setAsset2(String asset2) {
        Asset2 = asset2;
    }

    public String getAsset3() {
        return Asset3;
    }

    public void setAsset3(String asset3) {
        Asset3 = asset3;
    }

    public String getAccess() {
        return Access;
    }

    public void setAccess(String access) {
        Access = access;
    }
    public UserDetails(){}

    public UserDetails(String empname, String empemail, String empage, String department, String floor, String asset1, String asset2, String asset3, String access) {
        this.Empname = empname;
        this.Empemail = empemail;
        this.Empage = empage;
        this.Department = department;
        this.Floor = floor;
        this.Asset1 = asset1;
        this.Asset2 = asset2;
        this.Asset3 = asset3;
        this.Access = access;
    }
}
