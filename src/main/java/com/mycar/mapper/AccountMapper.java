package com.mycar.mapper;

import com.mycar.model.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by qixiang on 8/1/17.
 */
public interface AccountMapper {

    int insert(Account account);

    Account get(@Param("username") String username);

    List<Account> list();

    int update(Account account);

    int delete(@Param("username") String username);
}
