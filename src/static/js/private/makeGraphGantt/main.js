import { config } from '../config.js';
import {makeGraph} from './parsingData/making.js';

// makeGraph(scheme1)
fetch(`http://${config.url}/admin/schema/1`)
    .then(response => response.json())
    .then(response => makeGraph(response))
