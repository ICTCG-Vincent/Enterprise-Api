package com.ictcg.service.route;

import com.ictcg.service.impl.EnterpriseService;
import com.ictcg.service.models.Enterprise;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseRoute extends RouteBuilder {

    @Override
    public void configure() {
        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Enterprise.class);


        rest("/api")
                .get("/enterprises")
                .id("api-enterprises-get")
                .produces("application/json")
                .route()
                .bean(EnterpriseService.class, "findAll")
                .marshal().json()
                .endRest()

                .get("/enterprises/{id}")
                .id("api-enterprises-by-id")
                .produces("application/json")
                .route()
                .bean(EnterpriseService.class, "findBy(${header.id})")
                .marshal().json()
                .endRest()

                .post("/enterprises")
                .id("api-enterprises-add")
                .consumes("application/json")
                .type(Enterprise.class)
                .route()
                .to("bean-validator://x")
                .unmarshal(jsonDataFormat)
                .bean(EnterpriseService.class, "post")
                .marshal().json()
                .endRest()

        ;


    }

}
