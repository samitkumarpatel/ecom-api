package net.samitkumar.ecomdb.it;

import net.samitkumar.ecomdb.TestcontainersConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataRestEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/categories")
    @Order(1)
    void categoryEndpointTest() {
        assertAll(
                //POST
                () -> mockMvc.perform(post("/categories")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("""
                                {
                                    "name": "Electronics",
                                    "description": "Electronic Items"
                                }
                                """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.name").value("Electronics"))
                        .andExpect(jsonPath("$.description").value("Electronic Items"))
                        .andExpect(content().json("""
                                {
                                    "id": 1,
                                    "name": "Electronics",
                                    "description": "Electronic Items"
                                }
                                """)
                        ),
                //POST
                () -> mockMvc.perform(post("/categories")
                                .contentType("application/json")
                                .accept("application/json")
                                .content("""
                                {
                                    "name": "Clothes",
                                    "description": "Mens Clothing",
                                    "products" : [
                                        {
                                            "name": "Shirt",
                                            "description": "Formal Shirt",
                                            "price": 1000.0,
                                            "inventory": {
                                                "quantity": 100
                                            }
                                        },
                                        {
                                            "name": "Trousers",
                                            "description": "Formal Trousers",
                                            "price": 2000.0,
                                            "inventory": {
                                                "quantity": 50
                                            }
                                        }
                                    ]
                                }
                                """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(2))
                        .andExpect(jsonPath("$.name").value("Clothes"))
                        .andExpect(jsonPath("$.description").value("Mens Clothing"))
                        .andExpect(content().json("""
                                {
                                    "id": 2,
                                    "name": "Clothes",
                                    "description": "Mens Clothing",
                                    "products": [
                                      {
                                        "id": 1,
                                        "category": null,
                                        "name": "Trousers",
                                        "description": "Formal Trousers",
                                        "price": 2000.0,
                                        "inventory": {
                                          "id": 1,
                                          "productId": null,
                                          "quantity": 50
                                        }
                                      },
                                      {
                                        "id": 2,
                                        "category": null,
                                        "name": "Shirt",
                                        "description": "Formal Shirt",
                                        "price": 1000.0,
                                        "inventory": {
                                          "id": 2,
                                          "productId": null,
                                          "quantity": 100
                                        }
                                      }
                                    ]
                                  }
                        """)
                        ),
                // PUT
                () -> mockMvc.perform(put("/categories/1")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("""
                                {
                                    "id": 1,
                                    "name": "Electronics",
                                    "description": "Electronics Appliances"
                                }
                                """))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.name").value("Electronics"))
                        .andExpect(jsonPath("$.description").value("Electronics Appliances")),
                // GET
                () -> mockMvc.perform(get("/categories/1")
                        .accept("application/json"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.name").value("Electronics"))
                        .andExpect(jsonPath("$.description").value("Electronics Appliances")),
                // DELETE
                () -> mockMvc.perform(delete("/categories/1")
                        .accept("application/json"))
                        .andExpect(status().is2xxSuccessful()),
                // GET
                () -> mockMvc.perform(get("/categories/1")
                                .accept("application/json"))
                        .andExpect(status().isNotFound()),
                // DELETE
                () -> mockMvc.perform(delete("/categories/3")
                                .accept("application/json"))
                        .andExpect(status().isNotFound())

        );
    }

    @Test
    @DisplayName("/products")
    @Order(2)
    void productEndpointTest() {
        assertAll(
                //POST
                () -> mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .accept("application/json")
                        .content("""
                                {
                                    "category": 2,
                                    "name": "Jeans",
                                    "description": "Jean Pants",
                                    "price": 1000.0,
                                    "inventory": {
                                        "quantity": 100
                                    }
                                }
                                """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(3))
                        .andExpect(jsonPath("$.name").value("Jeans"))
                        .andExpect(jsonPath("$.description").value("Jean Pants"))
                        .andExpect(jsonPath("$.price").value(1000.0))
                        .andExpect(jsonPath("$.inventory.quantity").value(100))
                        .andExpect(content().json("""
                                {
                                    "id": 3,
                                    "name": "Jeans",
                                    "description": "Jean Pants",
                                    "price": 1000.0,
                                    "inventory": {
                                        "quantity": 100
                                    }
                                }
                                """)
                        )
                );
    }
}
