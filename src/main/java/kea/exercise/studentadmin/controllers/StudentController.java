package kea.exercise.studentadmin.controllers;

import kea.exercise.studentadmin.dtos.student.StudentRequestDTO;
import kea.exercise.studentadmin.dtos.student.StudentResponseDTO;
import kea.exercise.studentadmin.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Optional<StudentResponseDTO>> getStudentById(@PathVariable Long id) {
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
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO student) {
        return ResponseEntity.of(studentService.updateIfExists(id, student));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudentFields(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.of(studentService.updateStudentByFields(id, fields));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.of(studentService.deleteById(id));
    }
}