package com.tan.oldphone.common;

import com.google.gson.Gson;
import com.tan.oldphone.model.ReturnMsg;

public class GsonUtils {

	public static ReturnMsg getReturnMsg(String respone) {
		return new Gson().fromJson(respone, ReturnMsg.class);
	}
}
