package ru.alishev.springcourse.FirstSecurityApp.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;
import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails { //Класс обёртка над сущностью Person
    private final Person person;
    public PersonDetails(Person person) {        this.person = person;    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//Возвращает роль человека или список действий, которые может совершать
        //List<new SimpleGrantedAuthority(person.getRole())> //для списка доступных действий
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole())); //список из одной роли
    }

    @Override
    public String getPassword() {        return this.person.getPassword();    }

    @Override
    public String getUsername() {        return this.person.getUsername();    }

    @Override
    public boolean isAccountNonExpired() {//можем доставать из БД время регистрации и создать логику просрочки через 2 недели
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Person getPerson(){//чтобы получать данные аутентифицированного пользователя
        return this.person;
    }
}
