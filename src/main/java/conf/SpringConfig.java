package conf;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"conf", "controller", "dao", "validation"})
@Import(JpaConfig.class)
public class SpringConfig implements WebMvcConfigurer {
    //this solution from here: https://www.javacodegeeks.com/2012/07/four-solutions-to-lazyinitializationexc_05.html
    @Bean
    protected Module module() {
        return new Hibernate5Module();
    }


}
