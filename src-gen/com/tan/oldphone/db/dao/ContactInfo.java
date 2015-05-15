package com.tan.oldphone.db.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CONTACT_INFO.
 */
public class ContactInfo {

    private Long id;
    private String phoneNumber;
    private String name;
    private String pictureUrl;

    public ContactInfo() {
    }

    public ContactInfo(Long id) {
        this.id = id;
    }

    public ContactInfo(Long id, String phoneNumber, String name, String pictureUrl) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}