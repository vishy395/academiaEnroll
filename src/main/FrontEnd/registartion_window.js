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
        if (course.type === 'class') {
            ['A', 'B', 'C'].forEach(sectionName => {
                const sectionDiv = document.createElement('div');
                sectionDiv.innerHTML = `
                    <h3>${course.name} - Section ${sectionName}</h3>
                    <label>Select Faculty for Section ${sectionName}:</label>
                    <select class="faculty-select" data-course-id="${course.id}" data-section="${sectionName}">
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
                <select class="faculty-select" data-course-id="${course.id}" data-section="Elective">
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

// Submit faculty assignments in a batch
function submitFacultyAssignment() {
    const selects = document.querySelectorAll('.faculty-select');
    const courseOfferings = [];

    // Iterate over each course selection and build the course offering object
    selects.forEach(select => {
        const courseOfferingId = select.getAttribute('data-course-id');
        const facultyId = select.value;


        const capacity = select.getAttribute('data-capacity');
        const className = select.getAttribute('data-class');
        const courseId = select.getAttribute('data-course-id');
        const academicSemester = select.getAttribute('data-semester-id');

        if (facultyId) {

            const offering = {
                id: parseInt(courseOfferingId),
                capacity: parseInt(capacity),
                className: className,
                courseID: parseInt(courseId),
                facultyID: parseInt(facultyId),
                studentIds: null,
                academicSemester: parseInt(academicSemester)
            };

            courseOfferings.push(offering);
        }
    });

    if (courseOfferings.length > 0) {
        // Send the entire array of course offerings in a single POST request
        fetch(`http://localhost:8080/api/courses/${courseOfferings[0].courseID}/offerings`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(courseOfferings)  // Send the course offerings array as JSON
        })
            .then(response => {
                if (response.ok) {
                    alert('Faculty assigned successfully!');
                } else {
                    alert('Failed to assign faculty.');
                }
            })
            .catch(error => {
                console.error('Error assigning faculty:', error);
            });
    } else {
        alert('No faculty assignments were made.');
    }
}

// Event listener to trigger the submission when the "Add" button is clicked
document.getElementById('addButton').addEventListener('click', submitFacultyAssignment);

