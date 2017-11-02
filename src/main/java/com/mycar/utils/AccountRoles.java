package com.mycar.utils;

/**
 * Created by stupid-coder on 11/2/17.
 */
public class AccountRoles {

    public static final Integer ADMINISTRATOR = 1;
    public static final Integer OPERATOR = 2;
    public static final Integer STAFF = ADMINISTRATOR | OPERATOR;
    public static final Integer USER = 4;

}
