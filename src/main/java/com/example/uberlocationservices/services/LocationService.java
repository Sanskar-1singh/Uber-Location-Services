package com.example.uberlocationservices.services;

import com.example.uberlocationservices.Dtos.DriverLocationDto;

import java.util.List;

public interface LocationService {

    Boolean saveDriverLocation(String driverId,Double latitude,Double longitude);

    List<DriverLocationDto> getDriverLocations(Double latitude, Double longitude);
}
