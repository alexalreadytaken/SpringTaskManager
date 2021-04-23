function showFormExecute ( themeTask ) {

    if (!themeTask) {
        document.getElementById('form_Execute').style.display = 'block'

        document.getElementById('body').style.opacity = .7
        document.getElementById('body').style.filter = 'grayscale(70%)'
        document.getElementById('body').style.zIndex = -2333
    }

}

export { showFormExecute }