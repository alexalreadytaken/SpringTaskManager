import { config } from "../config.js";
import { clearing } from "../makeGraphGantt/additionalModules/clearing.js";
import { makeUsers } from "./makingElem/makeUsers.js";


function validateStudent (schema) {
    console.log(schema);
    fetch(`http://${config.url}/schemas`).then(response => response.json())
        .then(response => {
            response.forEach(el => {
                if (el.name === schema) {
                    makeUsers('extra-content', el.id)
                    addUserToSchema(el.id)
                } else {
                    alert('Схема не выбрана!')
                    clearing('extra-content')
                }
            })
        })
}

function addUserToSchema (schemaId) {
    document.getElementById('extra-content').addEventListener('submit', submitForm);

    function submitForm (event) {
        event.preventDefault()
        
        let formdata = new FormData(event.target)
        let obj
        let listOfDataStudents = []

        formdata.forEach((value, key) => {
            obj = {}
            obj[key] = value
            listOfDataStudents.push(obj)
        })

        listOfDataStudents.map(el => {
            fetch(`http://${config.url}/admin/schema/${schemaId}/addTo/user/${Object.keys(el)}`)
        })

        console.log(listOfDataStudents)
    }
}

export { validateStudent, addUserToSchema }
