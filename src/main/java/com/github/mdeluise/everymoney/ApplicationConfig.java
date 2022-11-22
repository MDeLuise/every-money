package com.github.mdeluise.everymoney;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        // ModelMapper modelMapper = new ModelMapper();
        // modelMapper.registerModule(new JSR310Module());
        // return modelMapper;
        return new ModelMapper();
    }
}
