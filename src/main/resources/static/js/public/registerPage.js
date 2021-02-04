formElem.onsubmit =(e) => {
    e.preventDefault();
    let object = {};
    new FormData(formElem)
        .forEach((value, key) => object[key] = value);
    let json = JSON.stringify(object);
    fetch('/register/new', {
        method: 'POST',
        body: json,
        headers:{'content-type': 'application/json'}
    }).then(response=>response.json())
        .then(response=>alert(response))
};
