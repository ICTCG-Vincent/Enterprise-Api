package com.ictcg.service;

import com.ictcg.service.models.Address;
import com.ictcg.service.models.Contact;
import com.ictcg.service.models.Enterprise;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EnterpriseApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnAEnterprise() {
        ResponseEntity<Enterprise> response = restTemplate
                .getForEntity("/api/enterprises/1", Enterprise.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Enterprise enterprise = response.getBody();

        assertThat(enterprise)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Genesis");
    }

    @Test
    public void shouldReturnListOfContact() {
        ResponseEntity<Enterprise[]> response = restTemplate
                .getForEntity("/api/enterprises", Enterprise[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Enterprise> enterprises = Arrays.asList(response.getBody());

        assertThat(enterprises).hasSize(4);
        assertThat(enterprises).element(0)
                .hasFieldOrPropertyWithValue("name", "Genesis")
                .hasFieldOrPropertyWithValue("id", 1);
        assertThat(enterprises).element(1)
                .hasFieldOrPropertyWithValue("name", "RealDolmen")
                .hasFieldOrPropertyWithValue("id", 2);
    }

    @Test
    public void shouldChangeNameOfEnterprise() {
        ResponseEntity<Enterprise> response = restTemplate
                .getForEntity("/api/enterprises/1", Enterprise.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Enterprise enterprise = response.getBody();

        String name = "ICTCG";
        enterprise.setName(name);

        restTemplate.postForEntity("/api/enterprises", enterprise, Enterprise.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        response = restTemplate
                .getForEntity("/api/enterprises/1", Enterprise.class);
        assertThat(response.getBody())
                .hasFieldOrPropertyWithValue("name", name);
    }

    @Test
    public void shouldChangeAddressOfEnterprise() {
        ResponseEntity<Enterprise> response = restTemplate
                .getForEntity("/api/enterprises/1", Enterprise.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Enterprise enterprise = response.getBody();

        String city = "new City";
        Optional<Address> first = enterprise.getAddress().stream().findFirst();
        first.get().setCity(city);

        restTemplate.postForEntity("/api/enterprises", enterprise, Enterprise.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        response = restTemplate
                .getForEntity("/api/enterprises/1", Enterprise.class);

        Set<Address> address = response.getBody().getAddress();

        assertThat(StringUtils.equals(address.stream().findFirst().get().getCity(), city));
    }

}
