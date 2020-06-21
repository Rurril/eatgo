package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    List<Restaurant> findAll();

    Optional<Restaurant> findById(Long id); // Optiional은 널 값이 들어왔을때 문제점을 해결하게 도와준다.

    Restaurant save(Restaurant restaurant);

}
