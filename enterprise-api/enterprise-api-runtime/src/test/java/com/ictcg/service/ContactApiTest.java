package com.ictcg.service;

import com.ictcg.service.models.Contact;
import com.ictcg.service.types.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnAContact() {
        ResponseEntity<Contact> response = restTemplate
                .getForEntity("/api/contacts/1", Contact.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Contact contact = response.getBody();

        assertThat(contact)
                .hasFieldOrPropertyWithValue("name", "Book")
                .hasFieldOrPropertyWithValue("status", Status.Freelance)
                .hasFieldOrPropertyWithValue("tva", "BE0723600303");
    }

    @Test
    public void shouldReturnListOfContact() {
        ResponseEntity<Contact[]> response = restTemplate
                .getForEntity("/api/contacts", Contact[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Contact> contacts = Arrays.asList(response.getBody());

        assertThat(contacts).hasSize(4);
        assertThat(contacts).element(0)
                .hasFieldOrPropertyWithValue("name", "Book")
                .hasFieldOrPropertyWithValue("status", Status.Freelance);
        assertThat(contacts).element(1)
                .hasFieldOrPropertyWithValue("name", "Watch")
                .hasFieldOrPropertyWithValue("status", Status.Employee);
    }

    @Test
    public void shouldChangeNameOfContact() {
        ResponseEntity<Contact> response = restTemplate
                .getForEntity("/api/contacts/1", Contact.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Contact contact = response.getBody();

        String oldContactName = contact.getName();

        String name = "Van Uytven";
        contact.setName(name);

        restTemplate.postForEntity("/api/contacts", contact, Contact.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        response = restTemplate
                .getForEntity("/api/contacts/1", Contact.class);
        assertThat(response.getBody())
                .hasFieldOrPropertyWithValue("name", name);

        contact.setName(oldContactName);
        restTemplate.postForEntity("/api/contacts", contact, Contact.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
