package ru.alishev.springcourse.FirstRestApp.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors){//проверяет, что сенсор есть в БД
        Measurement measurement = (Measurement) o;

        if (measurement.getSensor() == null) {return;}//если сенсор есть - продолжаем

        if (sensorsService.findByName(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "Нет зарегистрированного сенсора с таким именем!");
        //сенсора нет - ошибка. Добавлять measurements в БД нельзя
    }
}
