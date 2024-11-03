document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');

    document.getElementById('view-history-btn').addEventListener('click', viewHistory);

    // Check if the registration is open
    checkRegistrationStatus().then(isOpen => {
        if (isOpen) {
            const studentId = getStudentIdFromToken(token);  // Assume you decode token to get student ID
            fetchCoursesBySemester(studentId);
        } else {
            displayClosedRegistrationMessage();
        }
    });
});

// Function to fetch and display enrollment history
function viewHistory() {
    fetch('http://localhost:8080/api/courses/enrollment/history', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch enrollment history');
            }
            return response.json();
        })
        .then(history => {
            displayHistory(history);
        })
        .catch(error => {
            console.error('Error fetching enrollment history:', error);
        });
}

// Function to display enrollment history
function displayHistory(history) {
    const historyList = document.getElementById('history-list');
    historyList.innerHTML = '<h2>Enrollment History</h2>';

    history.forEach(entry => {
        const historyItem = document.createElement('div');
        historyItem.classList.add('history-item');
        historyItem.innerHTML = `<p>Course Offering ID: ${entry.courseOfferingId}, Action: ${entry.action}, Timestamp: ${new Date(entry.timestamp).toLocaleString()}</p>`;
        historyList.appendChild(historyItem);
    });
}

// Function to check if registration is open
function checkRegistrationStatus() {
    return fetch('http://localhost:8080/api/registration/status', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch registration status');
            }
            return response.json(); // Return the boolean status
        })
        .catch(error => {
            console.error('Error checking registration status:', error);
            alert('Unable to check registration status. Please try again later.');
            return false; // Default to false if there's an error
        });
}

// Function to get student ID from token
function getStudentIdFromToken(token) {
    const decodedToken = JSON.parse(atob(token.split('.')[1])); // Simplified JWT decode
    return decodedToken.id;  // Assuming student ID is stored in token
}

// Function to fetch courses for the student
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

// Function to display a message if registration is closed
function displayClosedRegistrationMessage() {
    const courseList = document.getElementById('course-list');
    courseList.innerHTML = '<p>Course registration is currently closed. Please check back later.</p>';
}

// Function to display courses
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

// Function to fetch course offerings
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

                // Fetch enrollment status for each offering
                fetch(`http://localhost:8080/api/courses/${offering.id}/isEnrolled`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('token')}`,
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => response.json())
                    .then(isEnrolled => {
                        // Set button text based on enrollment status
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

// Function to toggle enrollment
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
