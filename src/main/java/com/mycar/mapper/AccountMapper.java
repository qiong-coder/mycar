package com.mycar.mapper;

import com.mycar.model.Account;
import org.apache.ibatis.annotations.Param;

/**
 * Created by qixiang on 8/1/17.
 */
public interface AccountMapper {

    int insert(@Param("name") String name, @Param("password") String password);

    Account get(@Param("name") String name);

    int update(@Param("name") String name, @Param("password") String password);

    int delete(@Param("name") String name);
}
