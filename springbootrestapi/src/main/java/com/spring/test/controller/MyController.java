package com.spring.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.test.entites.Course;
import com.spring.test.service.CourseService;

@CrossOrigin
@RestController
public class MyController {
	
	@Autowired
	private CourseService courseservice;
	@GetMapping("/home")
	public String home() {
	return "Home page";
	}
	
//get courses 
	@GetMapping("/courses")
	public List<Course> getCourses(){
return courseservice.getCourses();
	}
	
	//get single course
	@GetMapping("/courses/{id}")
	public Course getCourse(@PathVariable String id) {
		return this.courseservice.getCourse(Long.parseLong(id));
	}
	
	//add course
	@PostMapping("/courses")
	public Course addCourse(@RequestBody Course course) {
		return courseservice.addCourse(course);
	}
	
	//Update Course 
	@PutMapping("/courses/update/{id}")
	public Course updateCourse(@RequestBody Course course ) {
		
		return courseservice.updateCourse(course);
		
	}
	
@DeleteMapping("/courses/delete/{id}")
	public ResponseEntity<HttpStatus> deleteCourse(@PathVariable String id) {
try {
	courseservice.deleteCourse(Long.parseLong(id));
	return new ResponseEntity<>(HttpStatus.OK);
} catch (Exception e) {
	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
}
}
}
