package com.example.uberlocationservices.services;


import com.example.uberlocationservices.Dtos.DriverLocationDto;
import lombok.Setter;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService{
    //but how spring will get this object and from where>>>
    private StringRedisTemplate stringRedisTemplate;
    private static final Double SEARCH_RADIUS=5.0;
    private static final String DRIVER_GEO_OPS_KEY="drivers";

    public LocationServiceImpl(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate=stringRedisTemplate;
    }
    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double longitude) {

        try{
            /**
             * to perfom any opertaion based upon geodata we can use below geoOps object that give
             *  us various geo based functionality>>
             */
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();

            geoOps.add(DRIVER_GEO_OPS_KEY, new Point(latitude
                       ,longitude),driverId);
            /**
             * geoOps.add(DRIVER_GEO_OPS_KEY,new RedisGeoCommands.GeoLocation<>(saveDriverLocationDto.getDriverId()
             new Point(saveDriverLocationDto.getLatitude(),saveDriverLocationDto.getLongitude())));
             */
            return true;
        }
        catch (Exception ex){
             return false;
        }
    }

    @Override
    public List<DriverLocationDto> getDriverLocations(Double latitude, Double longitude) {
        try{
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();

            Distance radius=new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
            Circle circle=new Circle(new Point(latitude,longitude),radius);

            GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY, circle);

            List<DriverLocationDto> driverIds = new ArrayList<>();
            for(GeoResult<RedisGeoCommands.GeoLocation<String>> result:results){
                Point point=geoOps.position(DRIVER_GEO_OPS_KEY,result.getContent().getName()).get(0);
                DriverLocationDto driverLocationDto= DriverLocationDto.builder()
                        .driverId(result.getContent().getName())
                        .latitude(point.getX())
                        .longitude(point.getY())
                        .build();

                driverIds.add(driverLocationDto);
            }
            return driverIds;
        }
        catch (Exception ex){
               return new ArrayList<>();
        }
    }
}
