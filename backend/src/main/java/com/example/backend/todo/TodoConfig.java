package com.example.backend.todo;

import com.example.backend.todo.app.TodoServiceImpl;
import com.example.backend.todo.db.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaRepositories("com.example.backend.todo.db")
public class TodoConfig implements WebMvcConfigurer {

    TodoRepository repository;

    @Autowired
    public TodoConfig(TodoRepository repository) {
        this.repository = repository;
    }

    @Bean
    public TodoServiceImpl todoService() {
        return new TodoServiceImpl(repository);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
