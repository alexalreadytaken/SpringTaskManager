import { config } from "../config.js"

function uploadSchemaListener() {
    document.querySelector('#uploadSchema').addEventListener('change', evt => {
        let fileData = evt.target["files"]
        let formData = new FormData()
        formData.append('file', fileData[0])
        evt.target.value = ''
        fetch(`http://${config.url}/schemas/files`, {
            method: 'POST',
            body: formData
        }).then(response =>
            response.status !== 200 ? response.json() : { result: 'Схема успешно загружена! Перейдите в "список схем" чтобы назначить студентов на схему' }
        ).then(response => {
            alert(response.result)
        })
    })
}

export { uploadSchemaListener }