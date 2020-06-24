package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {


    private MenuItemRepository menuItemsRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemsRepository) {
        this.menuItemsRepository = menuItemsRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems){
            update(restaurantId, menuItem);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if(menuItem.isDestroy()){
            menuItemsRepository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurantId);
        menuItemsRepository.save(menuItem);
    }
}
