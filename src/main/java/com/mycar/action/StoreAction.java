package com.mycar.action;

import com.mycar.model.Store;
import com.mycar.service.StoreService;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by qixiang on 8/4/17.
 */

@RestController
@RequestMapping(value="/store")
public class StoreAction {


    private static Logger logger = LoggerFactory.getLogger(StoreAction.class);


    @Resource
    StoreService storeService;

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public HttpResponse getById(@PathVariable("id") long id)
    {
        Store store = storeService.GetStoreById(id);
        if ( store == null ) return new HttpResponse(HttpStatus.NO_STORE);
        else return new HttpResponse(store);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public HttpResponse getAll()
    {
        List<Store> stores = storeService.GetAllStores();
        if ( stores == null || stores.isEmpty() ) return new HttpResponse(HttpStatus.NO_STORE);
        else return new HttpResponse(stores);
    }



}
