function registerUser() {
    var form = document.getElementById("registrationForm");
    var formData = new FormData(form);
    fetch('/register', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert('User registered successfully');
            form.reset();
            window.location.href = '/user_list'; 
        } else {
            alert('Failed to register user');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred while registering user');
    });
}

// function saveUser() {
//     var form = document.getElementById("editForm");
//     var formData = new FormData(form);
//     var userId = document.getElementById("userId").value; // Assuming you have a hidden input field for user ID

//     fetch('/edit/' + userId, {
//         method: 'PUT', // Assuming you use PUT method for updating
//         body: formData
//     })
//     .then(response => {
//         if (response.ok) {
//             alert('User details updated successfully');
//             // Redirect or perform any other action as needed
//             window.location.href = '/edit.html';
//         } else {
//             alert('Failed to update user details');
//         }
//     })
//     .catch(error => {
//         console.error('Error:', error);
//         alert('An error occurred while updating user details');
//     });
// }

// Event listener for register button
document.getElementById("registerButton").addEventListener("click", registerUser);

// Event listener for save button
// document.getElementById("editForm").addEventListener("submit", function(event) {
//     event.preventDefault(); // Prevent default form submission
//     saveUser(); // Call saveUser function to handle the update
// });

// function deleteUser(userId) {
//     if (confirm("Are you sure you want to delete this user?")) {
//         fetch('/users/' + userId, {
//             method: 'DELETE'
//         })
//         .then(response => {
//             if (response.ok) {
//                 // Reload the page after successful deletion
//                 window.location.reload();
//             } else {
//                 alert("Failed to delete user");
//             }
//         })
//         .catch(error => {
//             console.error('Error deleting user:', error);
//             alert("Failed to delete user");
//         });
//     }
// }

