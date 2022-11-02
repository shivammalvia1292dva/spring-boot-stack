package com.in28minutes.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class APIGatewayConfiguration {
    @Bean
    public RouteLocator gatewayRoute(RouteLocatorBuilder builder) {
        Function<PredicateSpec, Buildable<Route>> routeFunction = p -> p.path("/get")
                .filters(f -> f.addRequestHeader("MyHeader", "MyURI")
                        .addRequestParameter("Param", "MyValue")).uri("http://httpbin.org:80");
        return builder.routes()
                .route(routeFunction)
                //the below route will look for urls starting with currency exchange and redirect it eureka
                //where it will find out urls starting with currency-exchange and load balance between those urls because
                //we have mentioned lb
                .route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
                //Below is used to rewite the path or change the rewritten path to actual path
                .route(p -> p.path("/currency-conversion-new/**").filters(f -> f.rewritePath("/currency-conversion-new/", "/currency-conversion-feign/")).uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-old/**").filters(f -> f.rewritePath("/currency-conversion-old/", "/currency-conversion/")).uri("lb://currency-conversion"))
                .build();
    }
}
