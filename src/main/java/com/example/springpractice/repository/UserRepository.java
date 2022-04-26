package com.example.springpractice.repository;

import com.example.springpractice.bean.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
