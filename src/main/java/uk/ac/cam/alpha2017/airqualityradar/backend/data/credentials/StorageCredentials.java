package uk.ac.cam.alpha2017.airqualityradar.backend.data.credentials;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class StorageCredentials {
    public StorageConnectionInfo getConnectionInfo() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(new File("deploy/storage_connection.json"), StorageConnectionInfo.class);
    }
}
