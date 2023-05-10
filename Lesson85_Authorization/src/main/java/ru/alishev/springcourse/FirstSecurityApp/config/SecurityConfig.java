package ru.alishev.springcourse.FirstSecurityApp.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Авторизация на уровне методов. Обычно не используется в контроллерах. Можно повешать в Сервисе
public class SecurityConfig extends WebSecurityConfigurerAdapter {// Будем настраивать Аутентификацию и Авторизацию.
    // в новых версиях spring-boot-starter-parent отказались от WebSecurityConfiguration
    //https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

    private final PersonDetailsService personDetailsService; //Spring с помощью сервиса получит человека и сам проверит у него логин и пароль

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //переопределение этих двух методов должно привести к замене страницы аутентификации на кастомную
    protected void configure(HttpSecurity http) throws Exception {      //configure Spring Security
        http                      //Правила авторизации читаются сверху вниз
        //.csrf().disable()       //отключаем защиту от межсайтовой подделки запросов. Whitelabel Error Page
        .authorizeRequests()      //configure authorisation
        //.antMatchers("/admin").hasRole("ADMIN")  //Spring сам ДОБАВИТ в названии ROLE_
        //НЕ аутентифицированного пользователя МЫ должны пускать на эти 3 странички. Доступны всем:
        .antMatchers("/auth/login","/auth/registration", "/error").permitAll()
        //.anyRequest().authenticated()   //На все остальные страницы мы НЕ пускаем НЕ аутентифицированных пользователей. Должен быть Аутентифицирован
        .anyRequest().hasAnyRole("USER", "ADMIN") //ко ВСЕМ ОСТАЛЬНЫМ страницам добавить доступ USER, ADMIn
        .and()                          //and configure Authentication
        .formLogin().loginPage("/auth/login")        //меняем адрес формы логина
        .loginProcessingUrl("/process_login")        //указываем, куда отправлять данные с формы аутентификации
        .defaultSuccessUrl("/hello", true)   //что делать в случае успешной аутентификации. true - ВСЕГДА перенаправлять
        .failureUrl("/auth/login?error?")       //в случае неуспешной аутентификации передавать параметр error, который увидит thymeleaf
        .and()
        //logout = из сессии удаляется человек + у пользователя удаляются куки
        .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login"); //сами не реализуем логику logout. всё сделает Spring за нас
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Configure Authentication
        auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean   //механизм шифрования пароля
    public PasswordEncoder getPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }




    /*в БАЗОВОЙ Spring Authentication данный класс НЕ ИСПОЛЬЗУЕТСЯ. Только для сложных Spring Приложений с CAS (пароли хранятся на удалённом сервере)
    public final AuthProviderImp authProvider;
    @Autowired
    public SecurityConfig(AuthProviderImp authProvider) {        this.authProvider = authProvider;    }
    protected void configure(AuthenticationManagerBuilder auth){ auth.authenticationProvider(authProvider); }   //Configure Authentication
    */


}
