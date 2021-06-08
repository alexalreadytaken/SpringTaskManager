import { config } from "../config.js";
import { clearing } from "../makeGraphGantt/additionalModules/clearing.js";
import { makeUsers } from "./makingElem/makeUsers.js";


function validateStudent (schema) {
    console.log(schema);
    fetch(`http://${config.url}/schemas`).then(response => response.json())
        .then(response => {
            response.forEach(el => {
                if (el.name === schema) {
                    makeUsers('extra-content')
                } else {
                    alert('Схема не выбрана!')
                    clearing('extra-content')
                }
            })
        })
}

function addUserToSchema () {
    
}

export { validateStudent, addUserToSchema }
