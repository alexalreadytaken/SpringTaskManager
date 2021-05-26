import {makeGraph} from './parsingData/making.js';

fetch('http://10.3.0.87:2000/admin/schema/1')
    .then(response => response.json())
    .then(response => makeGraph(response))
