package uk.ac.cam.alpha2017.airqualityradar.backend.radarapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class HelloWorldControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(new HelloWorldController()).build();
    }

    @Test
    public void testHelloWorldReturnsHelloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RadarAPIEndpoints.HelloWorldEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }
}
