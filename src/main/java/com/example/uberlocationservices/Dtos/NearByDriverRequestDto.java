package com.example.uberlocationservices.Dtos;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearByDriverRequestDto {

    Double latitude;
    Double longitude;

}
