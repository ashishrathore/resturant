package com.test.internship.dao;

public class ResturantOrder {

    private int id;
    private String costumer_name;
    private String item_ordered;

    public ResturantOrder(int id, String costumer_name, String item_ordered) {
        super();
        this.id = id;
        this.costumer_name = costumer_name;
        this.item_ordered = item_ordered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCostumer_name() {
        return costumer_name;
    }

    public void setCostumer_name(String costumer_name) {
        this.costumer_name = costumer_name;
    }

    public String getItem_ordered() {
        return item_ordered;
    }

    public void setItem_ordered(String item_ordered) {
        this.item_ordered = item_ordered;
    }


    /* dummy constructor
     otherwise giives error -
     ! com.fasterxml.jackson.databind.JsonMappingException:
     Can not construct instance of com.test.internship.dao.ResturantOrder:
     no suitable constructor found, can not deserialize from Object value
     (missing default constructor or creator, or perhaps need to add/enable type information?)

    */
    public ResturantOrder() {
    }
}