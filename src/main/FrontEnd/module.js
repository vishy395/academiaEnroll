let createdModuleId = null;

window.onload = function() {
    fetch('http://localhost:8080/api/faculty/students', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(students => {
            const studentList = document.getElementById('student-list');
            if (!students || students.length === 0) {
                studentList.innerHTML = 'No students available.';
                return;
            }

            students.forEach(student => {
                if (student.id && student.name) {
                    const checkbox = document.createElement('input');
                    checkbox.type = 'checkbox';
                    checkbox.value = student.id;
                    checkbox.id = `student-${student.id}`;

                    const label = document.createElement('label');
                    label.htmlFor = `student-${student.id}`;
                    label.textContent = student.name;

                    studentList.appendChild(checkbox);
                    studentList.appendChild(label);
                    studentList.appendChild(document.createElement('br'));
                }
            });
        })
        .catch(error => {
            console.error('Error fetching students:', error);
            alert('Failed to load students. Please try again.');
        });
};

async function createModule() {
    const moduleName = document.getElementById('moduleName').value;
    const selectedStudents = Array.from(document.querySelectorAll('#student-list input[type="checkbox"]:checked'))
        .map(checkbox => parseInt(checkbox.value));

    try {
        const facultyId = await getSenderIdFromToken(localStorage.getItem('token'));

        if (!facultyId) {
            throw new Error('Faculty ID could not be retrieved.');
        }

        fetch('http://localhost:8080/api/modules/create', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: moduleName,
                facultyId: facultyId,
                studentIds: selectedStudents
            })
        })
            .then(response => response.json())
            .then(module => {
                createdModuleId = module.id;
                document.getElementById('uploadMaterialButton').disabled = false;
                alert('Module created successfully!');
            });
    } catch (error) {
        console.error('Error creating module:', error);
        alert('Failed to create module. Please try again.');
    }
}

async function uploadMaterial() {
    if (!createdModuleId) {
        alert("Module must be created first.");
        return;
    }

    const fileInput = document.getElementById('materialFile');
    if (!fileInput.files[0]) {
        alert("Please select a file to upload.");
        return;
    }

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    fetch(`http://localhost:8080/api/modules/${createdModuleId}/add-material`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to upload material. Status: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            alert(data);
        })
        .catch(error => {
            console.error('Error uploading material:', error);
            alert('Failed to upload material. Please try again.');
        });
}

// Helper functions to get faculty ID from token
async function getSenderIdFromToken(token) {
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    const username = decodedToken.sub;
    return await fetchUserIdByUsername(username);
}

function fetchUserIdByUsername(username) {
    return fetch(`http://localhost:8080/api/users/username/${username}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch user ID');
            return response.json();
        })
        .then(response => response.id)
        .catch(error => {
            console.error('Error fetching user ID:', error);
            return null;
        });
}
