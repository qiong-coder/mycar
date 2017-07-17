package com.mycar.utils;

/**
 * Created by stupid-coder on 7/17/17.
 */
public enum OrderStatus {

    UNPAID(0), PENDING(1),RENTING(2),DRAWBACK(3),FINISHED(4),CANCLED(5);

    private int status;

    OrderStatus(int status) { this.status = status; }

    public int getStatus() { return status; }

    private void setStatus(int status) { this.status = status; }
}
