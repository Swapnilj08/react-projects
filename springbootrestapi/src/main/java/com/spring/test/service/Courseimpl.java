package com.spring.test.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.test.DAO.CourseDAO;
import com.spring.test.entites.Course;
@Service
public class Courseimpl implements CourseService {
	@Autowired
	private CourseDAO coursedao;
//	List<Course> list;
//	
//	public Courseimpl(){
//		list=new ArrayList<>();
//		list.add(new Course(1, "java", "Core Java"));
//		list.add(new Course(2, "JavaAdv ", "Advance Java"));
//		list.add(new Course(3, "Spring", "Spring Mvc"));
//		
//	
//	}
	
	@Override
	public List<Course> getCourses() {
		
		return coursedao.findAll();
	}

	@Override
	public Course getCourse(long courseId) {
//		Course c= null;
//		//get list of course
//		for (Course course:list) {
//			//compare id of requested course with list of courses 
//			if(course.getId()==courseId) {
//				//if id matches return course details
//				c=course;
//				break;
//			}
//		}
		
		
		return coursedao.findById(courseId).get();
			}

	@Override
	public Course addCourse(Course course) {
//		 list.add(course);
	coursedao.save(course);
		return course;
	}

	@Override
	public Course updateCourse(Course course) {
//	list.forEach(e->{
//		if(e.getId()==course.getId()) {
//			e.setId(course.getId());
//			e.setTitle(course.getTitle());
//			e.setDescription(course.getDescription());
//		}
//	});
		
		coursedao.save(course);
		return course;
	}
	
	@Override
	public void deleteCourse(long courseId) {
		//list.remove(courseId);
//	 list = this.list.stream().filter(e->e.getId()!=courseId).collect(Collectors.toList());
	Course course = coursedao.getOne(courseId);
		coursedao.delete(course);
	}

	

}
