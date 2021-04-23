function createModalForm (notesTask) {
    document.getElementById('body').insertAdjacentHTML('beforebegin', `
        <div class="form_Execute" id="form_Execute">
            <div id = 'btn_Exit' class = 'btn_Exit'></div>
            <div id = 'notesTask'></div>
        </div>
    `)

    document.addEventListener('click', e => {
        if (e.target.id === 'btn_Exit') {
            document.getElementById('form_Execute').style.display = 'none'
            document.getElementById('body').style.opacity = 1
            document.getElementById('body').style.filter = 'grayscale(0)'
            document.getElementById('notesTask').innerHTML = ''
        }
    })

}

export { createModalForm }