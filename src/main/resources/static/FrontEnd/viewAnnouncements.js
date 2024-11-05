$(document).ready(function () {
    const token = localStorage.getItem('token');

    if (!token) {
        alert('You are not logged in. Please log in to continue.');
        window.location.href = 'login.html';
        return;
    }

    function fetchUserDetails(userId) {
        return fetch(`http://localhost:8080/api/users/${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch user details');
                return response.json();
            })
            .then(user => user.name)
            .catch(error => {
                console.error('Error fetching user details:', error);
                return "Unknown Faculty"; // Fallback in case of an error
            });
    }

    function loadAnnouncements() {
        fetch('http://localhost:8080/api/announcements', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch announcements');
                return response.json();
            })
            .then(announcements => {
                $('#announcementsList').empty();
                announcements.forEach(announcement => {
                    fetchUserDetails(announcement.sender)
                        .then(senderName => {
                            $('#announcementsList').append(`
                                <div class="message">
                                    <p><strong>Announcement:</strong> ${announcement.content}</p>
                                    <em>On ${new Date(announcement.timestamp).toLocaleString()}</em>
                                    <em>By ${senderName}</em>
                                    <em>For Semester ${announcement.academicSemester}</em>
                                </div>
                            `);
                        });
                });
            })
            .catch(error => {
                $('#errorMessage').text("Failed to load announcements. Try again later.");
                console.error('Error loading announcements:', error);
            });
    }

    loadAnnouncements();
});
