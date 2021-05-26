function createModalForm () {
    document.getElementById('body').insertAdjacentHTML('beforebegin', `
        <div class="form_Execute" id="form_Execute">
            <div id = 'btn_Exit' class = 'btn_Exit'></div>
            <div id = 'flex_Form' class = 'flex_Form'></div>
            <button class = 'modalBtnSave' id = 'modalBtnSave'>Сохранить</button>
        </div>
    `)

    document.addEventListener('click', e => {
        console.log(e.target.id)
        if (e.target.id === 'btn_Exit') {
            document.getElementById('form_Execute').style.display = 'none'
            document.getElementById('body').style.opacity = 1
            document.getElementById('body').style.filter = 'grayscale(0)'
            document.getElementById('flex_Form').innerHTML = ''
        } 
    })
}

export { createModalForm }