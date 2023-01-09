package com.github.mdeluise.everymoney;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info =
    @Info(
        title = "Every Money REST API",
        version = "1.0",
        description = "<h1>Introduction</h1>",
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
        contact = @Contact(name = "GitHub page", url = "https://github.com/MDeLuise/every-money")
    )
)
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        // ModelMapper modelMapper = new ModelMapper();
        // modelMapper.registerModule(new JSR310Module());
        // return modelMapper;
        return new ModelMapper();
    }
}
