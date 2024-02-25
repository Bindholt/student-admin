package kea.exercise.studentadmin.controllers;

import kea.exercise.studentadmin.dtos.teacher.TeacherRequestDTO;
import kea.exercise.studentadmin.dtos.teacher.TeacherResponseDTO;
import kea.exercise.studentadmin.models.Teacher;
import kea.exercise.studentadmin.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping
    public List<TeacherResponseDTO> getAll() {
        return teacherService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<TeacherResponseDTO>> getTeacherById(@PathVariable Long id) {
        var teacher = teacherService.findById(id);
        if (teacher.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teacher);
    }
    @PostMapping
    public TeacherResponseDTO createTeacher(@RequestBody TeacherRequestDTO teacher) {
        return teacherService.save(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Long id, @RequestBody TeacherRequestDTO teacher) {
        return ResponseEntity.of(teacherService.updateIfExists(id, teacher));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacherFields(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.of(teacherService.updateTeacherByFields(id, fields));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable Long id) {
        var teacher = teacherService.findById(id);
        if (teacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        teacherService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
