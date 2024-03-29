package ua.foxminded.bootstrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.bootstrap.models.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.name = :name")
    Course findByName(String name);

    @Query("SELECT c FROM Course c left JOIN fetch c.groups g left JOIN fetch g.students s WHERE s.id = :id")
    List<Course> findByStudentId(Long id);
    
    @Query("SELECT c FROM Course c JOIN c.teachers t WHERE t.id =:teacherId AND c.id =:courseId")
    Optional<Course> findRelationTeacherCourse(@Param("teacherId") Long teacherId, @Param("courseId") Long courseId);
    
    @Query("SELECT c FROM Course c JOIN c.groups g WHERE g.id =:groupId AND c.id =:courseId")
    Optional<Course> findRelationGroupCourse(@Param("groupId") Long groupId, @Param("courseId") Long courseId);
    
    @Query("SELECT c FROM Course c JOIN c.teachers t WHERE t.id =:id ORDER BY c.id")
    List<Course> findByTeacherId(@Param("id") Long teacherId);
}
