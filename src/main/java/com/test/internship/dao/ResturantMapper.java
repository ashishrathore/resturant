package com.test.internship.dao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResturantMapper implements ResultSetMapper<ResturantOrder> {
    private static final String id = "id";
    private static final String customer_name = "costumer_name";
    private static final String item_ordered = "item_ordered";

    public ResturantOrder map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new ResturantOrder(resultSet.getInt(id), resultSet.getString(customer_name), resultSet.getString(item_ordered));
    }
}
