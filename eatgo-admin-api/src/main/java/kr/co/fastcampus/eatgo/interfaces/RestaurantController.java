package kr.co.fastcampus.eatgo.interfaces;
import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/restaurants")
    public List<Restaurant> list(){
        return restaurantService.getRestaurants();
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") Long id){

        return restaurantService.getRestaurant(id);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {
        Restaurant restaurant = restaurantService.addRestaurant(Restaurant.builder()
                                .name(resource.getName())
                                .address(resource.getAddress())
                                .menuItems(new ArrayList<>())
                                .build());

        URI location = new URI("/restaurants/" + restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @RequestBody Restaurant restaurant){
        String name = restaurant.getName();
        String address = restaurant.getAddress();
        restaurantService.updateRestaurant(id, name, address);
        return "{}";
    }
}