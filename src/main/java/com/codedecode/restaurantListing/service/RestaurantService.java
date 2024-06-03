package com.codedecode.restaurantListing.service;

import com.codedecode.restaurantListing.dto.RestaurantDTO;
import com.codedecode.restaurantListing.entity.Restaurant;
import com.codedecode.restaurantListing.mapper.RestaurantMapper;
import com.codedecode.restaurantListing.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    public List<RestaurantDTO> findAllRestaurants() {

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        // map to list of dto
        List<RestaurantDTO> restaurantDTOList = restaurantList.stream().map(RestaurantMapper.INSTANCE::mapRestaurantToRestaurantDTO).collect(Collectors.toList());


        return restaurantDTOList;
    }

    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {

        Restaurant restaurantSaved = restaurantRepository.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));

        return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurantSaved);
    }


    public RestaurantDTO fetchRestaurantById(Integer id) {

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Restaurant of id " + id + " do not exist"));

        return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant);
    }


}
