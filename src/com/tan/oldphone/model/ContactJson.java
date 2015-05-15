package com.tan.oldphone.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class ContactJson {

    @Expose
    private long code;
    @Expose
    private String message;
    @Expose
    private ContactData data;

    /**
     * 
     * @return
     *     The code
     */
    public long getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(long code) {
        this.code = code;
    }

    public ContactJson withCode(long code) {
        this.code = code;
        return this;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public ContactJson withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 
     * @return
     *     The data
     */
    public ContactData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(ContactData data) {
        this.data = data;
    }

    public ContactJson withData(ContactData data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
