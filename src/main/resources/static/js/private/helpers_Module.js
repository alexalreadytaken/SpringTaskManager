import makeGraph from './making.js'

export function makeFileList (fileNames) {
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

export function makeUserList (users) {
    let userList = document.getElementById("UsersList")
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

export function multiFetch (url) {
    fetch( url )
    .then(response => response.json())
    .then(result => {
        switch ( url ) {
            case '/schemas/master/files':
                makeFileList(result)
            break
            
            case '/admin/users':
                makeUserList(result)
            break
            
            case 'http://10.1.0.64:2000/schemas/master/Предмет_1':
                makeGraph(result)
            break
        }
    })
}
