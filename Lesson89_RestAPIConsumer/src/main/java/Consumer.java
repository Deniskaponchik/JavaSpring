import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        /* GET
        String url = "https://reqres.in/api/users/2";
        String response = restTemplate.getForObject(url, String.class); //Делаем GET-запрос на сайт
        System.out.println(response);
        */
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "test name");
        jsonToSend.put("job", "Test job");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);
        String url = "https://reqres.in/api/users/";
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);

    }
}
