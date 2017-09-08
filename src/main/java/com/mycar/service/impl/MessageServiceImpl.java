package com.mycar.service.impl;

import com.mycar.service.MessageService;
import com.mycar.utils.CacheUtils;
import com.mycar.utils.FileUploadUtils;
import com.mycar.utils.ImageVerificationUtil;
import com.mycar.utils.TelVerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * Created by qixiang on 7/18/17.
 */
public class MessageServiceImpl implements MessageService {


    private static final String CODE_PREFIX="mycar-code-";
    private static final String TIMEOUT_PREFIX="mycar-code-timeout-";
    private static final int CODE_TIMEOUT = 60*15;
    private static final int TIMEOUT_TIMEOUT = 60;
    private static final String PICTURE_PREFIX="mycar-picture-";
    //private static final String PICTURE_TIMEOUT_PREFIX="mycar-picture-timeout-";
    private static final int PICTURE_TIMEOUT = 60*10;
    private static final int w = 90;
    private static final int h = 30;


    @Autowired
    CacheUtils cacheUtils;

    @Autowired
    FileUploadUtils fileUploadUtils;

    @Override
    public String getCode(String phone) {
        //String code = TelVerificationUtil.SendCode(phone);
        String code="4444";
        if ( code != null ) {
            cacheUtils.delete(CODE_PREFIX+phone);
            cacheUtils.delete(TIMEOUT_PREFIX+phone);
            cacheUtils.put(CODE_PREFIX+phone, code, CODE_TIMEOUT);
            cacheUtils.put(TIMEOUT_PREFIX+phone,"t",TIMEOUT_TIMEOUT);
        }
        return code;
    }

    @Override
    public boolean checkTimeout(String phone) {
        return cacheUtils.get(TIMEOUT_PREFIX+phone) == null;
    }

    @Override
    public boolean checkCode(String phone, String code) {
        String c = cacheUtils.get(CODE_PREFIX+phone);
        if ( c == null ) return false;
        else return code.compareTo(c) == 0;
    }

    @Override
    public String getPicture() {
        String filename = Long.toString(System.currentTimeMillis());
        try {
            String code = ImageVerificationUtil.outputVerifyImage(w, h, new File(fileUploadUtils.getCodePrefix()+"/"+filename+".jpg"), 4);
            if ( code != null ) cacheUtils.put(PICTURE_PREFIX+filename,code,PICTURE_TIMEOUT);
            return filename;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public int checkPicture(String picture, String code) {
        String c = cacheUtils.get(PICTURE_PREFIX+picture);
        if ( c == null ) return -2;
        if ( code.compareToIgnoreCase(c) == 0 ) {
            cacheUtils.delete(PICTURE_PREFIX+picture);
            fileUploadUtils.delete(fileUploadUtils.getCodePrefix()+"/"+picture+".jpg");
            return 0;
        } return -1;
    }
}
