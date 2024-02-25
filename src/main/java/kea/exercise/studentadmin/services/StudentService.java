package kea.exercise.studentadmin.services;

import kea.exercise.studentadmin.dtos.student.StudentRequestDTO;
import kea.exercise.studentadmin.dtos.student.StudentRequestDTOMapper;
import kea.exercise.studentadmin.dtos.student.StudentResponseDTO;
import kea.exercise.studentadmin.dtos.student.StudentResponseDTOMapper;
import kea.exercise.studentadmin.models.Student;
import kea.exercise.studentadmin.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentResponseDTOMapper studentResponseDTOMapper;
    private final StudentRequestDTOMapper studentRequestDTOMapper;

    public StudentService(StudentRepository studentRepository, StudentResponseDTOMapper studentResponseDTOMapper, StudentRequestDTOMapper studentRequestDTOMapper) {
        this.studentRepository = studentRepository;
        this.studentResponseDTOMapper = studentResponseDTOMapper;
        this.studentRequestDTOMapper = studentRequestDTOMapper;
    }

    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll().stream().map(studentResponseDTOMapper).toList();
    }

    public Optional<StudentResponseDTO> findById(Long id) {
        return studentRepository.findById(id).map(studentResponseDTOMapper);
    }

    public StudentResponseDTO save(StudentRequestDTO student) {
        Student newStudent = studentRequestDTOMapper.apply(student);
        studentRepository.save(newStudent);
        return studentResponseDTOMapper.apply(newStudent);
    }

    public Optional<StudentResponseDTO> deleteById(Long id) {
        Optional<Student> studentToDelete = studentRepository.findById(id);

        if(studentToDelete.isPresent()) {
            StudentResponseDTO studentResponse = studentResponseDTOMapper.apply(studentToDelete.get());
            //TODO: Fjern student fra courses/house
            studentRepository.delete(studentToDelete.get());
            return Optional.of(studentResponse);
        }

        return Optional.empty();
    }

    public Optional<StudentResponseDTO> updateIfExists(Long id, StudentRequestDTO student) {
        if (studentRepository.existsById(id)) {
            Student entity = studentRequestDTOMapper.apply(student);
            entity.setId(id);
            return Optional.of(studentResponseDTOMapper.apply(studentRepository.save(entity)));
        }
        return Optional.empty();
    }

    public Optional<StudentResponseDTO> updateStudentByFields(Long id, Map<String, Object> fields) {
        Optional<Student> studentToUpdate = studentRepository.findById(id);

        studentToUpdate.ifPresent(student -> fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Student.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, student, value);
            }
        }));

        return studentToUpdate.map(studentRepository::save).map(studentResponseDTOMapper);
    }
}

