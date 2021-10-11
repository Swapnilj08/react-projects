package com.spring.test.service;

import java.util.List;

import com.spring.test.entites.Course;

public interface CourseService {
 public List<Course> getCourses();

public Course getCourse(long parseLong);

public Course addCourse(Course course);

public void deleteCourse(long parseLong);

public Course updateCourse(Course course);


}
