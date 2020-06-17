package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RestaurantRepositoryImplTest {

    @Test
    public void save(){
        RestaurantRepository respository = new RestaurantRepositoryImpl();

        int oldCount = respository.findAll().size();

        Restaurant restaurant = new Restaurant("BeRyong", "Seoul");
        respository.save(restaurant);

        assertThat(restaurant.getId(), is(1234L));

        int newCount = respository.findAll().size();

        assertThat(newCount - oldCount, is(1));
    }
}