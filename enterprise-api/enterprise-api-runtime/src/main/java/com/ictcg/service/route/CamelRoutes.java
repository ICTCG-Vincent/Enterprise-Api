package com.ictcg.service.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class CamelRoutes extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.off)
                .jsonDataFormat("json-jackson")
                .dataFormatProperty("prettyPrint", "true");

        rest("/greetings/")
            .get("/{name}").produces("text/plain")
            .id("route-rest-greetings")
            .route()
                .transform(simple("Bonjour ${header.name}, bienvenue dans l'api enterprise"));
    }

}
