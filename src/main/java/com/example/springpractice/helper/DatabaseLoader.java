package com.example.springpractice.helper;

import com.example.springpractice.bean.Exam;
import com.example.springpractice.bean.User;
import com.example.springpractice.repository.PostRepository;
import com.example.springpractice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {
    /*@Bean
    CommandLineRunner init(UserRepository repository) {
        return args -> {
            repository.save(new User("sajad", "kamali", "waiter", "1234"));
            repository.save(new User("hasan", "ahmadi", "boss", "pas123"));
            repository.save(new User("mosa", "nnnn", "programmer", "pas123"));
        };
    }*/

}
