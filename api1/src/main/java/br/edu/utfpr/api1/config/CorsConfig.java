package br.edu.utfpr.api1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
      .addMapping("/**")                 // todas as rotas
      .allowedOrigins("http://localhost:3000")  // seu front
      .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
      .allowedHeaders("*")               // inclusive Authorization
      .allowCredentials(true);
  }
}
