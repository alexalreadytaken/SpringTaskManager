import { eventForSchemas } from '../../adminActions/teacherActions/teacher.js'

let themeTask, notesTask, taskId

function eventChart (chart, schemaId) {
    chart.listen ("rowDblClick", e => {
        // name, id, notes === any get item for obj
        taskId = e.item.get( 'id' ) 
        themeTask = e.item.get( 'theme' )
        notesTask = e.item.get( 'notes' )


        if (!themeTask) {
            eventForSchemas(taskId, schemaId)
        }
    })
}

export { eventChart, taskId, notesTask, themeTask }