package com.example.uberlocationservices.controllers;


import com.example.uberlocationservices.Dtos.NearByDriverRequestDto;
import com.example.uberlocationservices.Dtos.SaveDriverLocationDto;
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

    //but how spring will get this object and from where>>>
    private StringRedisTemplate stringRedisTemplate;
      private static final Double SEARCH_RADIUS=5.0;
    private static final String DRIVER_GEO_OPS_KEY="drivers";

    public RedisController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/drivers")
    public ResponseEntity<?> saveDriverLocation(@RequestBody SaveDriverLocationDto saveDriverLocationDto) {

        try{
            //to perfom any opertaion based upon geodata we can use below geoOps object that give
            // us various geo based functionality>>
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();

            geoOps.add(DRIVER_GEO_OPS_KEY, new Point(saveDriverLocationDto.getLatitude()
                    ,saveDriverLocationDto.getLongitude()),saveDriverLocationDto.getDriverId());

//        geoOps.add(DRIVER_GEO_OPS_KEY,new RedisGeoCommands.GeoLocation<>(saveDriverLocationDto.getDriverId()
//                ,new Point(saveDriverLocationDto.getLatitude(),saveDriverLocationDto.getLongitude())));

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<List<String>> getNearByDrivers(@RequestBody NearByDriverRequestDto nearByDriverRequestDto) {
        try{
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();

            Distance radius=new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
            Circle circle=new Circle(new Point(nearByDriverRequestDto.getLatitude()
                    ,nearByDriverRequestDto.getLongitude()),radius);

            GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY, circle);

            List<String> driverIds = new ArrayList<>();
            for(GeoResult<RedisGeoCommands.GeoLocation<String>> result:results){
                driverIds.add(result.getContent().getName());
            }

            return new ResponseEntity<>(driverIds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
