package com.codedecode.restaurantListing.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    // we can make id transient also
    private int id;

    private String name;

    private String address;

    private String city;

    private String restaurantDescription;

}
