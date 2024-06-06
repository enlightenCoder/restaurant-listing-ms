package com.codedecode.restaurantListing.service;

import com.codedecode.restaurantListing.dto.RestaurantDTO;
import com.codedecode.restaurantListing.entity.Restaurant;
import com.codedecode.restaurantListing.repository.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {


    @InjectMocks
    RestaurantService restaurantService;

    @Mock
    RestaurantRepository restaurantRepository;


    @BeforeEach
    public void setUp() {

    }


    @Test
    void testFindAllRestaurants() {
        //AAA
        // Arrange -> create test data
        List<Restaurant> mockRestaurants = Arrays.asList(
                new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Description 1"),
                new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Description 1")
        );
        // Act
        when(restaurantRepository.findAll()).thenReturn(mockRestaurants);

        List<RestaurantDTO> response = restaurantService.findAllRestaurants();

        //Assert
        Assertions.assertEquals(response.size(), mockRestaurants.size());

    }

    @Test
    void testAddRestaurantInDB() {

        // create Mock restaurant
        Restaurant restaurant = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Description 1");
        //Mock the behaviour
        when(restaurantRepository.save(any())).thenReturn(restaurant);

        // call service method
        RestaurantDTO restaurant1 = restaurantService.addRestaurantInDB(new RestaurantDTO());

        // verify the response
        Assertions.assertEquals(restaurant1.getName(), restaurant.getName());

        // verify if service method addRestaurantInDB(any()) was called 1 time by using restaurantService ref
        verify(restaurantRepository, times(1)).save(any());

    }

    @Test
    void testFetchRestaurantById() {

        // Create test data
        Integer mockRestaurantId = 1;

        // create a mock restaurant
        Restaurant restaurant = new Restaurant(1, "Restaurant 1", "Address 1", "city 1", "Description 1");

        // Mock the service behaviour
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));

        // call the controller method
        RestaurantDTO restaurant1 = restaurantService.fetchRestaurantById(mockRestaurantId);

        // Assertion
        // verify the response
        Assertions.assertEquals(restaurant1.getName(), restaurant.getName());

        // verify if service method addRestaurantInDB(any()) was called 1 time by using restaurantService ref
        verify(restaurantRepository, times(1)).findById(any());

    }
}
