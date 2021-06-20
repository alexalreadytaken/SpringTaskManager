import { config } from "../../config.js"
import { sratb } from "../../makeGraphGantt/main.js"


function selectSchema () {

    let schemasL 

    fetch(`http://${config.url}/schemas`).then(r => r.json())
        .then(schemas => {
            schemasL = schemas
            schemas.forEach(schema => {
                document.getElementById('select-schema').insertAdjacentHTML('beforeend', `
                    <option value = '${schema.id}'>${schema.name}</option>
                `)
            })
        })

    document.getElementById('showSchemaContent').insertAdjacentHTML('beforeend', `
        <button id = "showSchema">Выбрать</button>
    `)


    document.getElementById('showSchema').addEventListener(`click`, () => {
        console.log(document.getElementById('select-schema').value);
        sratb(document.getElementById('select-schema').value)
    })



    $('#selectSchema').click(() => {
        $('#modal-select-schema').modal()
    })
}


export {selectSchema}