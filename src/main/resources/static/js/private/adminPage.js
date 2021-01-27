console.log('%cwe are using open source library https://www.anychart.com', 'color: yellow; background:black;font-size:15px');

multiFetch = (url) => {
    fetch( url )
        .then(response => response.json())
        .then(result => {
            switch ( url ) {
                case '/admin/schemas/files':
                    makeFileList(result)
                    break;
                case '/admin/users':
                    makeUserList(result)
                    break;
            }
        })
}

multiFetch('/admin/schemas/')
multiFetch('/admin/schemas/files')
multiFetch('/admin/users')

