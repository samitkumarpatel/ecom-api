package net.samitkumar.ecom.catalog;

import net.samitkumar.ecom.TestcontainersConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertAll;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class CatalogControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testCategoryRouter() {
        assertAll(
                () -> webTestClient
                        .get()
                        .uri("/category")
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .json("""
                         []
                        """),
                () -> webTestClient
                        .post()
                        .uri("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("""
                                {}
                                """)
                        .exchange()
                        .expectStatus()
                        .isBadRequest(),
                () -> webTestClient
                        .post()
                        .uri("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("""
                                {
                                    "name": "Electronics",
                                    "description": "All electronic items"
                                }
                                """)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .jsonPath("$.id").isNotEmpty()
                        .jsonPath("$.name").isEqualTo("Electronics")
                        .jsonPath("$.description").isEqualTo("All electronic items"),
                () -> webTestClient
                        .get()
                        .uri("/category/{id}", 1)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .json("""
                           {
                             "id": 1,
                             "name": "Electronics",
                             "description": "All electronic items"
                           }
                        """),
                () -> webTestClient
                        .put()
                        .uri("/category/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("""
                                {
                                    "name": "Updated Electronics",
                                    "description": "Updated description"
                                }
                                """)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .json("""
                           {
                             "id": 1,
                             "name": "Updated Electronics",
                             "description": "Updated description"
                           }
                        """),
                () -> webTestClient
                        .patch()
                        .uri("/category/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("""
                                {
                                    "name": "Partially Updated Electronics"
                                }
                                """)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .json("""
                           {
                             "id": 1,
                             "name": "Partially Updated Electronics",
                             "description": "Updated description"
                           }
                        """),
                () -> webTestClient
                        .delete()
                        .uri("/category/{id}", 1)
                        .exchange()
                        .expectStatus()
                        .isOk(),
                () -> webTestClient
                        .get()
                        .uri("/category/{id}", 1)
                        .exchange()
                        .expectStatus()
                        .isNotFound()

        );
    }
}
