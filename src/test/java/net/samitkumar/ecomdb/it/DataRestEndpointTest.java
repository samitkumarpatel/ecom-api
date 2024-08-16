package net.samitkumar.ecomdb.it;

import net.samitkumar.ecomdb.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
public class DataRestEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void endpointTest() {
        assertAll(
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
                                """))


        );
    }
}
