import {makePercent} from '../parsingData/percentForTasks.js';
import {parsTask} from '../parsingData/parsFields.js';

function multiFetch (url) {
    fetch( url )
    .then(response => response.json())
    .then(result => {
        switch ( url ) {
            case '/schemas/master/files':
                makeFileList(result)
            break
            
            case '/admin/users':
                makeUserList(result)
            break
            
            case 'http://localhost:2000/admin/schema/1':
                makeGraph(result)
            break

        }
    })
}

async function getSummary ({url, tasks}) {
    const response = await fetch(url);
    const summary = await response.json();
    return makePercent({
        data: parsTask(tasks),
        summary: summary
    })
}

export {getSummary}