const deleteModal = document.getElementById("deleteModal")

document.addEventListener('click', async function (event) {
    if (event.target.classList.contains('button-for-delete')) {
        const userId = event.target.dataset.userId;
        await filldeleteModal(userId);
    }
});

async function filldeleteModal(userId) {
    try {
        const user = await getUserDataById(userId);
        const roles = await getRoles();

        console.log(user);
        document.getElementById('deleteId').value = user.id;
        document.getElementById('deleteName').value = user.userName;
        document.getElementById('deleteEmail').value = user.email;

        const roleSelect = document.getElementById('deleteRole');
        roleSelect.innerHTML = '';

        for (let role of roles) {
            const optionRole = document.createElement('option');
            optionRole.value = roles.name;
            optionRole.textContent = role.name.replace('ROLE_', '');
            roleSelect.appendChild(optionRole);
        }

        const deleteModal = document.getElementById('deleteModal');

    } catch (error) {
        console.error("Failed to fill edit modal:", error);
    }
}


async function sendDataDeleteUser(userId) {
    await fetch(`/api/admin/${userId}`,
        {method: "DELETE"});
}

async function getRoles() {
    const response = await fetch('/api/admin/roles');
    return await response.json();
}

const modalDelete = document.getElementById("deleteModal");

modalDelete.addEventListener("submit", async function (event) {
    event.preventDefault();

    const userId = event.target.querySelector("#deleteId").value;

    await sendDataDeleteUser(userId);
    document.querySelector('button#closeDeleteForm').click();
    await displayUsers()
})