document.getElementById('enrollmentForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const courseOfferingId = document.getElementById('courseOfferingId').value;
    const token = localStorage.getItem('token');

    if (!token) {
        document.getElementById('enrollMessage').textContent = 'You are not logged in!';
        return;
    }

    const response = await fetch('http://localhost:8080/enrollments/enroll', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify({
            courseOfferingId: courseOfferingId
        })
    });

    const result = await response.text();

    if (response.ok) {
        document.getElementById('enrollMessage').textContent = 'Enrollment successful: ' + result;
    } else {
        document.getElementById('enrollMessage').textContent = 'Enrollment failed: ' + result;
    }
});
