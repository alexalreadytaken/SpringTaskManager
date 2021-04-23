import { showFormExecute } from './executeTask.js'
import { createModalWin } from './createModalWin.js'

function eventChart (chart) {
    let oneComplete = false
    chart.listen ("rowClick", e => {
        const taskId = e.item.get("id") // name, id, notes === any get item for obj
        const themeTask = e.item.get('theme')
        const notesTask = e.item.get('notes')

        if (!oneComplete) {oneComplete = true; createModalWin(notesTask)}

        showFormExecute( themeTask )
    })
}

export {eventChart}