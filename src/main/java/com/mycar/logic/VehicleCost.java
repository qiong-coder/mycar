package com.mycar.logic;


/**
 * Created by stupid-coder on 7/16/17.
 */
public class VehicleCost {

    public static Double GetTotalCost(Double day_cost, Double base_insurance, Double free_insurance, Long days)
    {
        return (day_cost+base_insurance+free_insurance) * days;
    }

}
