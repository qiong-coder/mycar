package com.mycar.service.impl;

import com.mycar.mapper.StoreMapper;
import com.mycar.model.Store;
import com.mycar.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by qixiang on 8/4/17.
 */
public class StoreServiceImpl implements StoreService {

    private static Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    StoreMapper storeMapper;

    @Override
    public Store GetStoreById(long id) {
        Store store = storeMapper.GetStoreById(id);
        if ( store == null ) logger.warn("failure to get the store by id - {}", id);
        return store;
    }

    @Override
    public List<Store> GetAllStores() {
        List<Store> stores = storeMapper.GetAllStores();
        if ( stores == null || stores.isEmpty() ) logger.warn("failure to get the all stores");
        return stores;
    }
}
