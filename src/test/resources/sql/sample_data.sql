INSERT INTO courses (id, name, description)
VALUES (100, 'Math', 'Math Description'),
       (101, 'Biology', 'Biology Description'),
       (102, 'Geography', 'Geography Description'),
       (103, 'Music', 'Music Description'),
       (104, 'History', 'History Description'),
       (105, 'Physics', 'Physics Description'),
       (106, 'Medicine', 'Medicine Description'),
       (7, 'Tehnology', 'Tehnology Description');

INSERT INTO rooms (id, name)
VALUES (1, 'Lecture hall #1'),
       (2, 'Lecture hall #5'),
       (3, 'Lecture hall #6'),
       (4, 'Lecture hall #9'),
       (5, 'Lecture hall #13'),
       (7, 'Lecture hall #17');

INSERT INTO groups (id, name)
VALUES (1, 'AA-01'),
       (2, 'AA-02'),
       (3, 'AA-03'),
       (4, 'AA-04'),
       (5, 'AA-05'),
       (7, 'AA-05');
       
INSERT INTO users (id, login, password, role_type, role, group_ref, first_name, last_name)
VALUES (1, 'admin', '1234', 'admin', 'ADMIN', null, 'Admin', 'Admin'),
       (2, 'student1', '1234', 'student', 'STUDENT', 1, 'Jon', 'Cover'),
       (3, 'teacher1', '1234', 'teacher', 'TEACHER', null, 'Ann', 'Pinkerton'),
       (4, 'student2', '1234', 'student', 'STUDENT', 1, 'Scott', 'Swann'),
       (5, 'teacher3', '1234', 'teacher', 'TEACHER', null, 'Yudong', 'Tang'),
       (7, 'teacher7', '1237', 'teacher', 'TEACHER', null, 'Makama', 'Roy');
       
INSERT INTO timetables (id, room_ref, course_ref, group_ref, teacher_ref, date_ref, lesson_num)
VALUES (1, 1, 100, 1, 3, '2023-06-22', 5),
       (2, 2, 101, 1, 3, '2023-06-21', 4),
       (3, 3, 102, 3, 7, '2023-06-23', 3);


INSERT INTO teachers_courses (teacher_ref, course_ref)
VALUES (3, 100),
       (3, 101),
       (3, 103),
       (5, 100),
       (5, 102);

INSERT INTO courses_groups (group_ref, course_ref)
VALUES (1, 100),
       (1, 101),
       (2, 100),
       (2, 102),
       (3, 101);