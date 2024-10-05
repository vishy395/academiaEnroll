-- Insert into the ACADEMIC_SEMESTER table
INSERT INTO academic_semester (id, start_date, end_date, semester)
VALUES (1, '2023-08', '2023-12', 1);

INSERT INTO academic_semester (id, start_date, end_date, semester)
VALUES (2, '2024-01', '2024-05', 2);
-- Insert into the COURSE table
INSERT INTO course (id, name, code, type, description)
VALUES (1, 'Data Structures', 'CS101', 'core', 'Introduction to data structures');

INSERT INTO course (id, name, code, type, description)
VALUES (2, 'Operating Systems', 'CS102', 'core', 'Introduction to operating systems');

INSERT INTO course (id, name, code, type, description)
VALUES (3, 'Web Development', 'CS103', 'elective', 'Introduction to web technologies');

-- Insert into people table first (base class)
INSERT INTO people (id, name, username, password)
VALUES (1, 'Dr. John Doe', 'jdoe', 'password123');

INSERT INTO faculty (id, title, experience)
VALUES (1, 'Professor', 10);

INSERT INTO people (id, name, username, password)
VALUES (2, 'Dr. Jane Smith', 'jsmith', 'password456');

INSERT INTO faculty (id, title, experience)
VALUES (2, 'Associate Professor', 8);

INSERT INTO people (id, name, username, password)
VALUES (3, 'Dr. Alan Turing', 'aturing', 'password789');

INSERT INTO faculty (id, title, experience)
VALUES (3, 'Assistant Professor', 5);

-- Insert into the PEOPLE table (for students)
-- Insert into people table first (base class)
INSERT INTO people (id, name, username, password)
VALUES (4, 'Alice Johnson', 'alice', 'alicepassword');

INSERT INTO student (id, academic_semester)
VALUES (4, 1);

INSERT INTO people (id, name, username, password)
VALUES (5, 'Bob Brown', 'bob', 'bobpassword');

INSERT INTO student (id, academic_semester)
VALUES (5, 2);


-- Insert into the COURSE_OFFERING table
INSERT INTO course_offering (id, capacity, class_name, courseid, facultyid, academic_semester)
VALUES (1, 50, 'A', 1, 1, 1);

INSERT INTO course_offering (id, capacity, class_name, courseid, facultyid, academic_semester)
VALUES (2, 60, 'B', 2, 2, 2);

INSERT INTO course_offering (id, capacity, class_name, courseid, facultyid, academic_semester)
VALUES (3, 40, 'C', 3, 3, 1);

-- For academic semester 1, add course IDs
INSERT INTO academic_semester_course_ids (academic_semester_id, course_ids)
VALUES (1, 1), (1, 2);

-- For academic semester 2, add course IDs
INSERT INTO academic_semester_course_ids (academic_semester_id, course_ids)
VALUES (2, 3);
