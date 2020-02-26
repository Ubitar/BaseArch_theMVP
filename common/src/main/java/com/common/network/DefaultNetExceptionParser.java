package com.common.network;

import android.net.ParseException;

import com.alibaba.fastjson.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class DefaultNetExceptionParser {

    public static final int UNKNOWN = 1000;
    public static final int SUCCESS = 200;
    public static final int PARSE_ERROR = 1001;
    public static final int NETWORK_ERROR = 1002;
    public static final int RESULT_EMPTY = 1003;

    public static ApiException parse(Throwable e) {
        if (e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            return new ApiException(PARSE_ERROR, e.getMessage(), "数据解析错误");
        } else if (e instanceof ConnectException) {
            //网络错误
            return new ApiException(NETWORK_ERROR, e.getMessage(), "无法连接至服务器");
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            return new ApiException(NETWORK_ERROR, e.getMessage(), "无法连接至服务器");
        } else if (e instanceof ApiException) {
            return (ApiException) e;
        } else {
            //未知错误
            return  new ApiException(UNKNOWN, e.getMessage(), "未知错误");
        }
    }
}
