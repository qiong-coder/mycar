package com.mycar.action;

import com.mycar.service.AccountService;
import com.mycar.utils.AccountRoles;
import com.mycar.utils.FileUploadUtils;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * Created by stupid-coder on 10/11/17.
 */

@RestController
@RequestMapping(value = "/files")
public class FileUploadAction {

    @Autowired
    AccountService accountService;

    @Autowired
    FileUploadUtils fileUploadUtils;

    private String tranform(String id)
    {
        String id_prefix = id.substring(0,6);
        String birth_year = id.substring(6,10);
        String birth_month_days = id.substring(10,14);
        return "/"+id_prefix+"/"+birth_year+"/"+birth_month_days+"/"+id.substring(14);
    }

    @RequestMapping(value = "/{type}/{id}/", method = RequestMethod.GET)
    public HttpResponse getIdFile(HttpServletRequest request,
                                  @PathVariable("id") String id,
                                  @PathVariable("type") String type)
    {
        if (  accountService.check(request.getSession(),request.getHeader("token"), AccountRoles.STAFF) != 0 ) return new HttpResponse(HttpStatus.PERMISSION_DENY);
        else {
            String id_filename = tranform(id);
            String full_filename = fileUploadUtils.check("/"+type+id_filename);
            if ( full_filename != null ) return new HttpResponse(full_filename);
            else return new HttpResponse(HttpStatus.ERROR);
        }
    }

    @RequestMapping(value = "/{type}/{id}/", method = RequestMethod.POST)
    public HttpResponse updateIdFile(@RequestPart("attachment") Part attachment,
                                     @PathVariable("id") String id,
                                     @PathVariable("type") String type)
    {
        String id_filename = tranform(id);
        String upload_filename = fileUploadUtils.save("/"+type+id_filename, attachment);
        if (  upload_filename != null ) return new HttpResponse(upload_filename);
        else return new HttpResponse(HttpStatus.ERROR);
    }

}
