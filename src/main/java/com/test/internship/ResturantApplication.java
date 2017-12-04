package com.test.internship;

import com.test.internship.config.ApplicationHealthCheck;
import com.test.internship.config.ResturantConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import com.test.internship.resource.MyResturantResource;

import javax.sql.DataSource;

public class ResturantApplication extends Application<ResturantConfiguration> {


    public static void main(String[] args) throws Exception {
        new ResturantApplication().run(args);
    }

    @Override
    public void run(ResturantConfiguration configuration, Environment environment) {

        // Datasource configuration
        final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), Constants.SQL);
        DBI dbi = new DBI(dataSource);

        // Register Health Check
        ApplicationHealthCheck healthCheck = new ApplicationHealthCheck(dbi.onDemand(MyResturantResource.class));
        environment.healthChecks().register(Constants.SERVICE, healthCheck);

        // register my com.test.internship.resource
        environment.jersey().register((dbi.onDemand(MyResturantResource.class)));


    }
}
