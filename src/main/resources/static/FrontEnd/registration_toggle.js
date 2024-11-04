// On page load, fetch the current status
window.onload = function() {
    updateToggleButton();
};

// Function to fetch and display current status on the button
function updateToggleButton() {
    fetch('http://localhost:8080/api/registration/status', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(isOpen => updateButtonText(isOpen))
        .catch(error => console.error("Error fetching registration status:", error));
}

// Function to toggle registration and update button text
function toggleRegistration() {
    fetch('http://localhost:8080/api/registration/status?toggle=true', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(isOpen => updateButtonText(isOpen))
        .catch(error => console.error("Error toggling registration:", error));
}

// Helper function to update button text based on registration status
function updateButtonText(isOpen) {
    const button = document.getElementById('toggle-registration');
    button.textContent = isOpen ? 'Close Registration' : 'Open Registration';
}
