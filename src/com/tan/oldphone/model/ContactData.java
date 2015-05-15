package com.tan.oldphone.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class ContactData {

    @Expose
    private long pageNo;
    @Expose
    private long totalPage;
    @Expose
    private long pageSize;
    @Expose
    private List<ContactContent> content = new ArrayList<ContactContent>();

    /**
     * 
     * @return
     *     The pageNo
     */
    public long getPageNo() {
        return pageNo;
    }

    /**
     * 
     * @param pageNo
     *     The pageNo
     */
    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public ContactData withPageNo(long pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    /**
     * 
     * @return
     *     The totalPage
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * 
     * @param totalPage
     *     The totalPage
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public ContactData withTotalPage(long totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    /**
     * 
     * @return
     *     The pageSize
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * 
     * @param pageSize
     *     The pageSize
     */
    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public ContactData withPageSize(long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 
     * @return
     *     The content
     */
    public List<ContactContent> getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(List<ContactContent> content) {
        this.content = content;
    }

    public ContactData withContent(List<ContactContent> content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
