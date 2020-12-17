


fetch("/admin/tasks").then(response => response.json()).then(response =>{
    let tasksList = document.getElementById("tasksList");

    response.forEach(el=>{
        let div = document.createElement('div');
        let fileDownload = document.createElement('a')
        let file = el.filename
        fileDownload.innerText=file
        fileDownload.setAttribute('href',`admin/tasks/schema/${file}`)
        fileDownload.setAttribute('download',file)
        div.append(fileDownload)
        tasksList.append(div)
    })
})

fetch("/admin/users").then(response => response.json()).then(response => {

    let userList = document.getElementById("UsersList")
    response.forEach(element => {
        let user ='<div style="border: 1px solid black">'
        let hrefToUser = ''
        Object.entries(element).reverse().forEach(el=>{
            user+='<h4>'+el[0]+':'+el[1]+'</h4>'
        })
        user+=hrefToUser
        user+='</div>'
        userList.innerHTML+=user
    })
})
document.querySelector('#file').addEventListener('change',evt => {
    let fileData = evt.target["files"]
    let formData = new FormData()
    formData.append('file',fileData[0])
    evt.target.value=''
    fetch('/admin/addTasks',{
        method:'POST',
        body:formData
    }).then(response=>
        response.status!==200? response.json():{result:'загружено успешно'}
    ).then(response=>{
        alert(response.result)
    })
})
