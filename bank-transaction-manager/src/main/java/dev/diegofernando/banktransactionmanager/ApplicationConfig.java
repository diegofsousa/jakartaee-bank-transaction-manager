package dev.diegofernando.banktransactionmanager;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    public ApplicationConfig(@Context ServletConfig servletConfig){

        BeanConfig beanConfig = new BeanConfig();

        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("BankTransactionManager API");
        beanConfig.setBasePath("/bank-transaction-manager/api/v1");
        beanConfig.setResourcePackage("dev.diegofernando.banktransactionmanager.resources");
        beanConfig.setScan(true);
    }

}