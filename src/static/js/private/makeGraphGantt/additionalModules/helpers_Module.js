import {makePercent} from '../parsingData/percentForTasks.js';
import {parsTask} from '../parsingData/parsFields.js';

async function getSummary ({url, tasks}) {
    const response = await fetch(url);
    const summary = await response.json();
    return makePercent({
        data: parsTask(tasks),
        summary: summary
    })
}

export {getSummary}