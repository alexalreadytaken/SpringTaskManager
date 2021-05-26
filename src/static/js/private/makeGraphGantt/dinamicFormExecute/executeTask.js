import { acceptTask } from './acceptTask.js'

function showFormExecute ( themeTask ) {

    if (!themeTask) {
        document.getElementById('form_Execute').style.display = 'block'

        let elemForStyle = document.getElementById('body').style 

        elemForStyle.opacity = .7
        elemForStyle.filter = 'grayscale(70%)'
    }

}

export { showFormExecute }