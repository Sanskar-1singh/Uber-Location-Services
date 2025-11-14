package com.example.uberlocationservices.Dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDriverLocationDto {

    public String driverId;
     public Double Latitude;
     public Double Longitude;

}
