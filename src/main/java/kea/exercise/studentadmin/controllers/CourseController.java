package kea.exercise.studentadmin.controllers;

import kea.exercise.studentadmin.models.Course;
import kea.exercise.studentadmin.models.Student;
import kea.exercise.studentadmin.models.Teacher;
import kea.exercise.studentadmin.repositories.CourseRepository;
import kea.exercise.studentadmin.repositories.StudentRepository;
import kea.exercise.studentadmin.repositories.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public CourseController(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Course> getAll() {
        return courseRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Course>> getCourseById(@PathVariable int id) {
        var course = courseRepository.findById(id);
        if (course.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course);
    }
    @GetMapping("/{id}/teacher")
    public ResponseEntity<Teacher> getTeacherByCourseId(@PathVariable int id) {
        var course = courseRepository.findById(id);
        if (course.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var teacher = course.get().getTeacher();
        if(teacher == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsByCourseId(@PathVariable int id) {
        var course = courseRepository.findById(id);
        if (course.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var students = course.get().getStudents();
        if(students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        var courseToUpdate = courseRepository.findById(id);
        if (courseToUpdate.isPresent()) {
            Course updatedCourse  = courseToUpdate.get();
            updatedCourse.setSubject(course.getSubject());
            updatedCourse.setCurrent(course.isCurrent());
            updatedCourse.setSchoolYear(course.getSchoolYear());
            updatedCourse.setTeacher(course.getTeacher());
            updatedCourse.setStudents(course.getStudents());
            courseRepository.save(updatedCourse);
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/teacher")
    public ResponseEntity<Course> updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        var courseToUpdate = courseRepository.findById(id);
        if (courseToUpdate.isPresent()) {
            Course updatedCourse  = courseToUpdate.get();
            updatedCourse.setTeacher(teacher);
            courseRepository.save(updatedCourse);
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/students")
    public ResponseEntity<Course> updateStudents(@PathVariable int id, @RequestBody List<Student> students) {
        var courseToUpdate = courseRepository.findById(id);
        if (courseToUpdate.isPresent()) {
            Course updatedCourse  = courseToUpdate.get();
            updatedCourse.setStudents(students);
            courseRepository.save(updatedCourse);
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        var course = courseRepository.findById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/teacher")
    public ResponseEntity<Course> deleteTeacher(@PathVariable int id) {
        var course = courseRepository.findById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedCourse = course.get();
        updatedCourse.setTeacher(null);
        courseRepository.save(updatedCourse);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/students")
    public ResponseEntity<Course> deleteStudents(@PathVariable int id) {
        var course = courseRepository.findById(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedCourse = course.get();
        updatedCourse.setStudents(null);
        courseRepository.save(updatedCourse);
        return ResponseEntity.ok().build();
    }
}
