package ru.alishev.springcourse.FirstRestApp.util;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;

public class ErrorsUtil {//Логика возврата ошибки клиенту совпадает и для SensorController и для MeasurementController
    public static void returnErrorsToClient(BindingResult bindingResult) {//берёт BindingResult
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();//вычленяет тексты ошибок из BindingResult и конкатенирует в одну большую строку
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }

        throw new MeasurementException(errorMsg.toString());//возвращаем ошибку в виде Exception
    }
}
