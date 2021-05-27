import { showFormExecute } from './executeTask.js'
import { createModalForm } from './createModalForm.js'
// import { addNotesForTask } from './addNotesForTask.js'
import { acceptTask } from './acceptTask.js'
import { setGrade } from '../../interactionsTasks/setGrade.js'

let themeTask, notesTask, taskId

function eventChart (chart, schemaId) {
    let oneComplete = false
    chart.listen ("rowDblClick", e => {
        // name, id, notes === any get item for obj
        taskId = e.item.get( 'id' ) 
        themeTask = e.item.get( 'theme' )
        notesTask = e.item.get( 'notes' )

        
        if ( !oneComplete ) {oneComplete = true; createModalForm()}

        !themeTask ? setGrade(schemaId) : null

        showFormExecute( themeTask )
    })
}

export { eventChart, taskId, notesTask, themeTask }