
package com.tan.oldphone.model;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class ContactPublishJson {

    @Expose
    private String action;
    @Expose
    private ContactPublishData data;

    /**
     * 
     * @return
     *     The action
     */
    public String getAction() {
        return action;
    }

    /**
     * 
     * @param action
     *     The action
     */
    public void setAction(String action) {
        this.action = action;
    }

    public ContactPublishJson withAction(String action) {
        this.action = action;
        return this;
    }

    /**
     * 
     * @return
     *     The data
     */
    public ContactPublishData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(ContactPublishData data) {
        this.data = data;
    }

    public ContactPublishJson withData(ContactPublishData data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(action).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ContactPublishJson) == false) {
            return false;
        }
        ContactPublishJson rhs = ((ContactPublishJson) other);
        return new EqualsBuilder().append(action, rhs.action).append(data, rhs.data).isEquals();
    }

}
