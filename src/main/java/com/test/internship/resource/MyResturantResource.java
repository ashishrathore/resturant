package com.test.internship.resource;


import com.test.internship.dao.Representation;
import com.test.internship.dao.ResturantDao;
import com.test.internship.dao.ResturantOrder;
import org.eclipse.jetty.http.HttpStatus;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.test.internship.Constants.DB_ERROR;
import static com.test.internship.Constants.NOT_FOUND;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)

public abstract class MyResturantResource {

    @CreateSqlObject
    abstract ResturantDao resturantDao();



    @GET
    @Path("/getAllOrders")
    public Representation<List<ResturantOrder>> getAllOrders() {

        List<ResturantOrder> orders = resturantDao().getAllOrders();
        Representation<List<ResturantOrder>> ordersToReturn = new Representation<>(HttpStatus.OK_200, orders);
        return ordersToReturn;

    }



    @GET
    @Path("/getOrder/{id}")
    public Representation<ResturantOrder> getOrder(@PathParam("id") int id) {
        ResturantOrder resturantOrder = resturantDao().getOrder(id);

        Representation<ResturantOrder> ordersToReturn = new Representation<>(HttpStatus.OK_200, resturantOrder);
        return ordersToReturn;
    }



    @POST
    @Path("/putOrder")
    public Representation<ResturantOrder> putOrder(@NotNull ResturantOrder resturantOrder) {

        // first create order - return nothing (void)
        resturantDao().putOrder(resturantOrder);

        ResturantOrder lastPlacedOrder = resturantDao().getOrder(resturantDao().lastInsertId());
        Representation<ResturantOrder> ordersToReturn = new Representation<>(HttpStatus.OK_200, lastPlacedOrder);
        return ordersToReturn;
    }



    @PUT
    @Path("/editOrder/{id}")
    public Representation<ResturantOrder> editOrder(@NotNull ResturantOrder orderRecievedForEditing, @PathParam("id") int id) {

        orderRecievedForEditing.setId(id);

        // first check if order exists
        ResturantOrder orderFetchedFromDB = resturantDao().getOrder(orderRecievedForEditing.getId());

        if (orderFetchedFromDB == null) {
            throw new WebApplicationException(String.format(NOT_FOUND, orderRecievedForEditing.getId()),
                    Response.Status.NOT_FOUND);
        }

        // now that order does exist, edit the same
        resturantDao().editOrder(orderRecievedForEditing);
        ResturantOrder editedSuccessfullyOrder = resturantDao().getOrder(orderRecievedForEditing.getId());

        Representation<ResturantOrder> orderToReturn = new Representation<>(HttpStatus.OK_200, editedSuccessfullyOrder);
        return orderToReturn;
    }

    public String performHealthCheck() {
        try {
            resturantDao().getAllOrders();
        } catch (Exception ex) {
            return DB_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }

}
