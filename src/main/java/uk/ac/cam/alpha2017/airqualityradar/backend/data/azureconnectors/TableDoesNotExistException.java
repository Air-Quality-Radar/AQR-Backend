package uk.ac.cam.alpha2017.airqualityradar.backend.data.azureconnectors;

public class TableDoesNotExistException extends Exception {
    public TableDoesNotExistException(String tableName) {
        super(String.format("Table %s doesn't exist", tableName));
    }
}
