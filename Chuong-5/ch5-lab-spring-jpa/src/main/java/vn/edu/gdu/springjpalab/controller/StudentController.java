package vn.edu.gdu.springjpalab.controller;

import vn.edu.gdu.springjpalab.entity.Course;
import vn.edu.gdu.springjpalab.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.gdu.springjpalab.entity.Student;
import vn.edu.gdu.springjpalab.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private  final  CourseRepository courseRepository;

    public StudentController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // 1. Lấy danh sách tất cả sinh viên
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 2. Lấy sinh viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);

        return student
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Thêm sinh viên mới
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student) {

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    // 4. Cập nhật sinh viên
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student studentDetails) {

        Optional<Student> existingStudent =
                studentRepository.findById(id);

        if (existingStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = existingStudent.get();

        student.setStudentCode(studentDetails.getStudentCode());
        student.setFullName(studentDetails.getFullName());
        student.setEmail(studentDetails.getEmail());
        student.setGpa(studentDetails.getGpa());
        student.setEnrollmentDate(studentDetails.getEnrollmentDate());

        Student updatedStudent =
                studentRepository.save(student);

        return ResponseEntity.ok(updatedStudent);
    }

    // 5. Xóa sinh viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // 6. Đếm tổng số sinh viên
    @GetMapping("/count")
    public long countStudents() {
        return studentRepository.count();
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<String> enrollStudentInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        var studentOptional = studentRepository.findById(studentId);
        var courseOptional = courseRepository.findById(courseId);

        if (studentOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy sinh viên ID: " + studentId);
        }

        if (courseOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy khóa học ID: " + courseId);
        }

        var student = studentOptional.get();
        var course = courseOptional.get();

        student.enrollInCourse(course);
        studentRepository.save(student);

        return ResponseEntity.ok(
                "Đăng ký khóa học thành công. Student ID: "
                        + studentId
                        + ", Course ID: "
                        + courseId
        );
    }
}