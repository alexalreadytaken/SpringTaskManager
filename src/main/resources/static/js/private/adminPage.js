


fetch("/admin/schemas/tasks").then(response=>response.json()).then(response=>{
    makeGraph(response)
})

fetch("/admin/schemas/files").then(response => response.json()).then(response =>{
    let tasksList = document.getElementById("tasksList");
    response.forEach(file=>{
        let div = document.createElement('div');
        div.style.margin='10px'
        let fileDownload = document.createElement('a')
        fileDownload.innerText=file
        fileDownload.setAttribute('href',`admin/schema/${file}`)
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
    fetch('/admin/schemas/add',{
        method:'POST',
        body:formData
    }).then(response=>
        response.status!==200? response.json():{result:'загружено успешно'}
    ).then(response=>{
        alert(response.result)
    })
})


makeGraph = (jsonResponse) =>{
    class Dependency{
        constructor(parentId,childId) {
            this.parentId=parentId
            this.childId=childId
        }
    }

    var dependencies = []

    for (let i = 0; i < jsonResponse.length; i++) {
        let parentId = jsonResponse[i].parentId;
        let childId = jsonResponse[i].childId;
        if (parentId !== childId) {
            dependencies.push(new Dependency(Number(parentId),Number(childId)))
        }
    }
    dependencies.sort((a,b)=>a.parentId-b.parentId)

    var data = [{
        type: "sankey",
        arrangement: "snap",
        node:{
            label: [...new Set(dependencies.map(el=>el.parentId))],
            pad:30},
        link: {
            source: [...dependencies.map(el=>el.parentId)],
            target: [...dependencies.map(el=>el.childId)],
            value: [...dependencies.map(()=>1)]
        }
    }]
    Plotly.newPlot('graph', data)

    let cl = document.querySelectorAll('rect.node-capture')

    for (let i = 0; i < cl.length; i++) {
        cl[i].setAttribute('id', 'elem' + data[0].node.label[i])
    }

    document.addEventListener('mouseup', (e) => {
        if (e.target.id.includes('elem') === true) {
            console.log('Zaebisc!')
            /* for (let i = 0; i < data[0].node.label.length; i++ ) {
                 console.log('12312321312')
             }*/
        }
    })
}
