package ru.alishev.springcourse.FirstRestApp.dto;
import java.util.List;

public class MeasurementsResponse{//просто из Листа делает объект MeasurementsResponse
    private List<MeasurementDTO> measurements;

    public MeasurementsResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }
    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
