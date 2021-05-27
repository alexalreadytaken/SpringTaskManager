import { config } from "../config.js";
import { status } from "../local-JSON/studentList.js";
import { getSummary } from "../makeGraphGantt/additionalModules/helpers_Module.js";
import { refreshChart } from "../makeGraphGantt/chartMaking.js";
import { taskId } from "../makeGraphGantt/dinamicFormExecute/eventChart.js";
import { makeChildren } from "../makeGraphGantt/parsingData/addChildrenToData.js";
import { makeWeakDepen } from "../makeGraphGantt/parsingData/makeWeakDepen.js";
import { parsDepen, parsTask } from "../makeGraphGantt/parsingData/parsFields.js";

function setGrade(schemaId) {
    let studentList

    // add HTML tags to universal form, so as not to delete everything all the time
    document.getElementById('flex_Form').insertAdjacentHTML('afterbegin', `<div id = 'userId'><h1>Юзер</h1></div>`)
    document.getElementById('flex_Form').insertAdjacentHTML('beforeend', `<div id = 'statusCase'> <h1>Статус</h1> </div>`)
    document.getElementById('flex_Form').insertAdjacentHTML('beforeend', `<div id = 'gradeCase'><h1>Оценка</h1></div>`)


    fetch(`http://${config.url}/admin/schema/${schemaId}/task/${taskId}/state`).then(res => res.json())
    .then(res => {
        studentList = res
        res.forEach((elem, x) => {
            document.getElementById('statusCase').insertAdjacentHTML('beforeend', `
                <select id = 'status${x}'></select>
            `)
            
            document.getElementById('gradeCase').insertAdjacentHTML('beforeend', `
                    <input id = 'grade${x}' value = '${elem.grade}' type = 'number' min = '2' max = '5'/>
            `)

            status.forEach(el => {
                const checked = () => (el === elem.status) ? 'selected' : ''
                document.getElementById(`status${x}`).insertAdjacentHTML('beforeend', `
                        <option ${checked()}>${el}</option>
                `)
            })
        })        
    })

    fetch(`http://${config.url}/admin/users`).then(res => res.json())
        .then(response => {
            response.forEach( el => {
                document.getElementById('userId').insertAdjacentHTML('beforeend', `
                    <div>${el.name}</div>
                `)
            })
        })

    document.getElementById('modalBtnSave').addEventListener('click', e => {

        let tel = []
            
        studentList.forEach((st, i) => {

            tel.push({
                userId: st.userId,
                grade: document.getElementById(`grade${i}`).value,
                status: document.getElementById(`status${i}`).value
            })


            fetch(`http://${config.url}/admin/user/${st.userId}/schema/${schemaId}/task/${taskId}?setGrade=${tel[i].grade}&setStatus=${tel[i].status}`).then(res => {
                fetch(`http://${config.url}/admin/schema/${schemaId}`).then(res => res.json())
                .then(data => {
                    let tasks = parsTask(data)
    
                    const thee = tasks.filter(el => el.theme)
                    
                    makeWeakDepen(tasks, parsDepen(data))
                    
                    makeChildren(thee, parsDepen(data), tasks)
    
                    getSummary({
                        url: `http://${config.url}/admin/schema/1/summary`,
                        tasks: data
                    }).then(res => {
                        tasks = res            
                        refreshChart(thee)
                    })
                })    
            })
        })

        console.log(studentList)

        // console.log(tel)
        
        tel = null
    })
    studentList = null
}

export {setGrade}