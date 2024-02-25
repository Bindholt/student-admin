package kea.exercise.studentadmin.services;

import kea.exercise.studentadmin.dtos.course.CourseRequestDTO;
import kea.exercise.studentadmin.dtos.course.CourseRequestDTOMapper;
import kea.exercise.studentadmin.dtos.course.CourseResponseDTO;
import kea.exercise.studentadmin.dtos.course.CourseResponseDTOMapper;
import kea.exercise.studentadmin.dtos.student.StudentResponseDTO;
import kea.exercise.studentadmin.dtos.student.StudentResponseDTOMapper;
import kea.exercise.studentadmin.dtos.student.request.StudentDTOIds;
import kea.exercise.studentadmin.dtos.student.request.StudentDTONames;
import kea.exercise.studentadmin.dtos.teacher.request.TeacherDTOId;
import kea.exercise.studentadmin.dtos.teacher.TeacherResponseDTO;
import kea.exercise.studentadmin.dtos.teacher.TeacherResponseDTOMapper;
import kea.exercise.studentadmin.models.Course;
import kea.exercise.studentadmin.models.Student;
import kea.exercise.studentadmin.models.Teacher;
import kea.exercise.studentadmin.repositories.CourseRepository;
import kea.exercise.studentadmin.repositories.StudentRepository;
import kea.exercise.studentadmin.repositories.TeacherRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseResponseDTOMapper courseResponseDTOMapper;
    private final CourseRequestDTOMapper courseRequestDTOMapper;
    private final TeacherResponseDTOMapper teacherResponseDTOMapper;
    private final StudentResponseDTOMapper studentResponseDTOMapper;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository, CourseResponseDTOMapper courseResponseDTOMapper, CourseRequestDTOMapper courseRequestDTOMapper, TeacherResponseDTOMapper teacherResponseDTOMapper, StudentResponseDTOMapper studentResponseDTOMapper, kea.exercise.studentadmin.repositories.TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.courseResponseDTOMapper = courseResponseDTOMapper;
        this.courseRequestDTOMapper = courseRequestDTOMapper;
        this.teacherResponseDTOMapper = teacherResponseDTOMapper;
        this.studentResponseDTOMapper = studentResponseDTOMapper;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }


    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll().stream().map(courseResponseDTOMapper).toList();
    }

    public Optional<CourseResponseDTO> findById(Long id) {
        return courseRepository.findById(id).map(courseResponseDTOMapper);
    }

    public Optional<TeacherResponseDTO> findTeacherByCourseId(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Teacher teacher = course.get().getTeacher();
            if (teacher != null) {
                TeacherResponseDTO teacherResponseDTO = teacherResponseDTOMapper.apply(teacher);
                return Optional.of(teacherResponseDTO);
            }
        }
        return Optional.empty();
    }

    public Optional<List<StudentResponseDTO>> findStudentsByCourseId(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            List<StudentResponseDTO> studentResponseDTOS = course.get().getStudents().stream().map(studentResponseDTOMapper).toList();
            if(!studentResponseDTOS.isEmpty()) {
                return Optional.of(studentResponseDTOS);
            }
        }
        return Optional.empty();
    }

    public CourseResponseDTO save(CourseRequestDTO course) {
        Course newCourse = courseRequestDTOMapper.apply(course);
        courseRepository.save(newCourse);
        return courseResponseDTOMapper.apply(newCourse);
    }

    public Optional<CourseResponseDTO> updateIfExists(Long id, CourseRequestDTO course) {
        if (courseRepository.existsById(id)) {
            Course courseToUpdate = courseRequestDTOMapper.apply(course);
            courseToUpdate.setId(id);
            return Optional.of(courseResponseDTOMapper.apply(courseRepository.save(courseToUpdate)));
        }
        return Optional.empty();
    }

    public Optional<CourseResponseDTO> updateTeacher(Long id, TeacherDTOId teacherId) {
        Optional<Course> courseToUpdate = courseRepository.findById(id);
        if(courseToUpdate.isPresent()) {
            Optional<Teacher> teacher = teacherRepository.findById(teacherId.id());
            if(teacher.isPresent()) {
                Course updatedCourse = courseToUpdate.get();
                updatedCourse.setTeacher(teacher.get());
                courseRepository.save(updatedCourse);
                return Optional.of(courseResponseDTOMapper.apply(updatedCourse));
            }
        }
        return Optional.empty();
    }

    public Optional<CourseResponseDTO> addStudentsToCourse(Long id, StudentDTOIds studentIds) {
        Optional<Course> courseToUpdate = courseRepository.findById(id);
        courseToUpdate.ifPresent(course -> {
            studentIds.ids().forEach(sId -> {
                Optional<Student> studentToAdd = studentRepository.findById(sId);

                    studentToAdd.ifPresent(student -> {
                        if (isMatchingSchoolYear(course, student)) {
                            course.addStudent(student);
                        }
                    });
            });
        });
        return courseToUpdate.map(courseRepository::save).map(courseResponseDTOMapper);
    }

    public Optional<CourseResponseDTO> addStudentsToCourseByNames(Long id, StudentDTONames studentNames) {
        Optional<Course> courseToUpdate = courseRepository.findById(id);

            courseToUpdate.ifPresent(course -> {
                studentNames.names().forEach(name -> {
                    Student studentName = new Student(name);
                    Optional<Student> studentToAdd = studentRepository.findFirstByFirstNameContainingOrLastNameContaining(studentName.getFirstName(), studentName.getLastName());
                    studentToAdd.ifPresent(student -> {
                        if (isMatchingSchoolYear(course, student)) {
                            course.addStudent(student);
                        } else {
                            throw new RuntimeException("Can not assign student with id " + student.getId() + " with course because student schoolyear differs from course schoolYear");
                        }
                    });
                });
            });
        return courseToUpdate.map(courseRepository::save).map(courseResponseDTOMapper);
    }

    private boolean isMatchingSchoolYear(Course course, Student student) {
        return course.getSchoolYear() == student.getSchoolYear();
    }

    public Optional<CourseResponseDTO> deleteById(Long id) {
        Optional<Course> courseToDelete = courseRepository.findById(id);
        courseToDelete.ifPresent(courseRepository::delete);
        return courseToDelete.map(courseResponseDTOMapper);
    }

    public Optional<CourseResponseDTO> removeTeacherFromCourse(Long id) {
        Optional<Course> courseToUpdate = courseRepository.findById(id);
        courseToUpdate.ifPresent(course -> {
            course.setTeacher(null);
            courseRepository.save(course);
        });
        return courseToUpdate.map(courseResponseDTOMapper);
    }

    public Optional<CourseResponseDTO> removeStudentsFromCourse(Long id) {
        Optional<Course> courseToUpdate = courseRepository.findById(id);
        courseToUpdate.ifPresent(course -> {
            course.getStudents().clear();
            courseRepository.save(course);
        });
        return courseToUpdate.map(courseResponseDTOMapper);
    }
}
