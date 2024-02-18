package kea.exercise.studentadmin.controllers;

import kea.exercise.studentadmin.models.Teacher;
import kea.exercise.studentadmin.repositories.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Teacher>> getTeacherById(@PathVariable int id) {
        var teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(teacher);
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        var teacherToUpdate = teacherRepository.findById(id);
        if (teacherToUpdate.isPresent()) {
            Teacher updatedTeacher  = teacherToUpdate.get();
            updatedTeacher.setFirstName(teacher.getFirstName());
            updatedTeacher.setMiddleName(teacher.getMiddleName());
            updatedTeacher.setLastName(teacher.getLastName());
            updatedTeacher.setDateOfBirth(teacher.getDateOfBirth());
            updatedTeacher.setHouse(teacher.getHouse());
            updatedTeacher.setHeadOfHouse(teacher.isHeadOfHouse());
            updatedTeacher.setEmploymentType(teacher.getEmploymentType());
            updatedTeacher.setEmploymentStart(teacher.getEmploymentStart());
            updatedTeacher.setEmploymentEnd(teacher.getEmploymentEnd());
            teacherRepository.save(updatedTeacher);
            return ResponseEntity.ok(updatedTeacher);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable int id) {
        var teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        teacherRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
