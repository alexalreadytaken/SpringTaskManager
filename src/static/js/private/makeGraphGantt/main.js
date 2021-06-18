import { config } from '../config.js';
import { makeGraph } from './parsingData/making.js';

let SchemasPull = []

// makeGraph(scheme1)
fetch(`http://${config.url}/schema/1`)
    .then(response => response.json())
    .then(response => SchemasPull.push(response))

    setTimeout(() => {    
        makeGraph(...SchemasPull)
    }, 200);

export {SchemasPull}