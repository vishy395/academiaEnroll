$(document).ready(function () {
    const token = localStorage.getItem('token');

    if (!token) {
        alert('You are not logged in. Please log in to continue.');
        window.location.href = 'login.html';
        return;
    }

    function loadSemesters() {
        fetch('http://localhost:8080/api/semesters', {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(semesters => {
                semesters.forEach(semester => {
                    $('#semesterSelect').append(`<option value="${semester.id}">${semester.semester}</option>`);
                });
            })
            .catch(error => console.error('Error loading semesters:', error));
    }

    $('#sendAnnouncementButton').on('click', function () {
        const content = $('#announcementContent').val();
        const semesterId = $('#semesterSelect').val();

        if (!content.trim() || !semesterId) {
            alert('Both announcement content and semester must be selected.');
            return;
        }

        const announcement = {
            content: content,
            academicSemester: semesterId
        };

        fetch('http://localhost:8080/api/announcements', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(announcement)
        })
            .then(response => {
                if (!response.ok) throw new Error('Failed to send announcement');
                return response.json();
            })
            .then(() => {
                $('#responseMessage').text("Announcement created successfully!");
                $('#announcementContent').val('');
            })
            .catch(error => {
                $('#responseMessage').text("Error creating announcement. Try again.");
                console.error('Error creating announcement:', error);
            });
    });

    loadSemesters(); // Load semesters on page load
});
