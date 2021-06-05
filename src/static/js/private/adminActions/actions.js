import { uploadSchemaListener } from "../rest_Post/upload_schema.js";
import { createSchemas } from "./createListSchemas.js";



uploadSchemaListener()

document.getElementById('listOfSchemas').addEventListener('click', () => {
    createSchemas()
    document.getElementById('listSchemas').innerHTML = null
})

