let tasksArr = []
let dependencies = []

document.querySelector('#file').addEventListener('change',evt => {
    let fileData = evt.target["files"]
    let formData = new FormData()
    formData.append('file',fileData[0])
    evt.target.value=''
    fetch('/admin/addTasks',{
        method:'POST',
        body:formData
    }).then(response=>response.json())
        .then(response=>{
            let taskDependencyList = response.taskDependencyList;
            taskDependencyList.sort((a,b)=>{
                return a.taskParentId - b.taskParentId;
            });
            console.log(response)
        })
})

fetch("/admin/users").then(response => response.json()).then(response => {
    let userList = document.getElementById("UsersList")
    response.forEach(element => {
        let user ='<div style="border: 1px solid black">'
        let hrefToUser = ''
        Object.entries(element).reverse().forEach(el=>{
            user+='<h4>'+el[0]+':'+el[1]+'</h4>'
            if (el[0]==='id'){
                hrefToUser='<a href="/admin/users/'+el[1]+'">more info</a>'
            }
        })
        user+=hrefToUser
        user+='</div>'
        userList.innerHTML+=user
    })
})