package com.test.internship.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(ResturantMapper.class)
public interface ResturantDao {

    @SqlQuery("select * from orders;")
    public List<ResturantOrder> getAllOrders();

    @SqlQuery("select * from orders where id = :id")
    public ResturantOrder getOrder(@Bind("id") int id);

    @SqlUpdate("insert into orders(costumer_name, item_ordered) values(:costumer_name, :item_ordered)")
    void putOrder(@BindBean ResturantOrder resturantOrder);

    @SqlUpdate("update orders set costumer_name = coalesce(:costumer_name, costumer_name), item_ordered = coalesce(:item_ordered, item_ordered) where id = :id")
    void editOrder(@BindBean ResturantOrder resturantOrder);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}
