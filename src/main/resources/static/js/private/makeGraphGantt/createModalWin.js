function createModalWin (notesTask) {


    document.getElementById('body').insertAdjacentHTML('beforebegin', `
        <div class="form_Execute" id="form_Execute">
            <div id = 'btn_Exit' class = 'btn_Exit'></div>
        </div>
    `)

// bug with create empty elems - #form_Execute

    // document.getElementById('form_Execute').insertAdjacentHTML('beforeend', `
    //     <div id = 'notesTask'></div>
    // `)

    // document.getElementById('notesTask').innerHTML = ''


// fix tag "center", add to class
    
    // document.getElementById('notesTask').insertAdjacentHTML('beforebegin', `
    //     <center>${notesTask}</center>
    // `)


    console.log(notesTask)

    document.addEventListener('click', e => {
        if (e.target.id === 'btn_Exit') {
            document.getElementById('form_Execute').style.display = 'none'
            document.getElementById('body').style.opacity = 1
            document.getElementById('body').style.filter = 'grayscale(0)'
        }
    })

}

export {createModalWin}