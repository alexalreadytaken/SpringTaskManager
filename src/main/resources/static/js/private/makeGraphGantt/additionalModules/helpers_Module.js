import {makeGraph} from '../parsingData/making.js';
import {makePercent} from '../parsingData/percentForTasks.js';
import {parsTask} from '../parsingData/parsFields.js';

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

function multiFetch (url) {
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
            
            case 'http://10.3.0.87:2000/schemas/master/Предмет_1':
                makeGraph(result)
            break

        }
    })
}

async function getSummary ({url, tasks}) {
   const response = await fetch(url);
    const result_1 = await response.json();
    return makePercent({
        data: parsTask(tasks),
        summary: result_1
    });
}

export {makeFileList, makeUserList, multiFetch, getSummary}
