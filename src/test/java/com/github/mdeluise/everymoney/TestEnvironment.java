package com.github.mdeluise.everymoney;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.mdeluise.everymoney.security.services.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestEnvironment {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetailsImpl adminUser =
            new UserDetailsImpl(0L, "admin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UserDetailsImpl simpleUser = new UserDetailsImpl(0L, "user", "user", List.of());

        return new InMemoryUserDetailsManager(List.of(adminUser, simpleUser));
    }


    @Bean
    public ObjectMapper objectMapper() {
        // not only return new ObjectMapper() because if so, since in the test only some classes
        // are @Import, it will lead to error with Instant conversion in ModelMapper
        return JsonMapper.builder().addModule(new ParameterNamesModule()).addModule(new Jdk8Module())
                         .addModule(new JavaTimeModule()).build();

    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
