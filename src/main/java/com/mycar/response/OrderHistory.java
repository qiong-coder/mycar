package com.mycar.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stupid-coder on 9/1/17.
 */
public class OrderHistory {

    public class OrderHistoryItem {
        private String name;
        private String number;
        private String oid;
        private Integer ret_day;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public Integer getRet_day() {
            return ret_day;
        }

        public void setRet_day(Integer ret_day) {
            this.ret_day = ret_day;
        }
    }

    private Integer ret_day_total;
    private Integer idle_day;
    private List<OrderHistoryItem> history;

    public OrderHistory() {
        this.ret_day_total = 0;
        idle_day = 0;
        history = new ArrayList<>();
    }

    public Integer getRet_day_total() {
        return ret_day_total;
    }

    public void setRet_day_total(Integer ret_day_total) {
        this.ret_day_total = ret_day_total;
    }

    public Integer getIdle_day() {
        return idle_day;
    }

    public void setIdle_day(Integer idle_day) {
        this.idle_day = idle_day;
    }

    public List<OrderHistoryItem> getHistory() {
        return history;
    }

    public void setHistory(List<OrderHistoryItem> history) {
        this.history = history;
    }
}
