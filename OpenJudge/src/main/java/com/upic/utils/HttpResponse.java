package com.upic.utils;

import org.apache.commons.httpclient.Header;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhubuqing on 2018/1/31.
 */
public class HttpResponse {
    private Header[] responseHeaders;

    private String stringResult;

    private byte[] byteResult;

    private String input_charset = "UTF-8";

    public Header[] getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public byte[] getByteResult() {
        if (byteResult != null) {
            return byteResult;
        }
        if (stringResult != null) {
            return stringResult.getBytes();
        }
        return null;
    }

    public void setByteResult(byte[] byteResult) {
        this.byteResult = byteResult;
    }

    public String getStringResult() throws UnsupportedEncodingException {
        if (stringResult != null) {
            return stringResult;
        }
        if (byteResult != null) {
            return new String(byteResult, input_charset);
        }
        return null;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }
}
