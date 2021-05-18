// import {multiFetch} from './additionalModules/helpers_Module.js';
import {makeGraph} from './parsingData/making.js';
import scheme1 from '../local-JSON/scheme1.js';



// multiFetch('http://10.3.0.87:2000/schemas/master/Предмет_1')
// multiFetch('http://10.3.0.87:2000/schemas/master/Предмет_1/summary')

makeGraph(scheme1)