import { config } from "../config.js";

function createSchemas () {
    fetch (`http://${config.url}/schemas`).then(response => response.json())
        .then(response => {
            response.forEach(schema => {
                document.getElementById('listSchemas').insertAdjacentHTML('beforeend', `
                    <option id = 'nameSchema'>${schema.name}</option>
                `)
            })
        })
}

export { createSchemas }