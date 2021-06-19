import { config } from "../config.js";

async function createSchemas () {

    const response = await fetch(`http://${config.url}/schemas`)
    const schemas = await response.json()
    
    return schemas
}

export { createSchemas }