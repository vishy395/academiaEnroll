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
INSERT INTO people (id, name, username, password,role)
VALUES (1, 'Dr. John Doe', 'jdoe', '$2b$12$3bJkV1/QL9QwCvS740D8AeQ.kA6oUr.2nfoK/vY45eyiwc1TlKO/W','ROLE_FACULTY');

INSERT INTO faculty (id, title, experience)
VALUES (1, 'Professor', 10);

INSERT INTO people (id, name, username, password,role)
VALUES (2, 'Dr. Jane Smith', 'jsmith', '$2b$12$aZiiRo6Sd0j49NNmoU6Xme9tdfHXFkjpujvMyGjeWizSFA.MKQzRa','ROLE_FACULTY');

INSERT INTO faculty (id, title, experience)
VALUES (2, 'Associate Professor', 8);

INSERT INTO people (id, name, username, password,role)
VALUES (3, 'Dr. Alan Turing', 'aturing', '$2b$12$2qMVLGytPpkr60B7A88JE.5S.3bKlc3HggLncx0hQaWO/Z1LnUwtK','ROLE_FACULTY');

INSERT INTO faculty (id, title, experience)
VALUES (3, 'Assistant Professor', 5);

-- Insert into the PEOPLE table (for students)
-- Insert into people table first (base class)
INSERT INTO people (id, name, username, password,role)
VALUES (4, 'Alice Johnson', 'alice', '$2b$12$DNTkUIeYLF0/K/pIFMnE6uQSOFf5GnxjbG5/KBoe/8D8pCpZ2tYsq','ROLE_STUDENT');

INSERT INTO student (id, academic_semester)
VALUES (4, 1);

INSERT INTO people (id, name, username, password,role)
VALUES (5, 'Bob Brown', 'bob', '$2b$12$FwyOMaZpztYaErRITBvEr.4CR2cpRrIAe3V1UU0LYsQlqeL06HbmS','ROLE_STUDENT');

INSERT INTO student (id, academic_semester)
VALUES (5, 2);


-- Insert into the COURSE_OFFERING table
--INSERT INTO course_offering (id, capacity, class_name, courseid, facultyid, academic_semester)
--VALUES (1, 50, 'A', 1, 1, 1);

--INSERT INTO course_offering (id, capacity, class_name, courseid, facultyid, academic_semester)
--VALUES (2, 60, 'B', 2, 2, 2);

--INSERT INTO course_offering (id, capacity, class_name, courseid, facultyid, academic_semester)
--VALUES (3, 40, 'C', 3, 3, 1);

-- For academic semester 1, add course IDs
INSERT INTO academic_semester_course_ids (academic_semester_id, course_ids)
VALUES (1, 1), (1, 2), (1, 3);

-- For academic semester 2, add course IDs


INSERT INTO academic_semester_course_ids (academic_semester_id, course_ids)
VALUES (2, 1);

INSERT INTO academic_semester_course_ids (academic_semester_id, course_ids)
VALUES (2, 2);

INSERT INTO academic_semester_course_ids (academic_semester_id, course_ids)
VALUES (2, 3);