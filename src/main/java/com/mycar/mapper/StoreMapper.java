package com.mycar.mapper;

import com.mycar.model.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by stupid-coder on 7/30/17.
 */
public interface StoreMapper {

    Store GetStoreById(int id);

    List<Store> GetAllStores();

    int insertStore(Store store);

    int updateStore(Store store);

    int updateStoreToDelete(@Param("id") Integer id);

}
