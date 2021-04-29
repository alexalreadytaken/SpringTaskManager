import { showFormExecute } from './executeTask.js'
import { createModalForm } from './createModalForm.js'
import { addNotesForTask } from './addNotesForTask.js'
import { acceptTask } from './acceptTask.js'


function eventChart (chart) {
    let oneComplete = false
    chart.listen ("rowClick", e => {
        const taskId = e.item.get( 'id' ) // name, id, notes === any get item for obj
        const themeTask = e.item.get( 'theme' )
        const notesTask = e.item.get( 'notes' )

        if ( !oneComplete ) {oneComplete = true; createModalForm()}

        showFormExecute( themeTask )
        if (!themeTask) {addNotesForTask( notesTask )}
        
        acceptTask({
            taskId: taskId,
        })
    })
}

export { eventChart }