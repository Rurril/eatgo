package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock // 가짜 객체로 만들어도 상관 없는 것들이므로
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;


    @Before // 모든 테스트를 실행하기전에 setUp을 시켜주는 것이므로, Before 주석을 사용하는 것.
    public void setUp(){
        MockitoAnnotations.initMocks(this); // 위에 선언한 Mock객체들을 말해주는 것
        // Spring Annotation을 선언 안했으므로, 자동으로 해주지 않기 때문

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
        
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);

    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList();
        menuItems.add(MenuItem.builder()
            .name("Kimchi")
            .build());
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
        given(restaurantRepository.save(any())).willReturn(restaurant);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList();
        reviews.add(Review.builder()
                .name("BeRyong")
                .score(1)
                .description("Bad")
                .build());

        given(reviewRepository.findAllByRestaurantId(1004L))
                .willReturn(reviews);
    }

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        assertThat(restaurants.get(0).getId(), is(1004L));
    }

    @Test
    public void getRestaurantWithExisted(){
        Restaurant restaurant = restaurantService.getRestaurant(1004L);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Kimchi"));

        Review review = restaurant.getReviews().get(0);
        assertThat(review.getDescription(), is("Bad"));

    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantWithNotExisted(){
        restaurantService.getRestaurant(404L);
    }

    @Test
    public void addRestaurant(){

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();
        Restaurant saved = Restaurant.builder()
                .id(1234L)
                .name("BeRyong")
                .address("Busan")
                .build();



        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));
    }

    @Test
    public void updateRestaurant(){

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "Sool zip", "Busan");

        assertThat(restaurant.getName(), is("Sool zip"));
    }
}