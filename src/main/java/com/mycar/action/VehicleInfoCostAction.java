package com.mycar.action;

import com.mycar.model.VehicleInfoCost;
import com.mycar.service.VehicleInfoCostService;
import com.mycar.utils.HttpResponse;
import com.mycar.utils.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * Created by qixiang on 8/1/17.
 */

@RestController
public class VehicleInfoCostAction {


    private static Logger logger = LoggerFactory.getLogger(VehicleInfoCostAction.class);

    @Resource
    VehicleInfoCostService vehicleInfoCostService;

    @RequestMapping(value = "/vehicle/info/cost/{viid}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse getVehicleInfoCostByViid(@PathVariable("viid") long viid)
    {
        VehicleInfoCost vehicleInfoCost = vehicleInfoCostService.getVehicleInfoCostById(viid);
        if ( vehicleInfoCost == null ) return new HttpResponse(HttpStatus.NO_VEHICLE_COST_INFO);
        return new HttpResponse(vehicleInfoCost);
    }

    @RequestMapping(value = "/vehicle/info/cost/{viid}/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResponse updateVehicleInfoCostByViid(@PathVariable("viid") long viid,
                                                    @RequestBody VehicleInfoCost costInfo)
    {
        if ( costInfo.getDay_costs() != null ) {
            if ( vehicleInfoCostService.updateDayCosts(viid,costInfo) != 0 )
                return new HttpResponse(HttpStatus.OK);
            else return new HttpResponse(HttpStatus.ERROR);
        }
        else if ( costInfo.getBase_insurance() != null && costInfo.getFree_insurance() != null ) {
            if (vehicleInfoCostService.updateInsurance(viid, costInfo.getBase_insurance(), costInfo.getFree_insurance()) != 0)
                return new HttpResponse(HttpStatus.OK);
            else return new HttpResponse(HttpStatus.ERROR);
        } else return new HttpResponse(HttpStatus.ERROR);
    }

}
