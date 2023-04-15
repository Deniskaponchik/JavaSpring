package ru.alishev.springcourse.config;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Заменяет web.xml
public class MySpringMVCDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override  //этот метод использовать не будем
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //подставляем путь до класса Spring конфигурации
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        //все запросы http-запросы от пользователя пересылаем на DispatcherServlet
        return new String[]{"/"};
    }
}
