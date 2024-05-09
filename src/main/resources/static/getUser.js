const url = 'http://localhost:8080/api/user';
const userMenu = document.getElementById('userMenu');
const tableUser = document.getElementById('tableUser');

document.addEventListener('DOMContentLoaded', async function () {
    try {
        const user = await getCurrentUser();
        displayUser(user);
        displayMenu(user)
    } catch (error) {
        console.error("Failed to get current user:", error);
    }
});

async function getCurrentUser() {
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Failed to get current user: ${response.status} ${response.statusText}`);
    }
    return await response.json();
}

function displayUser(user) {
    const userData = `
        <tr>
            <td>${user.id}</td>
            <td>${user.userName}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.name).join(' ')}</td>
        </tr>
    `;
    tableUser.innerHTML = userData;
}

function displayMenu(user) {
    const menu = `
        <span>${user.email}</span>
        <span>with roles</span>
        <span>${user.roles.map(role => role.name).join(' ')}</span>
    `;
    userMenu.innerHTML = menu;
}
