package com.mycar.utils;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.http.Part;

/**
 * Created by qixiang on 9/2/17.
 */
public interface FileUploadUtils extends ServletContextAware {

    String save(Part attachment);

    String save(String filename, Part attachment);

    String check(String filename);

    void delete(String filename);

    String getMediaPrefix();

    String getCodePrefix();

    String getIdPrefix();
}
