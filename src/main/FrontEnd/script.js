const loginForm = document.getElementById('loginForm');

loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();  // Prevent the default form submission behavior

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const loginData = {
        username: username,
        password: password
    };

    try {
        const response = await fetch('http://localhost:8080/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData),
            credentials: 'include'
        });

        if (response.ok) {
            const token = await response.text(); // Assuming JWT token is returned as plain text
            localStorage.setItem('token', token);  // Save token in localStorage
            window.location.href = 'enrollment.html';  // Redirect to enrollment page
        } else {
            alert('Login failed! Please check your credentials.');
        }
    } catch (error) {
        console.error('Error during login:', error);
        alert('An error occurred during login.');
    }
});
