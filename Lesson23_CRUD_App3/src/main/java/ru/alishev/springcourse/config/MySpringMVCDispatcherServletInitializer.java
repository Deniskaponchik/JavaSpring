package ru.alishev.springcourse.config;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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

    //Позволяет обрабатывать скрытые поля от Thymeleaf. Добавляем фильтр
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }
    //Будет перенаправлять входящие GET и POST на нужные методы spring контроллера
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}
