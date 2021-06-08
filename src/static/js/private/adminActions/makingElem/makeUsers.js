import { config } from "../../config.js"


function makeUsers(id) {
    fetch(`http://${config.url}/admin/users`).then(response => response.json())
        .then(users => {
            users.forEach(user => {
                document.getElementById(id).insertAdjacentHTML('beforeend', `
                    <div class = 'user' id = 'userItem${user.id}'>
                        <p>${user.name}</p> <input type = 'checkbox'/>
                    </div>
                `)
            })
        })
}

export { makeUsers }