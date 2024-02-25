package kea.exercise.studentadmin.services;

import kea.exercise.studentadmin.dtos.teacher.TeacherRequestDTO;
import kea.exercise.studentadmin.dtos.teacher.TeacherRequestDTOMapper;
import kea.exercise.studentadmin.dtos.teacher.TeacherResponseDTO;
import kea.exercise.studentadmin.dtos.teacher.TeacherResponseDTOMapper;
import kea.exercise.studentadmin.models.Teacher;
import kea.exercise.studentadmin.repositories.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherResponseDTOMapper teacherResponseDTOMapper;
    private final TeacherRequestDTOMapper teacherRequestDTOMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherResponseDTOMapper teacherResponseDTOMapper, TeacherRequestDTOMapper teacherRequestDTOMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherResponseDTOMapper = teacherResponseDTOMapper;
        this.teacherRequestDTOMapper = teacherRequestDTOMapper;
    }

    public List<TeacherResponseDTO> findAll() {
        return teacherRepository.findAll().stream().map(teacherResponseDTOMapper).toList();
    }

    public Optional<TeacherResponseDTO> findById(Long id) {
        return teacherRepository.findById(id).map(teacherResponseDTOMapper);
    }

    public TeacherResponseDTO save(TeacherRequestDTO teacher) {
        Teacher newTeacher = teacherRequestDTOMapper.apply(teacher);
        teacherRepository.save(newTeacher);
        return teacherResponseDTOMapper.apply(newTeacher);
    }

    public Optional<TeacherResponseDTO> updateIfExists(Long id, TeacherRequestDTO teacher) {
        if (teacherRepository.existsById(id)) {
            Teacher entity = teacherRequestDTOMapper.apply(teacher);
            entity.setId(id);
            teacherRepository.save(entity);
            return Optional.of(teacherResponseDTOMapper.apply(entity));
        }
        return Optional.empty();
    }

    public Optional<TeacherResponseDTO> deleteById(Long id) {
        Optional<Teacher> teacherToDelete = teacherRepository.findById(id);
        if (teacherToDelete.isPresent()) {
            TeacherResponseDTO teacherResponse = teacherResponseDTOMapper.apply(teacherToDelete.get());
            teacherRepository.delete(teacherToDelete.get());
            return Optional.of(teacherResponse);
        }
        return Optional.empty();
    }

    public Optional<TeacherResponseDTO> updateTeacherByFields(Long id, Map<String, Object> fields) {
        Optional<Teacher> teacherToUpdate = teacherRepository.findById(id);
        teacherToUpdate.ifPresent(teacher -> fields.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(Teacher.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, teacher, value);
            }
        }));

        return teacherToUpdate.map(teacherRepository::save).map(teacherResponseDTOMapper);
    }
}
