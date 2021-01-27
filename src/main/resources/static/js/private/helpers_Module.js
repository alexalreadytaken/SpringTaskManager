let userList = document.getElementById("UsersList")

function makeFileList (fileNames) {
        let schemasFileList = document.getElementById("schemasFileList");
        fileNames.forEach(file=>{
            let div = document.createElement('div');
            div.style.margin='10px'
            let fileDownload = document.createElement('a')
            fileDownload.innerText=file
            fileDownload.setAttribute('href',`/admin/schemas/file/${file}`)
            fileDownload.setAttribute('download',file)
            div.append(fileDownload)
            schemasFileList.append(div)
        })
    }
    
function makeUserList (users) {
    users.forEach(user => {
        let userContainer ='<div style="border: 1px solid black">'
        let hrefToUser = ''
        Object.entries(user).reverse().forEach(el=>{
            userContainer+='<h4>'+el[0]+':'+el[1]+'</h4>'
        })
        userContainer+=hrefToUser
        userContainer+='</div>'
        userList.innerHTML+=userContainer
    })
}

document.getElementById('file').addEventListener('change', evt => { // #file in future maybe change (fixed...)
    let fileData = evt.target["files"]
    let formData = new FormData()
    formData.append('file',fileData[0])
    evt.target.value = ''
    fetch('/',{ // TODO ИЗМЕНИТЬ URL 
        method:'POST',
        body: formData
    })  
    .then(response=>
        response.status!==200 ? response.json():{result:'загружено успешно'})
    .then(response=>{
        alert(response.result)
    })
})