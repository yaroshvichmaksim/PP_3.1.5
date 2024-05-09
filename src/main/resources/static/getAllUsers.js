const urlUsers = 'http://localhost:8080/api/users';
const tableUsers = document.getElementById('tableUsers');

document.addEventListener('DOMContentLoaded', async function () {
    await displayUsers();
});

async function getAllUsers() {
    const response = await fetch(urlUsers);
    if (!response.ok) {
        throw new Error(`Failed to get current user: ${response.status} ${response.statusText}`);
    }
    return await response.json();
}

function insertUsers(users) {
    let usersData = '';
    for (let user of users) {
        usersData += `
        <tr>
            <td>${user.id}</td>
            <td>${user.userName}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.name).join(' ')}</td>
            <td>
                <button class="btn btn-info btn-sm text-white"
                        data-bs-toggle="modal"
                        data-bs-target="#editModal"
                        data-user-id="${user.id}">
                Edit</button>
            </td>
            <td>
                <button class="btn btn-danger btn-sm btn-delete button-for-delete"
                        data-bs-toggle="modal"
                        data-bs-target="#deleteModal"
                        data-user-id="${user.id}">                     
                Delete</button>
            </td>
        </tr>
    `;
    }
    tableUsers.innerHTML = usersData;
}

async function displayUsers() {
    try {
        const users = await getAllUsers();

        insertUsers(users);
    } catch (error) {
        console.error("Failed to get current user:", error);
    }
}
