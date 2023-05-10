import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println("введите предложение на русском языке");
        Scanner scanner = new Scanner(System.in);
        String sentenceToTranslate = scanner.nextLine();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        HttpHeaders headers = new HttpHeaders();  //import org.springframework.http.HttpHeaders;
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + "insert yandex token here");  //Insert yandex token

        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("folderId", "folder id from yandex");
        jsonData.put("targetLanguageCode", "en");
        jsonData.put("texts", "[" + sentenceToTranslate + "]"); //можно вставлять массив из текстов

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonData, headers);
        /* Вариант без создания отдельных классов под JSON-Объекты для Jackson
        String response = restTemplate.postForObject(url, request, String.class);
        //System.out.println(response);
        ObjectMapper mapper = new ObjectMapper(); //Jackson
        JsonNode obj = mapper.readTree(response);
        //Получаем массив, который под ключом translations + первый элемент в этом массиве + в нём ключ text
        System.out.println("Перевод: " + obj.get("translations").get(0).get("text"));
        */
        YandexResponse response = restTemplate.postForObject(url, request, YandexResponse.class);
        System.out.println("Перевод: " + response.getTranslations().get(0).getText());
    }
}
