import { status } from "../local-JSON/studentList.js";
import { getSummary } from "../makeGraphGantt/additionalModules/helpers_Module.js";
import { refreshChart } from "../makeGraphGantt/chartMaking.js";
import { taskId } from "../makeGraphGantt/dinamicFormExecute/eventChart.js";
import { makeChildren } from "../makeGraphGantt/parsingData/addChildrenToData.js";
import { makeWeakDepen } from "../makeGraphGantt/parsingData/makeWeakDepen.js";
import { parsDepen, parsTask } from "../makeGraphGantt/parsingData/parsFields.js";

function setGrade(schemaId) {
    // add HTML tags for universal form, so as not to delete everything all the time

    document.getElementById('flex_Form').insertAdjacentHTML('afterbegin', `<div id = 'userId'><h1>Юзер</h1></div>`)
    document.getElementById('flex_Form').insertAdjacentHTML('beforeend', `<div id = 'statusCase'> <h1>Статус</h1> </div>`)
    document.getElementById('flex_Form').insertAdjacentHTML('beforeend', `<div id = 'gradeCase'><h1>Оценка</h1></div>`)

    let studentList


    fetch(`http://10.3.0.87:2000/admin/schema/${schemaId}/task/${taskId}/state`).then(res => res.json())
    .then(res => {
        studentList = res
        res.forEach((el, x) => {
            document.getElementById('statusCase').insertAdjacentHTML('beforeend', `
                <select id = 'status${x}'></select>
            `)
            
            document.getElementById('gradeCase').insertAdjacentHTML('beforeend', `
                    <input id = 'grade${x}' placeholder = '2' type = 'number' min = '2' max = '5'/>
            `)

            status.forEach(el => {
                document.getElementById(`status${x}`).insertAdjacentHTML('beforeend', `
                        <option>${el}</option>
                `)
            })
        })

        
    })

    fetch('http://10.3.0.87:2000/admin/users').then(res => res.json())
        .then(response => {
            response.forEach( el => {
                document.getElementById('userId').insertAdjacentHTML('beforeend', `
                    <div>${el.name}</div>
                `)
            })
        })

    document.getElementById('modalBtnSave').addEventListener('click', e => {


        studentList.forEach((st, i) => {
            const statusSelect = document.getElementById(`status${i}`).value
            const gradeSelect = document.getElementById(`grade${i}`).value
            console.log([statusSelect], [gradeSelect])
        })

        fetch(`http://10.3.0.87:2000/admin/user/1/schema/${schemaId}/task/${taskId}?setGrade=5&setStatus=finished`)
        


        fetch(`http://10.3.0.87:2000/admin/schema/${schemaId}`).then(res => res.json())
            .then(data => {
                let tasks = parsTask(data)

                const thee = tasks.filter(el => el.theme)
                
                makeWeakDepen(tasks, parsDepen(data))
                
                makeChildren(thee, parsDepen(data), tasks)

                getSummary({
                    url: 'http://10.3.0.87:2000/admin/schema/1/summary',
                    tasks: data
                }).then(res => {
                    tasks = res            
                    refreshChart(thee)
                })
            })
    })
}

export {setGrade}