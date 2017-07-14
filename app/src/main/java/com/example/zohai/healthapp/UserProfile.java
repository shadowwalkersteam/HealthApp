package com.example.zohai.healthapp;

public class UserProfile {

    public String name;
    public String age;
    public String email;
    public String phone;
    public String group;


    public UserProfile() {
        // Default Constructor
    }

    public UserProfile(String name, String age, String email, String phone){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
//        this.group = group;

    }
    public String getName() {
        return name;
    }
    public String getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
//    public String getGroup()
//    {
//        return group;
//    }

}

//    public String getGenderRadio() {
//        return GenderRadio;
//    }
//
//    public void setGenderRadio(String genderRadio) {
//        GenderRadio = genderRadio;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public String getEmailid() {
//        return emailid;
//    }
//
//    public void setEmailid(String emailid) {
//        this.emailid = emailid;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getBloodgrp() {
//        return bloodgrp;
//    }
//
//    public void setBloodgrp(String bloodgrp) {
//        this.bloodgrp = bloodgrp;
//    }
//
//    public String getFullname() {
//        return Fullname;
//    }
//
//    public void setFullname(String fullname) {
//        Fullname = fullname;
//    }
//}
