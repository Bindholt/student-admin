package kea.exercise.studentadmin.services;

import kea.exercise.studentadmin.dtos.StudentRequestDTO;
import kea.exercise.studentadmin.dtos.StudentRequestDTOMapper;
import kea.exercise.studentadmin.dtos.StudentResponseDTO;
import kea.exercise.studentadmin.dtos.StudentResponseDTOMapper;
import kea.exercise.studentadmin.models.Student;
import kea.exercise.studentadmin.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Optional<StudentResponseDTO> findById(int id) {
        return studentRepository.findById(id).map(studentResponseDTOMapper);
    }

    public StudentResponseDTO save(StudentRequestDTO student) {
        Student newStudent = studentRequestDTOMapper.apply(student);
        studentRepository.save(newStudent);
        return studentResponseDTOMapper.apply(newStudent);
    }

    public Optional<StudentResponseDTO> deleteById(int id) {
        Optional<Student> studentToDelete = studentRepository.findById(id);

        if(studentToDelete.isPresent()) {
            StudentResponseDTO studentResponse = studentResponseDTOMapper.apply(studentToDelete.get());
            //TODO: Fjern student fra courses/house
            studentRepository.delete(studentToDelete.get());
            return Optional.of(studentResponse);
        }

        return Optional.empty();
    }

    public Optional<StudentResponseDTO> updateIfExists(int id, StudentRequestDTO student) {
        if (studentRepository.existsById(id)) {
            Student entity = studentRequestDTOMapper.apply(student);
            entity.setId(id);
            return Optional.of(studentResponseDTOMapper.apply(studentRepository.save(entity)));
        }
        return Optional.empty();
    }
}

