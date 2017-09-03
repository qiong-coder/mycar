package com.mycar.response;

/**
 * Created by qixiang on 9/3/17.
 */
public class VehicleInfoCount {

    private Long viid;
    private String name;
    private Integer spare;
    private Long count;

    public Long getViid() {
        return viid;
    }

    public void setViid(Long viid) {
        this.viid = viid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpare() {
        return spare;
    }

    public void setSpare(Integer spare) {
        this.spare = spare;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
