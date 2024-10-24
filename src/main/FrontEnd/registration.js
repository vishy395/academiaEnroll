document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');
    const studentId = getStudentIdFromToken(token);  // Assume you decode token to get student ID

    fetchCoursesBySemester(studentId);
});

function getStudentIdFromToken(token) {
    const decodedToken = JSON.parse(atob(token.split('.')[1])); // Simplified JWT decode
    return decodedToken.id;  // Assuming student ID is stored in token
}

function fetchCoursesBySemester(studentId) {
    fetch(`http://localhost:8080/api/students/current/courses`, {
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

function displayCourses(courses) {
    const courseList = document.getElementById('course-list');
    courseList.innerHTML = '';

    courses.forEach(course => {
        const courseDiv = document.createElement('div');
        courseDiv.classList.add('course-item');

        const courseTitle = document.createElement('h3');
        courseTitle.innerText = course.name;
        courseDiv.appendChild(courseTitle);

        fetchCourseOfferings(course.id, courseDiv);
        courseList.appendChild(courseDiv);
    });
}

function fetchCourseOfferings(courseId, courseDiv) {
    fetch(`http://localhost:8080/api/courses/${courseId}/offerings`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(offerings => {
            offerings.forEach(offering => {
                const offeringDiv = document.createElement('div');
                offeringDiv.classList.add('offering-item');

                const offeringInfo = document.createElement('p');
                offeringInfo.innerText = `Course Offering: ${offering.className}`;
                offeringDiv.appendChild(offeringInfo);

                const enrollButton = document.createElement('button');

                // Fetch enrollment status from the backend for each offering
                fetch(`http://localhost:8080/api/courses/${offering.id}/isEnrolled`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => response.json())
                    .then(isEnrolled => {
                        // Set the button text based on enrollment status
                        enrollButton.innerText = isEnrolled ? 'Deroll' : 'Enroll';

                        // Set the action when the button is clicked
                        enrollButton.onclick = function() {
                            toggleEnrollment(offering.id, enrollButton);
                        };
                    })
                    .catch(error => {
                        console.error('Error fetching enrollment status:', error);
                    });

                offeringDiv.appendChild(enrollButton);
                courseDiv.appendChild(offeringDiv);
            });
        })
        .catch(error => {
            console.error('Error fetching course offerings:', error);
        });
}

function toggleEnrollment(courseOfferingId, button) {
    const action = button.innerText === 'Enroll' ? 'enroll' : 'deroll';

    fetch(`http://localhost:8080/api/courses/${action}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            courseOfferingId: courseOfferingId
        })
    })
        .then(response => {
            if (response.ok) {
                return response.text();  // Get the response text (success/failure message)
            } else {
                throw new Error('Enrollment action failed');
            }
        })
        .then(result => {
            if (result.includes('Enrollment successful')) {
                button.innerText = 'Deroll';
                alert('Enrollment successful!');
            } else if (result.includes('Successfully derolled')) {
                button.innerText = 'Enroll';
                alert('Successfully derolled!');
            } else {
                console.error('Unexpected response:', result);
                alert('Action failed: ' + result);
            }
        })
        .catch(error => {
            console.error('Error toggling enrollment:', error);
            alert('Error: ' + error.message);
        });
}





