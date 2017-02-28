package uk.ac.cam.alpha2017.airqualityradar.backend.models.measurements;

public class WindDirectionMeasurement implements StringMeasurement {
    private String units;
    private String value;

    public WindDirectionMeasurement(String value){
        this.value = value;
    }
    /*
    /**
     * Gives the value of the measurement
     *
     * @return Value of the measurement
     */
    @Override
    public String getValue() {
        return value;
    }
}
