console.log('%cwe are using open source library https://plotly.com', 'color: yellow; background:black;font-size:15px');

let actions = document.querySelector('#element-actions');
let userList = document.getElementById("UsersList")

universalfunc = (url) => {
    fetch( url)
        .then(response => response.json())
        .then(result => {
            switch ( url ) {
                case '/admin/schemas/':
                    firstGraph(result)
                    break;
                case '/admin/schemas/files':
                    makeFileList(result)
                    break;
                case '/admin/users':
                    makeUserList(result)
                    break;
            }
        })
}

universalfunc('/admin/schemas/')
universalfunc('/admin/schemas/files')
universalfunc('/admin/users')

firstGraph = (response) => {
    let labels = Object.entries(response.schemas).map(el=>el[1]).map(el=>`${el.name} id=${el.id}`);

    makeGraph(labels,response.dependencies,'Схемы')
    let cl = document.querySelectorAll('g.sankey-node')
    cl.forEach(el=>{
        let childText = el.querySelector(".node-label-text-path");
        let schemeId = childText.innerHTML.split("=")[1];

        el.setAttribute('onmouseup', `schemeActions(${schemeId})`)
    })
}

schemeActions = (schemeId) => {
    actions.innerHTML=`<button onclick="schemeInfo(${schemeId})">Подробности</button>`
}
schemeInfo = (schemeId) => {
    actions.innerHTML=''
    fetch(`/admin/schemas/${schemeId}`).then(response=>response.json()).then(response=>{

        let labels = Object.entries(response.tasksMap).map(el=>el[1]).map(el=>`${el.name} id=${el.id}`);
        makeGraph(labels,response.tasksDependencies,response.name)
    })

}
makeFileList = (response) => {
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
}

makeUserList = (response) => {
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
}

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

makeGraph = (labels,jsonDependenciesArr,title) =>{
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

    const layout = {"title": title};
    Plotly.newPlot('graph', data,layout)
}
class Dependency {
    constructor(parentId, childId) {
        this.parentId = parentId
        this.childId = childId
    }
}