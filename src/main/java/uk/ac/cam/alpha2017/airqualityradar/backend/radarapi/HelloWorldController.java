package uk.ac.cam.alpha2017.airqualityradar.backend.radarapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(RadarAPIEndpoints.HelloWorldEndpoint)
    public String helloWorld() {
        return "Hello World!";
    }
}

