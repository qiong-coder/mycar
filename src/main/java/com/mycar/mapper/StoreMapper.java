package com.mycar.mapper;

import com.mycar.model.Store;

import java.util.List;

/**
 * Created by stupid-coder on 7/30/17.
 */
public interface StoreMapper {

    Store GetStoreById(long id);

    List<Store> GetStores();

}
