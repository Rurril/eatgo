package kr.co.fastcampus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
    List<MenuItem> findAllByRestaurantId(Long restaurantId);

    @Override
    default Iterable<MenuItem> findAllById(Iterable<Long> longs) {
        return null;
    }

    void deleteById(Long id);
}
