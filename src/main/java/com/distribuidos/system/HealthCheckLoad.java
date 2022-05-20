package com.distribuidos.system;

import com.distribuidos.model.HealthCheck;

public class HealthCheckLoad {

    public static void main(String[] args) {

        HealthCheck healthCheck = new HealthCheck();
        healthCheck.checkMonitors();
    }
    
}
