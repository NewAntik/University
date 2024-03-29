package ua.foxminded.bootstrap.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.bootstrap.models.Course;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CourseRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;
    
    @Test
    void findRelationGroupCourse_ShouldReturnCourseReletedToGroup() {
        Optional<Course> course = courseRepository.findRelationGroupCourse(1L, 100L);
        assertTrue(course.get().getGroups().stream().anyMatch(group -> group.getId().equals(1L)));
    }
    
    @Test
    void findRelationTeacherCourse_ShouldReturnCourseReletedToTeacher() {
        Optional<Course> course = courseRepository.findRelationTeacherCourse(3L, 100L);
        assertTrue(course.get().getTeachers().stream().anyMatch(teacher -> teacher.getId().equals(3L)));
    }
    
    @Test
    void findByTeacherID_ShouldReturnCourseListOfCourseReletedWithTeacherByTeacherId() {
        List<Course> expected = List.of(
                new Course(100L, "Math", "Math Description"),
                new Course(101L, "Biology", "Biology Description"),
                new Course(103L, "Music", "Music Description")
        );
        
        List<Course> actual = courseRepository.findByTeacherId(3L);
        assertEquals(expected, actual);
    }

    @Test
    void findByStudentId_ShouldReturnListOfCourseReletedWithStudentByStudentIdOrderedById() {
        List<Course> expected = List.of(
                new Course(100L, "Math", "Math Description"),
                new Course(101L, "Biology", "Biology Description")
        );

        List<Course> actual = courseRepository.findByStudentId(4L);
        assertEquals(expected, actual);
    }

    @Test
    void findByName_ShouldReturnCourseByName() {
        Course medicine = courseRepository.findByName("Medicine");
        assertEquals("Medicine", medicine.getName());
        assertEquals(106L, medicine.getId());
    }
}
