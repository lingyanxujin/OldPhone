package com.tan.oldphone.model;

import java.util.HashMap;

import com.google.gson.Gson;

public class ReturnMsg {

	private int code;
	private String message;
	private HashMap<String, Object> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
