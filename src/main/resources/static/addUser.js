async function createNewUser(user) {
    await fetch("/api/admin",
        {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(user)})
    ;
}

const ROLE_USER = {roleId: 1, name: "ROLE_USER"};
const ROLE_ADMIN = {roleId: 2, name: "ROLE_ADMIN"};

document.addEventListener('DOMContentLoaded', async function () {

    await addNewUserForm();

});

async function addNewUserForm() {
    const newUserForm = document.getElementById("newUser");

    newUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const userName = newUserForm.querySelector("#userName").value.trim();
        const email = newUserForm.querySelector("#email").value.trim();
        const password = newUserForm.querySelector("#password").value.trim();

        const rolesSelected = document.getElementById("roles");

        let roles = [];
        for (let option of rolesSelected.selectedOptions) {
            if (option.value === ROLE_USER.name) {
                roles.push(ROLE_USER);
            } else if (option.value === ROLE_ADMIN.name) {
                roles.push(ROLE_ADMIN);
            }
        }

        const newUserData = {
            userName: userName,
            email: email,
            password: password,
            roles: roles
        };

        await createNewUser(newUserData);
        newUserForm.reset();

        document.querySelector('button#user-table-tab').click();
        await displayUsers();
    });
}