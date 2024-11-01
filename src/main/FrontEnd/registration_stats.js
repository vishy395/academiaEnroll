// Fetch and populate semesters on page load
window.onload = function() {
    fetch('http://localhost:8080/api/semesters', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to fetch semesters");
            return response.json();
        })
        .then(semesters => {
            const semesterSelect = document.getElementById('semester');
            semesters.forEach(semester => {
                const option = document.createElement('option');
                option.value = semester.id;
                option.textContent = semester.semester;
                semesterSelect.appendChild(option);
            });

            // Fetch offerings when a semester is selected
            semesterSelect.addEventListener('change', fetchCourseOfferingsBySemester);
        })
        .catch(error => console.error("Error fetching semesters:", error));
};

// Fetch course offerings for the selected semester
function fetchCourseOfferingsBySemester() {
    const semesterId = document.getElementById('semester').value;

    // Ensure a semester is selected
    if (!semesterId) return;

    fetch(`http://localhost:8080/api/courseOfferings/bySemester/${semesterId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error("Failed to fetch course offerings");
            return response.json();
        })
        .then(offerings => {
            const container = document.getElementById('course-offerings');
            container.innerHTML = ''; // Clear previous data

            offerings.forEach(offering => {
                const div = document.createElement('div');
                div.textContent = `Course ID: ${offering.courseID}, Faculty: ${offering.facultyName}, Class: ${offering.className}, Students: ${offering.studentCount}`;
                container.appendChild(div);
            });
        })
        .catch(error => console.error("Error fetching course offerings:", error));
}

// Export data to CSV or TXT
function exportData(format) {
    const semesterId = document.getElementById('semester').value;

    // Ensure a semester is selected before exporting
    if (!semesterId) {
        alert("Please select a semester before exporting.");
        return;
    }

    // Trigger download by redirecting to the export URL
    window.location.href = `http://localhost:8080/api/courseOfferings/export?semester=${semesterId}&format=${format}`;
}
