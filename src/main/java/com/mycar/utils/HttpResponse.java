package com.mycar.utils;

/**
 * Created by stupid-coder on 7/15/17.
 */
public class HttpResponse {

    private int status;
    private String info;
    private Object data;

    public HttpResponse(Object data)
    {
        if ( data != null ) {
            this.status = HttpStatus.OK;
        } else {
            this.status = HttpStatus.ERROR;
        }
        this.info = HttpStatus.getInfo(this.status);
        this.data = data;
    }

    public HttpResponse(int status)
    {
        this.status = status;
        this.info = HttpStatus.getInfo(status);
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
