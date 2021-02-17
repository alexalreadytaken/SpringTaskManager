console.log('%cwe are using open source library https://plotly.com', 'color: yellow; background:black;font-size:15px');

let actions = document.querySelector('#element-actions');
let userList = document.getElementById("UsersList")

multiFetch = (url) => {
    fetch( url )
        .then(response => response.json())
        .then(result => {
            switch ( url ) {
                case '/schemas/':
                    firstGraph(result)
                    break;
                case '/schemas/master/files':
                    makeFileList(result)
                    break;
                case '/admin/users/':
                    makeUserList(result)
                    break;
            }
        })
}

// multiFetch('/schemas/')
multiFetch('/schemas/master/files')
multiFetch('/admin/users/')

firstGraph = (response) => {
    makeGraph(response,'Схемы')
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
    fetch(`/schemas/${schemeId}`).then(response=>response.json()).then(response=>{
        makeGraph(response,response.name)
    })

}

makeFileList = (fileNames) => {
    let schemasFileList = document.getElementById("schemasFileList");
    fileNames.forEach(file=>{
        let div = document.createElement('div');
        div.style.margin='10px'
        let fileDownload = document.createElement('a')
        fileDownload.innerText=file
        fileDownload.setAttribute('href',`/schemas/file/${file}`)
        fileDownload.setAttribute('download',file)
        div.append(fileDownload)
        schemasFileList.append(div)
    })
}

makeUserList = (users) => {
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

document.querySelector('#file').addEventListener('change',evt => {
    let fileData = evt.target["files"]
    let formData = new FormData()
    formData.append('file',fileData[0])
    evt.target.value=''
    fetch('/schemas/master/files/add',{
        method:'POST',
        body:formData
    }).then(response=>
        response.status!==200? response.json():{result:'загружено успешно'}
    ).then(response=>{
        alert(response.result)
    })
})

makeGraph = (jsonResponse,title) =>{
    let labels = Object.entries(jsonResponse.values).map(el=>el[1]).map(el=>`${el.name} id=${el.id}`);
    let dependencies = jsonResponse.dependencies;

    const data = [{
        type: "sankey",
        node: {
            label: [...labels]
        },
        link: {
            source: [...dependencies.map(el=>el.parentId)],
            target: [...dependencies.map(el=>el.childId)],
            value: [...dependencies.map(()=>1)],
        }
    }];

    const layout = {"title": title};
    Plotly.newPlot('graph', data,layout)
}
