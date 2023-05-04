package ru.alishev.springcourse.config;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

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


    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException { //обрабатывает скрытые поля от Thymeleaf. Добавляем фильтр
        super.onStartup(aServletContext);
        registerCharacterEncodingFilter(aServletContext); //русский язык
        registerHiddenFieldFilter(aServletContext);
    }


    private void registerHiddenFieldFilter(ServletContext aContext) { //перенаправляет GET и POST на нужные методы контроллера
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }

    private void registerCharacterEncodingFilter(ServletContext aContext) { //русский язык при вводе форм
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = aContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }
}
