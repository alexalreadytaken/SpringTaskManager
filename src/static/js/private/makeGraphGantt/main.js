import { config } from '../config.js';
import { makeGraph } from './parsingData/making.js';

let SchemasPull = []

// makeGraph(scheme1)

function sratb (schemaId) {
    fetch(`http://${config.url}/schema/${schemaId}`)
    .then(response => {
        console.log(response)
        if (response.status !== 404) {
            return response.json()
        } else {
            console.log('Схемы нет, пожалуйста загрузите ее.')
            return null
        }
    })
    .then(response =>{
        SchemasPull.push(response)
    })

    setTimeout(() => {    
        makeGraph(...SchemasPull)
    }, 200);
}



export {SchemasPull, sratb}