package com.example.uberlocationservices.controllers;


import com.example.uberlocationservices.Dtos.DriverLocationDto;
import com.example.uberlocationservices.Dtos.NearByDriverRequestDto;
import com.example.uberlocationservices.Dtos.SaveDriverLocationDto;
import com.example.uberlocationservices.services.LocationService;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class RedisController {

    private LocationService locationService;

    public RedisController(LocationService locationService) {
        this.locationService = locationService;
    }


    @PostMapping("/drivers")
    public ResponseEntity<?> saveDriverLocation(@RequestBody SaveDriverLocationDto saveDriverLocationDto) {

        try{
            Boolean response=locationService.saveDriverLocation(saveDriverLocationDto.getDriverId()
                    ,saveDriverLocationDto.getLatitude(),saveDriverLocationDto.getLongitude());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<List<DriverLocationDto>> getNearByDrivers(@RequestBody NearByDriverRequestDto nearByDriverRequestDto) {
        try{
           List<DriverLocationDto> response=locationService.getDriverLocations(nearByDriverRequestDto.getLatitude()
                   ,nearByDriverRequestDto.getLongitude());
           return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
              return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
