import { config } from "../../config.js"


function makeUsers(id) {
    fetch(`http://${config.url}/admin/users`).then(response => response.json())
        .then(users => {
            users.forEach(user => {
                document.getElementById(id).insertAdjacentHTML('beforeend', `
                    <div class = 'user' id = 'userItem${user.id}'>
                        <p>${user.name}</p> <input name = '${user.id}' type = 'checkbox'/>
                    </div>
                `)
            })
            document.getElementById(id).insertAdjacentHTML('beforeend', `
            <button type="submit" id="addUserToSchema">Назначить</button>
            `)
        })
}

export { makeUsers }