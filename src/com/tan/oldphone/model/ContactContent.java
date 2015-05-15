package com.tan.oldphone.model;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class ContactContent {

	@Expose
    private String phoneNumber;
    @Expose
    private String name;
    @Expose
    private long id;
    @Expose
    private String picture;
    
    
    

    public ContactContent() {
		super();
	}

	public ContactContent(String phoneNumber, String name, long id,
			String picture) {
		super();
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.id = id;
		this.picture = picture;
	}

	/**
     * 
     * @return
     *     The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 
     * @param phoneNumber
     *     The phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ContactContent withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public ContactContent withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The id
     */
    public long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(long id) {
        this.id = id;
    }

    public ContactContent withId(long id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 
     * @param picture
     *     The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ContactContent withPicture(String picture) {
        this.picture = picture;
        return this;
    }

//    @Override
//    public String toString() {
//    	
////        return ToStringBuilder.reflectionToString(this);
//    }

    @Override
	public String toString() {
		return "ContactContent [phoneNumber=" + phoneNumber + ", name=" + name
				+ ", id=" + id + ", picture=" + picture + "]";
	}
    
    

}
