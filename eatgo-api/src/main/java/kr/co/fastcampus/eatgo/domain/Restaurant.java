package kr.co.fastcampus.eatgo.domain;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {


    private final String name;
    private final String address;
    private final long id;
    private List<MenuItem> menuItems = new ArrayList<>();

    public Restaurant(long id, String name, String address) {
        this.address = address;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation(){
        return name + " in " + address;
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem){
        menuItems.add(menuItem);
    }

    public long getId() {
        return this.id;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems){
            addMenuItem(menuItem);
        }
    }
}
