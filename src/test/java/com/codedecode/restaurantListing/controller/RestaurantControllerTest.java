package com.codedecode.restaurantListing.controller;

import com.codedecode.restaurantListing.dto.RestaurantDTO;
import com.codedecode.restaurantListing.service.RestaurantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class RestaurantControllerTest {

    @InjectMocks
    RestaurantController restaurantController;

    @Mock
    RestaurantService restaurantService;


    @BeforeEach
    public void setUp(){

       MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testFetchAllRestaurants(){
        //AAA
        // Arrange -> create test data
        List<RestaurantDTO> mockRestaurants = Arrays.asList(
                new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Description 1"),
                new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Description 1")
        );
        // Act
        when(restaurantService.findAllRestaurants()).thenReturn(mockRestaurants);
        List<RestaurantDTO> response = restaurantController.fetchAllRestaurants().getBody();

        //Assert
        Assertions.assertTrue(response.size() == mockRestaurants.size());

    }

    @Test
    public void testSaveRestaurant(){
        // create Mock restaurant
        RestaurantDTO restaurantDTO = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Description 1");
        //Mock the behaviour
        when(restaurantService.addRestaurantInDB(any())).thenReturn(restaurantDTO);
        // call controller method
        ResponseEntity<RestaurantDTO> restaurantDTOResponseEntity = restaurantController.saveRestaurant(restaurantDTO);

        // verify the response
        Assertions.assertEquals(HttpStatus.CREATED, restaurantDTOResponseEntity.getStatusCode());
        Assertions.assertEquals(restaurantDTO, restaurantDTOResponseEntity.getBody());

        // verify if service method addRestaurantInDB(any()) was called 1 time by using restaurantService ref
        verify(restaurantService, times(1)).addRestaurantInDB(any());
    }

    @Test
    public void testFetchRestaurantById(){
        // Create test data
        Integer mockRestaurantId = 1;

        // create a mock restaurant
        RestaurantDTO restaurantDTO = new RestaurantDTO(1, "Restaurant 1", "Address 1", "city 1", "Description 1");

        // Mock the service behaviour
        when(restaurantService.fetchRestaurantById(any())).thenReturn(restaurantDTO);

        // call the controller method
        ResponseEntity<RestaurantDTO> restaurantById = restaurantController.findRestaurantById(mockRestaurantId);

        // Assertion
        Assertions.assertEquals(HttpStatus.OK, restaurantById.getStatusCode());
        Assertions.assertEquals(restaurantDTO, restaurantById.getBody());

        // verify if method has been hit by service ref
        verify(restaurantService, times(1)).fetchRestaurantById(any());
    }
}
