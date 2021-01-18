console.log('%cwe are using open source library https://plotly.com', 'color: yellow; background:black;font-size:15px');


fetch("/admin/schemas/0").then(response=>response.json()).then(response=>{

    // Object.entries(response).map(el=>el[1])


    let labels = Object.entries(response.tasksMap).map(el=>el[1]).map(el=>`${el.name} (${el.id})`);

    makeGraph(labels,response.tasksDependencies)
    let cl = document.querySelectorAll('rect.node-capture')

    for (let i = 0; i < cl.length; i++) {
        cl[i].setAttribute('id', 'elem')
    }

    document.addEventListener('mouseup', (e) => {
        if (e.target.id.includes('elem') === true) {
            console.log('Zaebisc!')
        }
    })
})

fetch("/admin/schemas/files").then(response => response.json()).then(response =>{
    let schemasFileList = document.getElementById("schemasFileList");
    response.forEach(file=>{
        let div = document.createElement('div');
        div.style.margin='10px'
        let fileDownload = document.createElement('a')
        fileDownload.innerText=file
        fileDownload.setAttribute('href',`admin/schemas/file/${file}`)
        fileDownload.setAttribute('download',file)
        div.append(fileDownload)
        schemasFileList.append(div)
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



class Dependency {
    constructor(parentId, childId) {
        this.parentId = parentId
        this.childId = childId
    }
}
makeGraph = (labels,jsonDependenciesArr) =>{
    jsonDependenciesArr
        .map(el=>new Dependency(Number(el.parentId),Number(el.childId)))
        .sort((a,b)=>a.parentId-b.parentId)

    const data = [{
        type: "sankey",
        node: {
            label: [...labels]
        },
        link: {
            source: [...jsonDependenciesArr.map(el=>el.parentId)],
            target: [...jsonDependenciesArr.map(el=>el.childId)],
            value: [...jsonDependenciesArr.map(()=>1)],
        }
    }];
    Plotly.newPlot('graph', data)
}
