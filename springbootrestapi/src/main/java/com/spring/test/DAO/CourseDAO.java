package com.spring.test.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.test.entites.Course;

public interface CourseDAO extends JpaRepository<Course, Long> {

}
