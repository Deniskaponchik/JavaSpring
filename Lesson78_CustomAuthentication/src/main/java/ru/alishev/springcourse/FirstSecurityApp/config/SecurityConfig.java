package ru.alishev.springcourse.FirstSecurityApp.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import ru.alishev.springcourse.FirstSecurityApp.security.AuthProviderImp;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration{//Будем настраивать Аутентификацию и Авторизацию

    public final AuthProviderImp authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImp authProvider) {        this.authProvider = authProvider;    }

    protected void configure(AuthenticationManagerBuilder auth){ //Configure Authentication
            auth.authenticationProvider(authProvider);
        }
}
