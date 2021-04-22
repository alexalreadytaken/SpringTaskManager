import { checkCompleteTask } from './executeTask.js'
import { createModalWin } from './createModalWin.js'


function eventChart (chart) {
    chart.listen ("rowClick", e => {
        const taskId = e.item.get("id") // name, id, notes === any get item for obj
        const themeTask = e.item.get('theme')
        const notesTask = e.item.get('notes')


        createModalWin(notesTask)
        checkCompleteTask( taskId, themeTask, notesTask )
    })
}

export {eventChart}