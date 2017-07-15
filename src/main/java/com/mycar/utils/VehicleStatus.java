package com.mycar.utils;

/**
 * Created by stupid-coder on 7/15/17.
 */
public enum VehicleStatus {

    OK(0), FIXING(1), RENTING(2), VALIDATE(3);

    private int status;

    private VehicleStatus(int status) {
        this.status = status;
    }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

}
