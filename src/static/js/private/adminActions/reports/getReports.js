import { config } from "../../config.js"


function getReports () {
    let table = document.getElementById('tbody-reports')
    table.innerHTML = null
    fetch(`http://${config.url}/admin/tasks/unconfirmed`).then(response => response.json())
        .then(reports => {
            reports.forEach(report => {
                console.log(report)
                table.insertAdjacentHTML('beforeend', `
                    <tr>
                        <td>${report.userId}</td>
                        <td>${report.schemaId}</td>
                        <td>${report.taskId}</td>
                        <td>${report.percentComplete * 100}%</td>
                        <td><button id = 'someId...'>проверить</button></td>
                    </tr>
                `)
            })
        })
}


export { getReports }