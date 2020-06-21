package kr.co.fastcampus.eatgo.domain;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

import javax.annotation.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Transient // 임의로 만들어서, DB처리를 하거나 하지 않을 것이라는 것
    private List<MenuItem> menuItems;

    public String getInformation(){
        return name + " in " + address;
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
