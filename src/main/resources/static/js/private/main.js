// import * as all from './helpers_Module.js'
// import makeGraph from './making.js'

import scheme1 from './local-JSON/scheme1.js';

// all.multiFetch('http://10.1.0.64:2000/schemas/master/Предмет_1')

// makeGraph()

const tasksPars = Object.entries(scheme1.tasksMap).map(el => el[1])
const depen = Object.entries(scheme1.dependencies).map(el => el[1])

tasksPars.splice(0,2)
console.log(tasksPars)

var tasks = [...tasksPars]


var gantt = new Gantt("#gantt", tasks);
