import { chartMaking } from '../chartMaking.js';
import { parsTask, parsDepen } from './parsFields.js';
import { makeChildren } from './addChildrenToData.js';
import { makeWeakDepen } from './makeWeakDepen.js';

import { getSummary } from '../additionalModules/helpers_Module.js'

import { refreshChart } from '../chartMaking.js'
import { config } from '../../config.js';

function makeGraph (response) {

    if (response === null) {
        console.log(response);
        $('#graph-Gant').html('<h1>Схем нет, пожалуйста загрузите</h1>')
    } else {
        const rootTask = response.rootTask
        
        let tasks = parsTask(response)
        
        // для обновления процентов, с дальнейшей возможностью расшерения до обновления каждого куска схемы в percentForTask
        getSummary({
            url: `http://${config.url}/admin/schema/1/summary`,
            tasks: response
        }).then(res => {
            tasks = res            
            refreshChart(data)
        })

        const depen = parsDepen(response)

        console.log(tasks)

        const data = tasks.filter(el => el.theme) // filter tasks of themes

        makeWeakDepen(tasks, depen) // make WEAK depen in tasks

        makeChildren(data, depen, tasks) // make HIERARCHICAL depen in data

        console.log('Data elem', data)
        console.log(depen)

        chartMaking(data, rootTask)
}
}


export {makeGraph}