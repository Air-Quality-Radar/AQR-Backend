package uk.ac.cam.alpha2017.airqualityradar.backend.radarapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.ac.cam.alpha2017.airqualityradar.backend.services.RadarDataService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AirQualityControllerTest {
    private MockMvc mockMvc;
    private RadarDataService mockDataService;

    @BeforeEach
    public void setup() {
        mockDataService = mock(RadarDataService.class);
        mockMvc = standaloneSetup(new AirQualityController(mockDataService)).build();
    }

    @Test
    public void testAirQualityParsesDateCorrectly() throws Exception {
        String dateString = "2015-05-09";

        mockMvc.perform(MockMvcRequestBuilders.get(RadarAPIEndpoints.AirQualityEndpoint, dateString))
               .andExpect(status().isOk());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedDate = dateFormat.parse(dateString);
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expectedDate);

        verify(mockDataService).getDataPoints(expectedCalendar);
    }
}
