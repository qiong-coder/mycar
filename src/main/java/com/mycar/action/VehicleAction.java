package com.mycar.action;


import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.service.VehicleService;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import com.mycar.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by stupid-coder on 7/15/17.
 */

@RestController
public class VehicleAction {

    private static Logger logger = LoggerFactory.getLogger(VehicleAction.class);

    @Resource
    private VehicleService vehicleService;

    @Resource
    private VehicleInfoCostService vehicleInfoCostService;

    @RequestMapping(value = "/vehicles/{status}/{sid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getVehiclesByStatus(@PathVariable("status") int status,
                                            @PathVariable("sid") long sid)
    {
        List<Vehicle> vehicles = vehicleService.getAllVehiclesByStatusAndSid(status,sid);
        if ( vehicles == null || vehicles.isEmpty() ) return new HttpResponse(HttpStatus.NO_VEHICLE);
        else return new HttpResponse(vehicles);
    }

    @RequestMapping(value = "/vehicle/{vid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getVehicleById(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @PathVariable("vid") long vid)
    {
        Vehicle vehicle = vehicleService.getVehicleById(vid);
        if ( vehicle == null ) {
            return new HttpResponse(HttpStatus.NO_VEHICLE);
        } else return new HttpResponse(vehicle);
    }


//    @RequestMapping(value = "/vehicle/info/{viid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public HttpResponse getInfoById(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    @PathVariable("viid") long id)
//    {
//        VehicleInfo info = vehicleService.getVehicleInfoById(id);
//        if ( info == null )
//        {
//            logger.warn("failure to get the vehicle info - {}", id);
//            return new HttpResponse(HttpStatus.NO_VEHICLE_INFO);
//        } else return new HttpResponse(info);
//    }

    @RequestMapping(value = "/vehicle/info/{begin}/{end}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getAllInfos(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable("begin") long begin_s,
                                    @PathVariable("end") long end_s)
    {
        Timestamp begin = new Timestamp(begin_s * TimeUtils.MILLIS_PER_SECOND);
        Timestamp end = new Timestamp(end_s * TimeUtils.MILLIS_PER_SECOND);

        List<VehicleInfo> vehicleInfos = vehicleService.getVehicleInfosByTime(begin,end);
        if (vehicleInfos == null ) return new HttpResponse(HttpStatus.NO_VEHICLE_INFO);

        return new HttpResponse(vehicleInfos);
    }

    @RequestMapping(value = "/vehicle/info/{viid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getInfoByIdAndCost(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @PathVariable("viid") long id)
    {
        VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(id);

        if ( vehicleInfo == null )
        {
            logger.warn("failure to get the vehicle info - id:{} begin:{} end:{}",id);
            return new HttpResponse(HttpStatus.NO_VEHICLE_INFO);
        }

        vehicleInfo.setCost(vehicleInfoCostService.getVehicleInfoCostById(vehicleInfo.getId()));
        return new HttpResponse(vehicleInfo);
    }
}
