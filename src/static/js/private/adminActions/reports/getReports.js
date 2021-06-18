import { config } from "../../config.js"

function getReports () {
    let table = document.getElementById('tbody-reports')
    table.innerHTML = null
    fetch(`http://${config.url}/admin/tasks/unconfirmed`).then(response => response.json())
        .then(reports => {
            fetch(`http://${config.url}/admin/users`).then(response => response.json())
                .then(users => {
                    users.forEach(user => {
                        reports.forEach(report => {
                            if (user.id === Number(report.userId)) {
                                table.insertAdjacentHTML('beforeend', `
                                    <tr>
                                        <td>${user.name}</td>
                                        <td>${report.schemaId}</td>
                                        <td>${report.taskId}</td>
                                        <td>${report.percentComplete * 100}%</td>
                                        <td><button id = 'someId...'>проверить</button></td>
                                    </tr>
                                `)
                            }
                        })
                    })
                })
            })
}


export { getReports }