package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.interfaces.RestaurantController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class) // 스프링을 이용해서 테스트를 실행할 수 있게 한다. (CRUD에 관한)
@WebMvcTest(RestaurantController.class) // 무엇에 대한 테스트인지.
public class RestaurantControllerTest {

    @Autowired // 다음에 알아본다고 한다. 일단 이렇게 자동으로 spring에서 사용하도록 한다고 한다.
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .name("Joker house")
                .address("Seoul")
                .build());

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"Joker house\"")));
    }
    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Joker house")
                .address("Seoul")
                .build();

        restaurant.setMenuItems(Arrays.asList(MenuItem.builder()
                .name("Kimchi")
                .build()));

        Review review = Review.builder()
                .name("kimchi")
                .score(5)
                .description("Great!")
                .build();

        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);

        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"Joker house\"")))
                .andExpect(content().string(
                        containsString("Kimchi")))
                .andExpect(content().string(
                        containsString("Great!")));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

}