function addNotesForTask (notesTask) {
    document.getElementById('notesTask')
        .insertAdjacentHTML('beforeend', `
            <center>${notesTask}</center>
        `)
}

export { addNotesForTask }