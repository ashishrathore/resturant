package com.test.internship.config;

import com.codahale.metrics.health.HealthCheck;
import com.test.internship.resource.MyResturantResource;

public class ApplicationHealthCheck extends HealthCheck {
    private static final String HEALTHY = "Healthy for read and write";
    private static final String UNHEALTHY = "Not healthy";

    private final MyResturantResource serviceCheck;

    public ApplicationHealthCheck(MyResturantResource rest) {
        this.serviceCheck = rest;
    }

    @Override
    public Result check() throws Exception {
        String healthStatus = serviceCheck.performHealthCheck();

        if (healthStatus == null) {
            return Result.healthy(HEALTHY);
        } else {
            return Result.unhealthy(UNHEALTHY , healthStatus);
        }
    }
}
