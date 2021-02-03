package com.ictcg.service.route;

import com.ictcg.service.impl.ContactService;
import com.ictcg.service.models.Contact;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class ContactRoute extends RouteBuilder {

    @Override
    public void configure() {
      JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Contact.class);

      rest("/api")
            .get("/contacts")
            .id("api-contacts-get")
            .produces("application/json")
             .route()
            .bean(ContactService.class, "findAll")
              .marshal().json()
            .endRest()

            .get("/contacts/{id}")
            .id("api-contacts-by-id")
            .produces("application/json")
            .route()
            .bean(ContactService.class, "findBy(${header.id})")
            .marshal().json()
            .endRest()

            .post("/contacts")
            .id("api-contacts-add")
            .consumes("application/json")
            .type(Contact.class)
            .route()
            .to("bean-validator://x")
            .unmarshal(jsonDataFormat)
            .bean(ContactService.class, "post")
            .marshal().json()
            .endRest()

            .delete("/contacts/{id}")
            .id("api-contacts-delete")
            .consumes("application/json")
            .type(Contact.class)
            .route()
            .bean(ContactService.class, "delete(${header.id})")
            .marshal().json()
            .endRest()
        ;


    }

}
