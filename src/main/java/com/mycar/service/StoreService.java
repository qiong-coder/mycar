package com.mycar.service;

import com.mycar.model.Store;

import java.util.List;

/**
 * Created by qixiang on 8/4/17.
 */
public interface StoreService {

    Store GetStoreById(int id);

    List<Store> GetAllStores();

    int insertStore(Store store);

    int updateStore(int id, Store store);

    int updateStoreToDelete(int id);

}
