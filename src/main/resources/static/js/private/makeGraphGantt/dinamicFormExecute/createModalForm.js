import { parsTask } from '../parsingData/parsFields.js'
import { acceptTask } from './acceptTask.js'
import scheme1 from '../../local-JSON/scheme1.js'


function createModalForm () {
    document.getElementById('body').insertAdjacentHTML('beforebegin', `
        <div class="form_Execute" id="form_Execute">
            <div id = 'btn_Exit' class = 'btn_Exit'></div>
            <div id = 'flex_Form' class = 'flex_Form'>
                <div id = 'notesTask'></div>
                <div id = 'content_Form' class = 'content_Form'>some text content annotation for task blablablablablabla</div>
                <button id = 'submitTask' class = 'btn_Send_Execute'>Сохранить!</button>
            </div>
        </div>
    `)

    document.addEventListener('click', e => {
        if (e.target.id === 'btn_Exit') {
            document.getElementById('form_Execute').style.display = 'none'
            document.getElementById('body').style.opacity = 1
            document.getElementById('body').style.filter = 'grayscale(0)'
            document.getElementById('notesTask').innerHTML = ''
        }
    })

    document.getElementById('submitTask')
    .addEventListener('click', e => {
        const tasks = parsTask(scheme1).splice(2, parsTask(scheme1).length)
        acceptTask({
            tasks: tasks
        })
    })
}

export { createModalForm }