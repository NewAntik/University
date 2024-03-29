package ua.foxminded.bootstrap.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.bootstrap.dao.CourseRepository;
import ua.foxminded.bootstrap.dao.GroupRepository;
import ua.foxminded.bootstrap.dao.RoomRepository;
import ua.foxminded.bootstrap.dao.TeacherRepository;
import ua.foxminded.bootstrap.dao.TimetableRepository;
import ua.foxminded.bootstrap.models.Course;
import ua.foxminded.bootstrap.models.Group;
import ua.foxminded.bootstrap.models.Room;
import ua.foxminded.bootstrap.models.Teacher;
import ua.foxminded.bootstrap.models.Timetable;
import ua.foxminded.bootstrap.service.TimetableService;


@SpringBootTest(classes = {TimetableServiceImpl.class})
class TimetableServiceImplTest {

    @MockBean
    TimetableRepository timetableRepository;
    @MockBean
    RoomRepository roomRepository;
    @MockBean
    GroupRepository groupRepository;
    @MockBean
    TeacherRepository teacherRepository;
    @MockBean
    CourseRepository courseRepository;

    @Autowired
    TimetableService timetableService;
    
    @Test
    void findByTeacherId_ShouldTrewIllegalArgumentExceptionReletionTeacherToScheduleDoesntExist() {
        when(timetableRepository.findByTeacherId(1L)).thenReturn(new ArrayList<>());

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.findByTeacherId(1L); 
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void findByStudentId_ShouldTrewIllegalArgumentExceptionReletionStudentToScheduleDoesntExist() {
        when(timetableRepository.findByStudentId(1L)).thenReturn(new ArrayList<>());

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.findByStudentId(1L); 
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void delete_ShouldDeleteTimetable() throws SQLException {
        when(timetableRepository.findById(1L)).thenReturn(Optional.of(new Timetable()));
        timetableService.delete(1L);
        
        verify(timetableRepository).deleteById(1L);
    }
    
    @Test
    void update_ShouldTrewIllegalArgumentExceptionReletionTeacherAlreadeExist() {
        when(timetableRepository.findByTeacherLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.update(1L, 6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void update_ShouldTrewIllegalArgumentExceptionReletionCourseAlreadeExist() {
        when(timetableRepository.findByCourseLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.update(1L, 6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void update_ShouldTrewIllegalArgumentExceptionReletionGroupAlreadeExist() {
        when(timetableRepository.findByGroupLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.update(1L, 6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void update_ShouldTrewIllegalArgumentExceptionReletionRoomAlreadeExist() {
        when(timetableRepository.findByRoomLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.update(1L, 6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void update_ShouldTrewIllegalArgumentExceptionRoomDoesntExist() {
        when(roomRepository.findById(7L)).thenReturn(Optional.empty());

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.update(1L, 6L, 1L, 1L, 1L, null, 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void update_ShouldTrewIllegalArgumentExceptionGroupDoesntExist() {
        when(groupRepository.findById(7L)).thenReturn(Optional.empty());
        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.update(1L, 6L, 1L, 1L, 1L, null, 1L);    
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void update_ShouldTrewIllegalArgumentExceptionTeacherDoesntExist() {
        when(teacherRepository.findById(7L)).thenReturn(Optional.empty());
        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.update(1L ,1L, 1L, 1L, 1L, null, 1L);    
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void update_ShouldUpdateTimetable() throws SQLException {
        when(roomRepository.findById(7L)).thenReturn(Optional.of(new Room(7L, "")));
        when(groupRepository.findById(7L)).thenReturn(Optional.of(new Group(7L, "", Collections.emptySet(), Collections.emptySet())));
        when(teacherRepository.findById(7L)).thenReturn(Optional.of(new Teacher(7L, "", "", "", "")));
        when(courseRepository.findById(7L)).thenReturn(Optional.of(new Course(7L, "", "")));

        when(timetableRepository.save(any())).thenAnswer(call -> call.getArgument(0, Timetable.class));

        Timetable actual = timetableService.add(7L, 7L, 7L, 7L, LocalDate.of(2023, 1, 1), 5L);
        assertEquals(actual.getTeacher().getId(), 7);
    }
    
    @Test
    void add_ShouldAddNewTimetable() throws SQLException {
        when(roomRepository.findById(7L)).thenReturn(Optional.of(new Room(7L, "")));
        when(groupRepository.findById(7L)).thenReturn(Optional.of(new Group(7L, "", Collections.emptySet(), Collections.emptySet())));
        when(teacherRepository.findById(7L)).thenReturn(Optional.of(new Teacher(7L, "", "", "", "")));
        when(courseRepository.findById(7L)).thenReturn(Optional.of(new Course(7L, "", "")));

        when(timetableRepository.save(any())).thenAnswer(call -> call.getArgument(0, Timetable.class));

        Timetable actual = timetableService.add(7L, 7L, 7L, 7L, LocalDate.of(2023, 1, 1), 5L);
        assertEquals(actual.getTeacher().getId(), 7);
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionReletionTeacherAlreadeExist() {
        when(timetableRepository.findByTeacherLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionReletionCourseAlreadeExist() {
        when(timetableRepository.findByCourseLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionReletionGroupAlreadeExist() {
        when(timetableRepository.findByGroupLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionReletionRoomAlreadeExist() {
        when(timetableRepository.findByRoomLessonData(1L, 1l, LocalDate.of(2023, 06, 22))).thenReturn(Optional.of(new Timetable()));

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(6L, 1L, 1L, 1L, LocalDate.of(2023, 06, 22), 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionRoomDoesntExist() {
        when(roomRepository.findById(7L)).thenReturn(Optional.empty());

        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(6L, 1L, 1L, 1L, null, 1L);  
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionGroupDoesntExist() {
        when(groupRepository.findById(7L)).thenReturn(Optional.empty());
        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(6L, 1L, 1L, 1L, null, 1L);    
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionTeacherDoesntExist() {
        when(teacherRepository.findById(7L)).thenReturn(Optional.empty());
        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(1L, 1L, 1L, 1L, null, 1L);    
        });
        assertNotNull(thrown.getMessage());
    }
    
    @Test
    void add_ShouldTrewIllegalArgumentExceptionCourseDoesntExist() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        Throwable thrown = assertThrows(IllegalArgumentException.class,() ->{
            timetableService.add(1L, 1L, 3L, 1L, null, 1L);     
        });
        assertNotNull( thrown.getMessage());
    }
}
