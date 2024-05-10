async function getUserDataById(userId) {
    const response = await fetch(`/api/admin/${userId}`);
    return await response.json();
}

const editModal = document.getElementById("editModal")

document.addEventListener('click', async function (event) {
    if (event.target.classList.contains('btn-info')) {
        const userId = event.target.dataset.userId;
        await fillEditModal(userId);
    }
});

async function fillEditModal(userId) {
    try {
        const user = await getUserDataById(userId);
        const roles = await getRoles();

        document.getElementById('editId').value = user.id;
        document.getElementById('editName').value = user.userName;
        document.getElementById('editEmail').value = user.email;

        const roleSelect = document.getElementById('editRole');
        roleSelect.innerHTML = '';
        console.log(roles);
        for (let role of roles) {
            const optionRole = document.createElement('option');
            optionRole.value = role.name;
            optionRole.textContent = role.name.replace('ROLE_', '');
            roleSelect.appendChild(optionRole);
        }

        const editModal = document.getElementById('editModal');

    } catch (error) {
        console.error("Failed to fill edit modal:", error);
    }
}


async function sendDataEditUser(user) {
    await fetch("/api/admin",
        {method: "PUT", headers: {'Content-type': 'application/json'}, body: JSON.stringify(user)})
}

//
const modalEdit = document.getElementById("editModal");


modalEdit.addEventListener("submit", async function (event) {
    event.preventDefault();

    const rolesSelected = document.getElementById("editRole");

    let roles = [];
    for (let option of rolesSelected.selectedOptions) {
        if (option.value === ROLE_USER.name) {
            roles.push(ROLE_USER);
        } else if (option.value === ROLE_ADMIN.name) {
            roles.push(ROLE_ADMIN);
        }
    }

    let user = {
        id: document.getElementById("editId").value,
        userName: document.getElementById("editName").value,
        email: document.getElementById("editEmail").value,
        password: document.getElementById("editPassword").value,
        roles: roles
    }
    console.log(user);

    await sendDataEditUser(user);
    document.querySelector('button#closeEditForm').click();
    await displayUsers()

})