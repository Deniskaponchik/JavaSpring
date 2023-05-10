import java.util.List;

public class YandexResponse { //Будет соответствовать json-объекту, приходящему с Яндекса
    private List<Translation> translations;

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
