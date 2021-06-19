import { config } from "../config.js";
import { getSummary } from "../makeGraphGantt/additionalModules/helpers_Module.js";
import { refreshChart } from "../makeGraphGantt/chartMaking.js";
import { taskId } from "../makeGraphGantt/dinamicFormExecute/eventChart.js";
import { makeChildren } from "../makeGraphGantt/parsingData/addChildrenToData.js";
import { makeWeakDepen } from "../makeGraphGantt/parsingData/makeWeakDepen.js";
import { parsDepen, parsTask } from "../makeGraphGantt/parsingData/parsFields.js";

function setGrade(schemaId) {
    // let studentList

    // fetch(`http://${config.url}/admin/schema/${schemaId}/task/${taskId}/state`).then(res => res.json())
    // .then(res => {
    //     studentList = res
    //     res.forEach((elem, x) => {
    //         document.getElementById('statusCase').insertAdjacentHTML('beforeend', `
    //             <select id = 'status${x}'></select>
    //         `)
            
    //         document.getElementById('gradeCase').insertAdjacentHTML('beforeend', `
    //                 <input id = 'grade${x}' value = '${elem.percentComplete}' type = 'number' min = '2' max = '5'/>
    //         `)


    //         fetch(`http://${config.url}/statuses/ru`).then(res=>res.json())
    //             .then(status => {
    //                 console.log(elem);
    //                 status.forEach(el => {
    //                     const checked = () => (el === elem.status) ? 'selected' : ''
    //                     document.getElementById(`status${x}`).insertAdjacentHTML('beforeend', `
    //                             <option ${checked()}>${el}</option>
    //                     `)
    //                 })
    //             })

    //     })        
    // })

    // fetch(`http://${config.url}/admin/users`).then(res => res.json())
    //     .then(response => {
    //         response.forEach( el => {
    //             document.getElementById('userId').insertAdjacentHTML('beforeend', `
    //                 <div>${el.name}</div>
    //             `)
    //         })
    //     })

    document.getElementById('modalBtnSave').addEventListener('click', e => {

        let oldValue = []
            
        studentList.forEach((st, i) => {

            oldValue.push({
                userId: st.userId,
                grade: document.getElementById(`grade${i}`).value,
                status: document.getElementById(`status${i}`).value
            })


            fetch(`http://${config.url}/admin/user/${st.userId}/schema/${schemaId}/task/${taskId}?setPercentComplete=${oldValue[i].grade}&setStatus=${oldValue[i].status}`).then(res => {
                fetch(`http://${config.url}/schema/${schemaId}`).then(res => res.json())
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
        
        oldValue = null
    })
    studentList = null
}

export {setGrade}