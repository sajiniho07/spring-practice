package com.example.springpractice.repository;

import com.example.springpractice.bean.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Exam, Long> {
}
