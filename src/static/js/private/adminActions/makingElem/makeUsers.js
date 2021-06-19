import { config } from "../../config.js"


function makeUsers(id, schemaId) {
    fetch(`http://${config.url}/admin/schema/${schemaId}/candidates`).then(response => response.json())
        .then(candidates => {
            if (candidates.length) {

                candidates.forEach(user => {
                    document.getElementById(id).insertAdjacentHTML('beforeend', `
                        <div class = 'user' id = 'userItem${user.id}'>
                            <p>${user.name}</p> <input name = '${user.id}' type = 'checkbox'/>
                        </div>
                    `)
                })
                document.getElementById(id).insertAdjacentHTML('beforeend', `
                    <button type="submit" id="addUserToSchema">Назначить</button>
                `)
            } else {
                document.getElementById(id).insertAdjacentHTML('beforeend',`
                    <h3>Кандидатов на схему нет!</h3>
                `)
            }
        })
}

export { makeUsers }