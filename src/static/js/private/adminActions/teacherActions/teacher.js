import { config } from "../../config.js"
import { getSummary } from "../../makeGraphGantt/additionalModules/helpers_Module.js"
import { refreshChart } from "../../makeGraphGantt/chartMaking.js"
import { makeChildren } from "../../makeGraphGantt/parsingData/addChildrenToData.js"
import { makeWeakDepen } from "../../makeGraphGantt/parsingData/makeWeakDepen.js"
import { parsDepen, parsTask} from '../../makeGraphGantt/parsingData/parsFields.js'

let localUsers

function eventForSchemas (taskId, schemaId) {

    let statusesRu

    fetch(`http://${config.url}/statuses/ru`).then(r=>r.json())
        .then(statuses => statusesRu = statuses)
        .catch(e => alert(e))

    let table = document.getElementById('tbody-infoTasks')
    table.innerHTML = null


    fetch(`http://${config.url}/admin/schema/${schemaId}/task/${taskId}/state`).then(response => response.json())
        .then(taskState => {
            console.log(taskState);
            fetch(`http://${config.url}/admin/users`).then(response => response.json())
                .then(users => {
                    localUsers = users
                    taskState.forEach((task, x) => {
                        users.forEach(user => {
                            if (user.id === Number(task.userId)) {
                                table.insertAdjacentHTML('beforeend', `
                                    <tr>    
                                        <td>${user.name}</td>
                                        <td><select id = 'select${x}'>выберите...</select></td>
                                        <td><input id = 'grade${x}' value = '${task.percentComplete}'></input></td>
                                    </tr>
                                `)

                                statusesRu.forEach(status => {
                                    const checked = () => (status === task.status) ? 'selected' : ''
                                    document.getElementById(`select${x}`).insertAdjacentHTML('beforeend', `
                                        <option ${checked()} >${status}</option>
                                    `)
                                })

                            }
                        })
                    })

                })
        })

    $('#modal-infoTasks').modal()

    document.getElementById('saveUserStat').addEventListener('click', (e) => {
        console.log(localUsers)
        let infoStudents = []
    
        localUsers.forEach((user, x) => {
    
            infoStudents.push({
                userId: user.id,
                status: document.getElementById(`select${x}`).value,
                grade: document.getElementById(`grade${x}`).value
            })
    

            console.log(infoStudents[x])
    
            fetch(`http://${config.url}/admin/user/${infoStudents[x].userId}/schema/${schemaId}/task/${taskId}?setPercentComplete=${infoStudents[x].grade}&setStatus=${infoStudents[x].status}`)
                .then(response => {
                    console.log(response.json());
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
                    }).catch(e => console.log('schema fetch: ', e))
              }).catch(e => console.log(e))
        })
    })
}



export { eventForSchemas }