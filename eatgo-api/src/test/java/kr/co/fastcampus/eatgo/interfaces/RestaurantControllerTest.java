package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 스프링을 이용해서 테스트를 실행할 수 있게 한다. (CRUD에 관한)
@WebMvcTest(RestaurantController.class) // 무엇에 대한 테스트인지.
public class RestaurantControllerTest {

    @Autowired // 다음에 알아본다고 한다. 일단 이렇게 자동으로 spring에서 사용하도록 한다고 한다.
    private MockMvc mvc;

    @SpyBean(RestaurantService.class)
    private RestaurantService restaurantService;

    @SpyBean(RestaurantRepositoryImpl.class)
    private RestaurantRepositoryImpl restaurantRepository;

    @SpyBean(MenuItemRepositoryImpl.class)
    private MenuItemRepository menuItemRepository;

    @Test
    public void list() throws Exception {
        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")));

    }

    @Test
    public void detail() throws Exception {
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(
                        containsString("Kimchi")
                ));
    }

}