document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

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
            localStorage.setItem('token', token);

            // Decode the token manually to extract the role
            const decodedToken = decodeJWT(token);
            const userRole = decodedToken.role;  // Assuming the role is stored as 'role'

            // Redirect based on role
            if (userRole === 'ROLE_STUDENT') {
                window.location.href = 'student-home.html';
            } else if (userRole === 'ROLE_FACULTY') {
                window.location.href = 'faculty-home.html';
            } else {
                document.getElementById("loginMessage").innerText = "Invalid role!";
            }
        } else {
            alert('Login failed! Please check your credentials.');
        }
    } catch (error) {
        console.error('Error during login:', error);
        alert('An error occurred during login.');
    }
});

// Function to manually decode JWT
function decodeJWT(token) {
    // Split the token into its 3 parts (header, payload, signature)
    const tokenParts = token.split('.');

    // Base64 decode the payload (2nd part of the JWT)
    const base64Payload = tokenParts[1];

    // Decode Base64 and parse JSON
    const payload = atob(base64Payload);
    return JSON.parse(payload);
}
