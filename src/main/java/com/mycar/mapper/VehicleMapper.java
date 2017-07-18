package com.mycar.mapper;

import com.mycar.model.Vehicle;

import java.util.List;

/**
 * Created by stupid-coder on 7/15/17.
 */
public interface VehicleMapper {

    Vehicle getById(long id);

    Vehicle getByNumber(String number);

    List<Vehicle> getAll();

}
