package pl.gov.coi.pomocua.ads.dictionaries.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.gov.coi.pomocua.ads.dictionaries.domain.City;
import pl.gov.coi.pomocua.ads.dictionaries.domain.CityRepository;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CityLookupResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CityRepository cityRepository;

    private static final String URL = "/api/dictionaries/city";

    @Test
    void shouldReturnEmptyResponse() {
        // when:
        ResponseEntity<CityLookupResponse> response = restTemplate.getForEntity(URL + "/?region=mazowieckie&city=x", CityLookupResponse.class);

        // then:
        assertThat(response.hasBody()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnListOfMatchingCities() {
        // given:
        givenFollowingCitiesExists("mazowieckie/war1", "mazowieckie/war2");

        // when:
        ResponseEntity<CityLookupResponse> response = restTemplate.getForEntity(URL + "/?region=mazowieckie&city=War", CityLookupResponse.class);

        // then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().cities()).contains("war1", "war2");
    }

    @Test
    void shouldImportCitiesFromFile() {
        // when:
        ResponseEntity<CityLookupResponse> response = restTemplate.getForEntity(URL + "/?region=ŚLĄSKIE&city=Bi", CityLookupResponse.class);

        // then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().cities()).contains("bieruń", "bielsko-biała");
    }

    // ---

    private void givenFollowingCitiesExists(String... values) {
        Stream.of(values).forEach(value -> {
            String[] parts = value.split("/");
            cityRepository.save(new City(parts[1], parts[0]));
        });
    }
}