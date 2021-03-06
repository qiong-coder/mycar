package com.mycar.action;


import com.mycar.model.Vehicle;
import com.mycar.model.VehicleInfo;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.service.VehicleService;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.sql.Timestamp;
import java.util.List;

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

    @RequestMapping(value = "/vehicles/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public HttpResponse getVehiclesByStatus()
    {
        List<Vehicle> vehicles = vehicleService.getAllVehicles(0);
        if ( vehicles == null || vehicles.isEmpty() ) return new HttpResponse(HttpStatus.NO_VEHICLE);
        else return new HttpResponse(vehicles);
    }

    @RequestMapping(value = "/vehicles/{viid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public HttpResponse getVehiclesByStatus(@PathVariable("viid") long viid)
    {
        List<Vehicle> vehicles = vehicleService.getAllVehiclesByViid(viid,0);
        if ( vehicles == null || vehicles.isEmpty() ) return new HttpResponse(HttpStatus.NO_VEHICLE);
        else return new HttpResponse(vehicles);
    }

    @RequestMapping(value = "/vehicles/{viid}/{status}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public HttpResponse getVehiclesByStatus(@PathVariable("viid") long viid,
                                            @PathVariable("status") int status)
    {
        List<Vehicle> vehicles = vehicleService.getAllVehiclesByViidAndStatusAndSid(viid,status,0);
        if ( vehicles == null || vehicles.isEmpty() ) return new HttpResponse(HttpStatus.NO_VEHICLE);
        else return new HttpResponse(vehicles);
    }

    @RequestMapping(value = "/vehicle/{vid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @RequestMapping(value = "/vehicle/{viid}/", method = RequestMethod.POST)
    public HttpResponse addVehicle(@PathVariable("viid") long viid,
                                   @RequestBody Vehicle vehicle)
    {
        vehicle.setViid(viid);
        int id = vehicleService.insertVechile(vehicle);
        if ( id != 1 ) return new HttpResponse(HttpStatus.DUPLICATE_VEHICLE);
        else return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/vehicle/{vid}/", method = RequestMethod.PUT )
    public HttpResponse updateVehicleByVid(@PathVariable("vid") long vid,
                                           @RequestParam("description") String description)
    {
        int id = vehicleService.updateVehicleDescription(vid, description);
        if ( id != 1 ) return new HttpResponse(HttpStatus.NO_VEHICLE);
        else return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/vehicle/{vid}/", method = RequestMethod.DELETE )
    public HttpResponse deleteVehicleById(@PathVariable("vid") long vid)
    {
        int count = vehicleService.updateVehicleToDelete(vid);
        if ( count != 1 ) return new HttpResponse(HttpStatus.NO_VEHICLE);
        else return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/vehicle/info/", method = RequestMethod.GET)
    public HttpResponse getAllInfos(HttpServletRequest request,
                                    HttpServletResponse response)
    {
        List<VehicleInfo> info = vehicleService.getAllVehicleInfos(0);
        if ( info == null )
        {
            logger.warn("failure to get the all vehicle info");
            return new HttpResponse(HttpStatus.NO_VEHICLE_INFO);
        } else return new HttpResponse(info);
    }

    @RequestMapping(value = "/vehicle/info/", method = RequestMethod.POST)
    public HttpResponse insertVehicleInfo(VehicleInfo vehicleInfo,
                                          @RequestPart("attachment") Part attachment) {
        if ( vehicleInfo.getSpare() == null ) vehicleInfo.setSpare(0);
        int count = vehicleService.insertVehicleInfo(vehicleInfo, attachment);
        if ( count == 0 ) return new HttpResponse(HttpStatus.ERROR);
        return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/vehicle/info/{viid}/", method = RequestMethod.POST)
    public HttpResponse updateVehicleInfo(@RequestPart("attachment") Part attachment,
                                          @PathVariable("viid") long viid,
                                          VehicleInfo vehicleInfo) {
        int count = vehicleService.updateVehicleInfo(viid,vehicleInfo,attachment);
        if (count != 1) return new HttpResponse(HttpStatus.NO_VEHICLE);
        return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/vehicle/info/{viid}/", method = RequestMethod.DELETE)
    public HttpResponse deleteVehicleInfo(@PathVariable("viid") long viid) {
        int count = vehicleService.updateVehicleInfoToDelete(viid);
        if ( count != 1 ) return new HttpResponse(HttpStatus.NO_VEHICLE_INFO);
        return new HttpResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/vehicle/{viid}/{begin}/{end}/", method = RequestMethod.GET)
    public HttpResponse getVehicleWithTime(@PathVariable Long viid,
                                           @PathVariable Long begin,
                                           @PathVariable Long end) {
        Timestamp begin_stamp = new Timestamp(begin);
        Timestamp end_stamp = new Timestamp(end);

        List<Vehicle> vehicles = vehicleService.getVehicleByTime(viid,begin_stamp, end_stamp);
        if ( vehicles == null ) return new HttpResponse(HttpStatus.NO_VEHICLE);
        return new HttpResponse(vehicles);
    }

    @RequestMapping(value = "/vehicle/info/{begin}/{end}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpResponse getAllInfosWithTime(@PathVariable Long begin,
                                            @PathVariable Long end)
    {
        Timestamp begin_stamp = new Timestamp(begin);
        Timestamp end_stamp = new Timestamp(end);

        List<VehicleInfo> vehicleInfos = vehicleService.getVehicleInfosByTime(begin_stamp,end_stamp);
        if (vehicleInfos == null ) return new HttpResponse(HttpStatus.NO_VEHICLE_INFO);

        return new HttpResponse(vehicleInfos);
    }

    @RequestMapping(value = "/vehicle/info/{viid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpResponse getInfoByIdAndCost(@PathVariable Long viid)
    {
        VehicleInfo vehicleInfo = vehicleService.getVehicleInfoById(viid);

        if ( vehicleInfo == null )
        {
            logger.warn("failure to get the vehicle info - id:{} begin:{} end:{}",viid);
            return new HttpResponse(HttpStatus.NO_VEHICLE_INFO);
        }

        vehicleInfo.setCost(vehicleInfoCostService.getVehicleInfoCostById(vehicleInfo.getId()));
        return new HttpResponse(vehicleInfo);
    }
}
