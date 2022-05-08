package com.example.springpractice.repository;

import com.example.springpractice.bean.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
