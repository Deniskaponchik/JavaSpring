import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        final String sensorName = "Sensor123";
        registerSensor(sensorName);

        Random random = new Random();
        double minTemperature = 0.0;
        double maxTemperature = 45.0;
        for (int i = 0; i < 500; i++) {
            System.out.println(i);
            sendMeasurement(random.nextDouble() * maxTemperature, random.nextBoolean(), sensorName);
            //nextDouble - возвращается случайное число от 0.0 до 1.0
        }
    }

    private static void registerSensor(String sensorName) {
        final String url = "http://localhost:8080/sensors/registration";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);
        makePostRequestWithJSONData(url, jsonData);
    }


    private static void sendMeasurement(double value, boolean raining, String sensorName) {
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();      //создаём заголовки
        headers.setContentType(MediaType.APPLICATION_JSON); //чтобы принимающая сторона знала, что отправляем JSON
        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers); //упаковываем в HttpEntity

        try {
            restTemplate.postForObject(url, request, String.class);//переадём: url, тело запроса, что должно возвращаться из этого запроса(String.class).
            // То, что возвращается, мы даже никуда не сохраняем, т.к. нам это нигде не надо
            System.out.println("Измерение успешно отправлено на сервер!");
        } catch (HttpClientErrorException e) {
            System.out.println("ОШИБКА!");
            System.out.println(e.getMessage());
        }
    }
}
