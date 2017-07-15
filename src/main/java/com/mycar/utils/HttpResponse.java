package com.mycar.utils;

/**
 * Created by stupid-coder on 7/15/17.
 */
public class HttpResponse {

    private int status;
    private Object data;

    public HttpResponse(Object data)
    {
        if ( data != null ) {
            this.status = 0;
            this.data = data;
        } else {
            this.status = -1;
            this.data = data;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
