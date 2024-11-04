let selectedAcademicSemester = null;

// Fetch available academic semesters and populate dropdown
window.onload = function() {
    fetch('http://localhost:8080/api/semesters', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(semesters => {
            const semesterSelect = document.getElementById('semester');
            semesters.forEach(semester => {
                const option = document.createElement('option');
                option.value = semester.id;
                option.innerText = semester.semester;
                semesterSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching semesters:', error);
        });
};

// Fetch courses for the selected semester
function fetchCoursesBySemester() {
    selectedAcademicSemester = document.getElementById('semester').value;

    fetch(`http://localhost:8080/api/courses/byAcademicSemester/${selectedAcademicSemester}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(courses => {
            console.log('Fetched courses:', courses);
            displayCourses(courses);
        })
        .catch(error => {
            console.error('Error fetching courses:', error);
        });
}

// Dynamically display courses and sections with faculty selection dropdowns
function displayCourses(courses) {
    const courseContainer = document.getElementById('course-container');
    courseContainer.innerHTML = ''; // Clear the existing content

    courses.forEach(course => {
        console.log(course); // Log each course to inspect its data
        if (course.type === 'core') {
            ['A', 'B', 'C'].forEach(sectionName => {
                const sectionDiv = document.createElement('div');
                sectionDiv.innerHTML = `
                <h3>${course.name} - Section ${sectionName}</h3>
                <label>Select Faculty for Section ${sectionName}:</label>
                <select class="faculty-select" data-course-id="${course.id}" data-section="${sectionName}" data-capacity="50">
                    <option value="">--Select Faculty--</option>
                </select>
                <button class="add-button" onclick="submitFacultyAssignment(this, '${course.id}', '${sectionName}')">Add</button>
            `;
                courseContainer.appendChild(sectionDiv);
            });
        } else if (course.type === 'elective') {
            // Handle elective courses
            console.log("Displaying elective:", course.name);
            const sectionDiv = document.createElement('div');
            sectionDiv.innerHTML = `
            <h3>${course.name} - Elective</h3>
            <label>Select Faculty for Section Elective:</label>
            <select class="faculty-select" data-course-id="${course.id}" data-section="elective" data-capacity="50">
                <option value="">--Select Faculty--</option>
            </select>
            <button class="add-button" onclick="submitFacultyAssignment(this, '${course.id}', 'elective')">Add</button>
        `;
            courseContainer.appendChild(sectionDiv);
        } else {
            console.warn("Unexpected course type:", course.type);
        }
    });

    fetchFacultyList(); // Fetch and populate faculty dropdown
}

// Fetch faculty list and populate dropdowns
function fetchFacultyList() {
    fetch('http://localhost:8080/api/faculty', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(faculty => {
            document.querySelectorAll('.faculty-select').forEach(select => {
                faculty.forEach(member => {
                    const option = document.createElement('option');
                    option.value = member.id;
                    option.innerText = member.name;
                    select.appendChild(option);
                });
            });
        })
        .catch(error => {
            console.error('Error fetching faculty list:', error);
        });
}

// Submit the selected faculty and course assignment
function submitFacultyAssignment(button, courseId, section) {
    const select = button.previousElementSibling;
    const facultyId = select.value;

    if (!facultyId) {
        alert("Please select a faculty member before submitting.");
        return;
    }

    const capacity = select.getAttribute('data-capacity');
    const courseOffering = {
        capacity: parseInt(capacity),
        className: section,
        courseID: parseInt(courseId),
        facultyID: parseInt(facultyId),
        studentIds: null,
        academicSemester: parseInt(selectedAcademicSemester)
    };

    fetch(`http://localhost:8080/api/courses/${courseId}/offerings`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify([courseOffering])
    })
        .then(response => {
            if (response.ok) {
                console.log(`Faculty assigned successfully for Course ID: ${courseId}, Section: ${section}`);
                button.innerHTML = '✔';
                button.classList.add('added');
                button.disabled = true;
            } else {
                console.error(`Failed to assign faculty for Course ID: ${courseId}`, response);
            }
        })
        .catch(error => {
            console.error('Error assigning faculty:', error);
        });
}
