package com.tec.producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.tec.producto.controller",
    "com.tec.producto.service", 
    "com.tec.producto.serviceImpl",
    "com.tec.producto.repository"
})
public class ProductoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductoApplication.class, args);
    }
    
    @Configuration
    public class AppConfig {
        
        @Bean
        public WebMvcConfigurer webMvcConfigurer() {
            return new WebMvcConfigurer() {
                
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            // CORREGIDO: Usar allowedOriginPatterns en lugar de allowedOrigins
		                    .allowedOriginPatterns(
		                    	    "http://localhost:*",           // Cualquier puerto en localhost
		                    	    "http://127.0.0.1:*",          // Cualquier puerto en 127.0.0.1
		                    	    "http://192.168.*.*:*",        // Cualquier puerto en red local
		                    	    "http://10.*.*.*:*",           
		                    	    "http://172.16.*.*:*",
		                    	    "https://*.ondigitalocean.app", // Digital Ocean App Platform
		                    	    "http://*.ondigitalocean.app",  // Digital Ocean App Platform (HTTP)
		                    	    "*"                            // Permitir todos los orígenes (para desarrollo)
		                    	)
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                            .allowedHeaders("*")
                            .allowCredentials(false)  // CAMBIADO: false para evitar conflictos
                            .maxAge(3600);
                }
                
                @Override
                public void addResourceHandlers(ResourceHandlerRegistry registry) {
                    // Mapear /uploads/** a /uploads/ (raíz de uploads, no solo categorias)
                    registry.addResourceHandler("/uploads/**")
                            .addResourceLocations("file:/uploads/")
                            .setCachePeriod(3600);
                }
            };
        }
    }
}