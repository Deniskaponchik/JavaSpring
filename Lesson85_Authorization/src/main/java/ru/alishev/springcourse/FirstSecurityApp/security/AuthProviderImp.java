package ru.alishev.springcourse.FirstSecurityApp.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;
import java.util.Collections;

@Component //чтобы могли внедрять аннотоцией Autowired
public class AuthProviderImp implements AuthenticationProvider {//Будем смотреть в БД и сравнивать пароль, который был введён с формы с табличным
    //в БАЗОВОЙ Spring Authentication данный класс НЕ ИСПОЛЬЗУЕТСЯ. Только для сложных Spring Приложений с CAS (пароли хранятся на удалённом сервере)

    private final PersonDetailsService personDetailsService;
    @Autowired //чтобы всё было явно
    public AuthProviderImp(PersonDetailsService personDetailsService) {        this.personDetailsService = personDetailsService;    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException { //логика аутентификации
        String username = authentication.getName();

        UserDetails personDetails = personDetailsService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();

        if(!password.equals(personDetails.getPassword()))//сравниваем пароль с PersonDetails
            throw new BadCredentialsException("Incorrect password");

        //Полученный Объект будет добавляться в сессию. Мы всегда его сможем доставать из сессии
        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {//нужен, чтобы спрингу понять, для какого объекта authentication он работает
        //если в приложении несколько authetication providers, то в этом методе указать, для каких сценариев какой провайдер использовать
        return true;
    }

}
