package kea.exercise.studentadmin.controllers;

import kea.exercise.studentadmin.dtos.StudentRequestDTO;
import kea.exercise.studentadmin.dtos.StudentResponseDTO;
import kea.exercise.studentadmin.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponseDTO> getAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<StudentResponseDTO>> getStudentById(@PathVariable int id) {
        var student = studentService.findById(id);
        if (student.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public StudentResponseDTO createStudent(@RequestBody StudentRequestDTO student) {
        return studentService.save(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable int id, @RequestBody StudentRequestDTO student) {
        return ResponseEntity.of(studentService.updateIfExists(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> deleteStudent(@PathVariable int id) {
        return ResponseEntity.of(studentService.deleteById(id));
    }
}