package com.tan.oldphone.interf;

public interface GetResultInterface<T> {
	void onSuccessResponse(T response);
	void onErrorResponse(int errorCode);
}
