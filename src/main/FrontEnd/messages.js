document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');

    if (!token) {
        alert('You are not logged in. Please log in to continue.');
        window.location.href = 'login.html';
        return;
    }

    loadMessages();
    loadReceivers(); // Load available receivers

    $('#sendMessageButton').on('click', async function() {
        const content = $('#messageContent').val();
        const senderId = await getSenderIdFromToken(token);
        console.log('Sender ID from token:', senderId);
        const receiverId = $('#receiverSelect').val();
        console.log('receiverId ID from token:', receiverId);
        sendMessage(senderId, receiverId, content);
    });
});

// Function to load messages
function loadMessages() {
    fetch('http://localhost:8080/api/messages', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch messages');
            return response.json();
        })
        .then(messages => {
            $('.message-list').empty();
            messages.forEach(message => {
                $('.message-list').append(`
                <div class="message">
                    <strong>${message.sender}:</strong> ${message.content} <em>${new Date(message.timestamp).toLocaleString()}</em>
                </div>
            `);
            });
        })
        .catch(error => {
            console.error('Error loading messages:', error);
        });
}

// Function to load available receivers
function loadReceivers() {
    fetch('http://localhost:8080/api/users', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                console.error('Error fetching receivers:', response.statusText);
                throw new Error(`Failed to fetch receivers. Status: ${response.status}`);
            }
            return response.json();
        })
        .then(users => {
            const receiverSelect = $('#receiverSelect');
            receiverSelect.empty();
            users.forEach(user => {
                receiverSelect.append(new Option(user.name, user.id));
            });
        })
        .catch(error => {
            console.error('Error loading receivers:', error);
        });
}

// Function to send a message
function sendMessage(senderId, receiverId, content) {
    //console.log(receiverId, senderId);
    if (!content.trim()) {
        alert('Message content cannot be empty.');
        return;
    }

    const message = {
        content: content,
        sender: senderId,
        receiver: receiverId
    };

    fetch('http://localhost:8080/api/messages', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(message)
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to send message');
            return response.json();
        })
        .then(response => {
            loadMessages(); // Reload messages after sending
            $('#messageContent').val(''); // Clear the input
        })
        .catch(error => {
            alert('Error sending message. Make sure you can message this recipient.');
            console.error('Error sending message:', error);
        });
}

// Function to get sender ID from token
async function getSenderIdFromToken(token) {
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    const username = decodedToken.sub; // Get the username from the token
    return await fetchUserIdByUsername(username); // Await the user ID from the fetch
}

async function fetchUserIdByUsername(username) {
    return fetch(`http://localhost:8080/api/users/username/${username}`, {  // Update the endpoint as needed
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch user ID');
            return response.json(); // Return the user object
        })
        .then(response => {
            return response.id; // Return the user ID (ensure this matches your API response)
        })
        .catch(error => {
            console.error('Error fetching user ID:', error);
            return null; // Handle the error appropriately
        });
}
