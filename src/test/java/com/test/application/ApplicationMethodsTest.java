package com.test.application;

import com.test.internship.ResturantApplication;
import com.test.internship.config.ResturantConfiguration;
import com.test.internship.dao.Representation;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

// taken from
// http://www.dropwizard.io/1.0.5/docs/manual/testing.html

public class ApplicationMethodsTest {

    private String JSON_PUT_ORDER = "{\"costumer_name\":\"Chapel\",\"item_ordered\":\"Pepsi\"}";
    private String JSON_EDIT_ORDER = "{\"costumer_name\":\"Chapel\",\"item_ordered\":\"Pepsi and Fries\"}";
    private String EDITED_ORDER_ITEM = "Pepsi and Fries";

    @ClassRule
    public static final DropwizardAppRule<ResturantConfiguration> RULE = new DropwizardAppRule<>(
            ResturantApplication.class,
            "resturant.yml");

    @Test
    public void getAllOrdersTest() {
        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");

        Response response = client.target(
                String.format("http://localhost:%d/getAllOrders", RULE.getLocalPort()))
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(200);
    }


    @Test
    public void addNewOrder() {
        // do something

        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client 2");

        String putOrderUrl = "http://localhost:%d/putOrder";
        Response putUrlResponse = client.target(
                String.format(putOrderUrl, RULE.getLocalPort()))
                .request()
                .post(Entity.json(JSON_PUT_ORDER));

        // Order added?
        assertThat(putUrlResponse.getStatus()).isEqualTo(200);

        //TODO 'data' from Representation class comes in form of LinkedHashMap; need to cehck why
        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) putUrlResponse.readEntity(Representation.class).getData();

        String orderId = String.valueOf(data.get("id"));
        assertThat(orderId).isNotEmpty();

        // seems we have new orderId, lets try editing the order (PUT method)
        String editOrderUrl = "http://localhost:%d/editOrder/" + orderId;
        Response editUrlResponse = client.target(
                String.format(editOrderUrl, RULE.getLocalPort()))
                .request()
                .put(Entity.json(JSON_EDIT_ORDER));

        // Order Edit successfull?
        assertThat(editUrlResponse.getStatus()).isEqualTo(200);

        // get the item_ordered from 'data'
        LinkedHashMap<String, String> data2 = (LinkedHashMap<String, String>) editUrlResponse.readEntity(Representation.class).getData();
        String item_ordered = data2.get("item_ordered");
        assertThat(item_ordered).isNotEmpty();
        assertThat(item_ordered).isEqualToIgnoringCase(EDITED_ORDER_ITEM);

    }


}
