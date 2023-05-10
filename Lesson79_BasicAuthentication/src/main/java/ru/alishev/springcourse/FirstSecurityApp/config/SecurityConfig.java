package ru.alishev.springcourse.FirstSecurityApp.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration{//Будем настраивать Аутентификацию и Авторизацию


    //Spring с помощью сервиса получит человека и сам проверит у него логин и праоль
    private final PersonDetailsService personDetailsService;
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {        this.personDetailsService = personDetailsService;    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Configure Authentication
        auth.userDetailsService(personDetailsService); }

    @Bean   //механизм шифрования пароля
    public PasswordEncoder getPasswordEncoder(){         return NoOpPasswordEncoder.getInstance();    }



    /*в БАЗОВОЙ Spring Authentication данный класс НЕ ИСПОЛЬЗУЕТСЯ. Только для сложных Spring Приложений с CAS (пароли хранятся на удалённом сервере)
    public final AuthProviderImp authProvider;
    @Autowired
    public SecurityConfig(AuthProviderImp authProvider) {        this.authProvider = authProvider;    }
    protected void configure(AuthenticationManagerBuilder auth){ auth.authenticationProvider(authProvider); }   //Configure Authentication
    */








}
