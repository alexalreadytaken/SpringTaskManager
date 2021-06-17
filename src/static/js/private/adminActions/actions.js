import { clearing } from "../makeGraphGantt/additionalModules/clearing.js";
import { uploadSchemaListener } from "../rest_Post/upload_schema.js";
import { validateStudent } from "./addStudentToSchema.js";
import { createSchemas } from "./createListSchemas.js";
import { getReports } from "./reports/getReports.js";

uploadSchemaListener()
document.getElementById('listReports').addEventListener('click', getReports)

document.getElementById('listOfSchemas').onclick = () => {
    let schemas

    createSchemas().then(response => schemas = response)

    setTimeout(() => {
        schemas.forEach(schema => {
            document.getElementById('listSchemas').insertAdjacentHTML('beforeend', `
                <option id = 'nameSchema'>${schema.name}</option>
            `)
        })
    }, 100);
        
    document.getElementById('saveUserToSchema').onclick = () => {
        validateStudent(document.getElementById('listSchemas').value)
    }

    document.getElementById('listSchemas').addEventListener('change', e => {
        if (e.target.value !== 'Выберите схему') {
            document.getElementById('saveUserToSchema').setAttribute('rel','modal:open')    
        } else {
            document.getElementById('saveUserToSchema').setAttribute('rel','')
        }
    })


    clearing('listSchemas')
    clearing('extra-content')
    document.addEventListener('click', e => e.target.id === 'modalBtnBack' ? clearing('extra-content') : null)
    schemas = null
    document.getElementById('listSchemas').insertAdjacentHTML('beforeend', '<option>Выберите схему</option>')
}