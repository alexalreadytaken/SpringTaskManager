// import * as all from './helpers_Module.js'
import multiFetch from './helpers_Module.js';
import makeGraph from './making.js';
import scheme1 from './local-JSON/scheme1.js';


// multiFetch('http://10.1.0.64:2000/schemas/master/Предмет_1')

makeGraph(scheme1)