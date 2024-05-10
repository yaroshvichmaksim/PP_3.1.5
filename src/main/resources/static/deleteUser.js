
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

        console.log(user);
        document.getElementById('deleteId').value = user.id;
        document.getElementById('deleteName').value = user.userName;
        document.getElementById('deleteEmail').value = user.email;

        const roleSelect = document.getElementById('deleteRole');
        roleSelect.innerHTML = '';

        const optionUser = document.createElement('option');
        optionUser.value = 'ROLE_USER';
        optionUser.textContent = 'USER';
        roleSelect.appendChild(optionUser);

        const optionAdmin = document.createElement('option');
        optionAdmin.value = 'ROLE_ADMIN';
        optionAdmin.textContent = 'ADMIN';
        roleSelect.appendChild(optionAdmin);

        const deleteModal = document.getElementById('deleteModal');

    } catch (error) {
        console.error("Failed to fill edit modal:", error);
    }
}


async function sendDataDeleteUser(userId) {
    await fetch(`/api/admin/${userId}`,
        {method: "DELETE"});
}

const modalDelete = document.getElementById("deleteModal");

modalDelete.addEventListener("submit", async function (event) {
    event.preventDefault();

    const userId= event.target.querySelector("#deleteId").value;

    await sendDataDeleteUser(userId);
    document.querySelector('button#closeDeleteForm').click();
    await displayUsers()
})