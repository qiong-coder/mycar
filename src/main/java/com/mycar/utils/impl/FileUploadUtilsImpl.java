package com.mycar.utils.impl;

import com.mycar.utils.FileUploadUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

/**
 * Created by qixiang on 9/2/17.
 */
@Component("FileUploadUtils")
public class FileUploadUtilsImpl implements FileUploadUtils {

    private String media_path;
    private String code_path;
    private String id_path;

    public void setServletContext(ServletContext servletContext) {
        media_path = servletContext.getRealPath("/")+"../images";
        File file = new File(media_path);
        if ( !file.exists() ) file.mkdirs();

        code_path = media_path+"/codes";
        file = new File(code_path);
        if ( !file.exists() ) file.mkdirs();

        id_path = media_path+"/ids";
        file = new File(id_path);
        if( !file.exists() ) file.mkdirs();
    }

    @Override
    public String getMediaPrefix() {
        return media_path;
    }

    @Override
    public String getCodePrefix() {
        return code_path;
    }

    @Override
    public String getIdPrefix() {
        return id_path;
    }

    private void mkPrefixPath(String filename)
    {
        String prefix_path = filename.substring(0,filename.lastIndexOf('/'));
        File dir = new File(prefix_path);
        if ( !dir.exists() ) dir.mkdirs();
    }

    private String getSuffix(Part attachment) {
        if ( attachment.getSubmittedFileName().lastIndexOf('.') == -1 ) return null;
        else return attachment.getSubmittedFileName().substring(attachment.getSubmittedFileName().lastIndexOf('.'));
    }



    @Override
    public String save(Part attachment) {
        return save("/"+attachment.getSubmittedFileName(),attachment);
    }

    @Override
    public String save(String filename, Part attachment) {
        mkPrefixPath(filename);

        String suffix = getSuffix(attachment);

        if ( suffix == null ) return null;

        if ( attachment.getSize() == 0 ) return null;
        try {
            attachment.write(media_path+filename+suffix);
        } catch ( IOException e ) {
            return null;
        }

        return filename+suffix;
    }

    @Override
    public String check(String id_filename) {

        File id_file = new File(media_path+id_filename+".jpg");

        if ( id_file.exists() ) return id_filename+".jpg";

        id_file = new File(media_path+id_filename+".png");

        if ( id_file.exists() ) return id_filename+".png";

        return null;
    }

    @Override
    public void delete(String filename) {
        File file = new File(filename);
        if ( file.exists() ) file.delete();
    }
}
