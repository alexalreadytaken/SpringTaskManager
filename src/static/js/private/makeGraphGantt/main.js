import {makeGraph} from './parsingData/making.js';

<<<<<<< HEAD
multiFetch('http://localhost:2000/admin/schema/1')

// makeGraph(scheme1)
=======
fetch('http://10.3.0.87:2000/admin/schema/1')
    .then(response => response.json())
    .then(response => makeGraph(response))
>>>>>>> 51d214d66448011b039f513c98ffa7528b8e453d
