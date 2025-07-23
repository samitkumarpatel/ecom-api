package net.samitkumar.ecom.catalog;

import net.samitkumar.ecom.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertAll;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ProductControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    void productControllerTest() {
        AtomicLong categoryId = new AtomicLong();

        assertAll(
                () -> webTestClient
                        .get()
                        .uri("/products")
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
                                {
                                    "name": "Electronics",
                                    "description": "All electronic items"
                                }
                                """)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .jsonPath("$.id").value(id -> categoryId.set(Long.valueOf(id.toString())))
                        .json("""
                          {
                            "name": "Electronics",
                            "description": "All electronic items"
                          }
                        """)
                ,
                () -> webTestClient
                        .post()
                        .uri("/category/{id}/product", categoryId.get())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("""
                        {}
                        """).exchange()
                        .expectStatus()
                        .isBadRequest(),
                () -> webTestClient
                        .post()
                        .uri("/category/{id}/product", categoryId.get())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("""
                                {
                                    "name": "Smartphone",
                                    "description": "Latest model smartphone",
                                    "price": 699.99
                                }
                                """)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(1)
                        .jsonPath("$.category").isEqualTo(categoryId.get())
                        .json("""
                            {
                              "name":"Smartphone",
                              "description":"Latest model smartphone",
                              "price":699.99,
                              "inventory":null
                            }
                        """),
                () -> webTestClient
                        .get()
                        .uri("/products")
                        .exchange()
                        .expectBody()
                        .jsonPath("$[0].category").isEqualTo(categoryId.get())
                        .json("""
                          [{"id":1,"name":"Smartphone","description":"Latest model smartphone","price":699.99,"inventory":null}]
                        """),
                () -> webTestClient
                        .get()
                        .uri("/category/{id}/product/{productId}", categoryId.get(),1)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .jsonPath("$.category").isEqualTo(categoryId.get())
                        .json("""
                            {
                              "id": 1,
                              "name": "Smartphone",
                              "description": "Latest model smartphone",
                              "price": 699.99,
                              "inventory": null
                            }
                        """),
        () -> webTestClient
                .put()
                .uri("/category/{categoryId}/product/{id}", categoryId.get(), 1)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "Updated Smartphone",
                            "description": "Updated description",
                            "price": 799.99
                        }
                        """)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.category").isEqualTo(categoryId.get())
                .json("""
                            {
                              "id": 1,
                              "name": "Updated Smartphone",
                              "description": "Updated description",
                              "price": 799.99,
                              "inventory": null
                            }
                        """),
                () -> webTestClient
                        .patch()
                        .uri("/category/{categoryId}/product/{id}", categoryId.get(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("""
                                {
                                    "inventory": {
                                        "quantity": 50
                                    }
                                }
                                """)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .jsonPath("$.category").isEqualTo(categoryId.get())
                        .json("""
                            {
                              "id": 1,
                              "name": "Updated Smartphone",
                              "description": "Updated description",
                              "price": 799.99,
                              "inventory": {"id":1,"productId":null,"quantity":50}
                            }
                        """),
                () -> webTestClient
                        .delete()
                        .uri("/category/{categoryId}/product/{id}", categoryId.get(), 1)
                        .exchange()
                        .expectStatus()
                        .isOk(),
                () -> webTestClient
                        .get()
                        .uri("/category/{categoryId}/product/{id}", categoryId.get(), 1)
                        .exchange()
                        .expectStatus()
                        .isNotFound()

        );
    }
}
