formElem.onsubmit =(e) => {
    e.preventDefault();
    let object = {};
    new FormData(formElem)
        .forEach((value, key) => object[key] = value);
    let json = JSON.stringify(object);
    fetch('/reg', {
        method: 'POST',
        body: json,
        headers:{'content-type': 'application/json'}
    }).then(response=>response.json())
        .then(response=>console.log(response))
};
