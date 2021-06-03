import { config } from "../config.js"

document.querySelector('#uploadSchema').addEventListener('change', evt => {
    let fileData = evt.target["files"]
    let formData = new FormData()
    formData.append('file',fileData[0])
    evt.target.value = ''
    fetch(`http://${config.url}/schemas/files`,{
        method:'POST',
        body:formData
    }).then(response=>
        response.status!==200? response.json():{result:'Загрузил!'}
    ).then(response=>{
        alert(response.result)
    })
})
