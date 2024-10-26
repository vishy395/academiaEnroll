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
    const semesterId = document.getElementById('semester').value;

    fetch(`http://localhost:8080/api/semesters/${semesterId}/courses`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(courses => {
            displayCourses(courses);
        })
        .catch(error => {
            console.error('Error fetching courses:', error);
        });
}

// Dynamically display courses and sections with faculty selection dropdowns
function displayCourses(courses) {
    const courseContainer = document.getElementById('course-container');
    courseContainer.innerHTML = '';

    courses.forEach(course => {
        if (course.type === 'core') {
            ['A', 'B', 'C'].forEach(sectionName => {
                const sectionDiv = document.createElement('div');
                sectionDiv.innerHTML = `
                    <h3>${course.name} - Section ${sectionName}</h3>
                    <label>Select Faculty for Section ${sectionName}:</label>
                    <select class="faculty-select" data-course-id="${course.id}" data-section="${sectionName}" data-capacity="50" data-class="${sectionName}" data-semester-id="1">
                        <option value="">--Select Faculty--</option>
                    </select>
                `;
                courseContainer.appendChild(sectionDiv);
            });
        } else if (course.type === 'elective') {
            const electiveDiv = document.createElement('div');
            electiveDiv.innerHTML = `
                <h3>${course.name} - Elective</h3>
                <label>Select Faculty for Elective:</label>
                <select class="faculty-select" data-course-id="${course.id}" data-section="Elective" data-capacity="30" data-class="Elective" data-semester-id="1">
                    <option value="">--Select Faculty--</option>
                </select>
            `;
            courseContainer.appendChild(electiveDiv);
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

function submitFacultyAssignment() {
    const selects = document.querySelectorAll('.faculty-select');
    const courseOfferings = [];

    // Iterate over each course selection and build the course offering object
    selects.forEach(select => {
        const facultyId = select.value;
        const courseOfferingId = select.getAttribute('data-course-id');
        const capacity = select.getAttribute('data-capacity');
        const className = select.getAttribute('data-class');
        const courseId = select.getAttribute('data-course-id');
        const academicSemester = select.getAttribute('data-semester-id');

        console.log(`Processing select with courseOfferingId: ${courseOfferingId}, facultyId: ${facultyId}`);

        if (facultyId) {
            // Build course offering object
            const offering = {
                id: parseInt(courseOfferingId),
                capacity: parseInt(capacity),
                className: className,
                courseID: parseInt(courseId),
                facultyID: parseInt(facultyId),
                studentIds: null,
                academicSemester: parseInt(academicSemester)
            };

            console.log("Adding Course Offering:", offering);  // Debugging log
            courseOfferings.push(offering);
        }
    });

    // Submit each course offering separately
    courseOfferings.forEach(offering => {
        fetch(`http://localhost:8080/api/courses/${offering.courseID}/offerings`, {  // Send one by one
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify([offering])  // Send each offering separately
        })
            .then(response => {
                if (response.ok) {
                    console.log(`Faculty assigned successfully for Course ID: ${offering.courseID}`);
                } else {
                    console.error(`Failed to assign faculty for Course ID: ${offering.courseID}`, response);
                }
            })
            .catch(error => {
                console.error(`Error assigning faculty for Course ID: ${offering.courseID}`, error);
            });
    });
}


// Event listener to trigger the submission when the "Add" button is clicked
document.getElementById('addButton').addEventListener('click', submitFacultyAssignment);

