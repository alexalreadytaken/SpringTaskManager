// multiFetch('/admin/schemas/files')  //GET на схемы и 
// multiFetch('/admin/users')                 // юзеров
multiFetch('http://10.1.0.64:2000/admin/schemas/0')

let user = {"name":"Задачка1.mrp","id":"0","dependencies":[{"parentId":"9","childId":"3"},{"parentId":"3","childId":"4"},{"parentId":"13","childId":"4"},{"parentId":"4","childId":"5"},{"parentId":"17","childId":"5"},{"parentId":"7","childId":"8"},{"parentId":"7","childId":"9"},{"parentId":"6","childId":"10"},{"parentId":"11","childId":"12"},{"parentId":"11","childId":"13"},{"parentId":"15","childId":"16"},{"parentId":"8","childId":"16"},{"parentId":"15","childId":"17"},{"parentId":"12","childId":"17"}],"tasks":{"11":{"id":"11","name":"Лекция 1","fields":{"Вид":"Теория"},"duration":0,"notes":"<p> Заметки к лекции 1 </p>","parentsId":["10"],"childrenId":["12","13"],"constraint":null,"theme":false,"actualStart":"2020-11-18 11:45:00","actualEnd":"2020-11-18 12:30:00"},"12":{"id":"12","name":"Лекция 2","fields":{"Вид":"Теория","Тип":"Самостоятельное","Формат":"Online"},"duration":0,"notes":"<p> Заметки к лекции 2 </p>","parentsId":["11","10"],"childrenId":["17"],"constraint":null,"theme":false,"actualStart":"2020-11-18 12:30:00","actualEnd":"2020-11-18 13:15:00"},"13":{"id":"13","name":"Проект1","fields":{"Вид":"Практика","Тип":"Самостоятельное"},"duration":0,"notes":"<p> План практики 1 </p>","parentsId":["11","10"],"childrenId":["4"],"constraint":null,"theme":false,"actualStart":"2020-11-18 12:30:00","actualEnd":"2020-11-18 15:30:00"},"14":{"id":"14","name":"Тема 2","fields":null,"duration":0,"notes":null,"parentsId":["1"],"childrenId":["15","16","17"],"constraint":null,"theme":true,"actualStart":"2020-11-18 08:00:00","actualEnd":"2020-11-18 16:15:00"},"15":{"id":"15","name":"Лекция 1","fields":{"Вид":"Теория","Тип":"Интеракив","Формат":"Offline"},"duration":0,"notes":"<p> Заметки к лекции 1 </p>","parentsId":["14"],"childrenId":["16","17"],"constraint":null,"theme":false,"actualStart":"2020-11-18 08:00:00","actualEnd":"2020-11-18 08:45:00"},"16":{"id":"16","name":"Лекция 2","fields":{"Вид":"Теория"},"duration":0,"notes":"<p> Заметки к лекции 2 </p>","parentsId":["15","8","14"],"childrenId":[],"constraint":null,"theme":false,"actualStart":"2020-11-18 09:30:00","actualEnd":"2020-11-18 10:15:00"},"17":{"id":"17","name":"Проект 2","fields":{"Вид":"Практика","Тип":"Самостоятельное"},"duration":0,"notes":"<p> План практики 1 </p>","parentsId":["15","12","14"],"childrenId":["5"],"constraint":null,"theme":false,"actualStart":"2020-11-18 13:15:00","actualEnd":"2020-11-18 16:15:00"},"0":{"id":"0","name":"Все проекты","fields":null,"duration":0,"notes":null,"parentsId":[],"childrenId":["1"],"constraint":null,"theme":true,"actualStart":"2020-11-18 08:00:00","actualEnd":"2020-11-18 16:15:00"},"1":{"id":"1","name":"Предмет 1","fields":null,"duration":0,"notes":null,"parentsId":["0"],"childrenId":["2","6","10","14"],"constraint":null,"theme":true,"actualStart":"2020-11-18 08:00:00","actualEnd":"2020-11-18 16:15:00"},"2":{"id":"2","name":"Квалификации ","fields":null,"duration":0,"notes":null,"parentsId":["1"],"childrenId":["3","4","5"],"constraint":null,"theme":true,"actualStart":"2020-11-18 11:45:00","actualEnd":"2020-11-18 16:15:00"},"3":{"id":"3","name":"Квалификация 1","fields":{"Вид":"Навык"},"duration":0,"notes":null,"parentsId":["9","2"],"childrenId":["4"],"constraint":null,"theme":false,"actualStart":"2020-11-18 11:45:00","actualEnd":"2020-11-18 11:45:00"},"4":{"id":"4","name":"Квалификация 2","fields":{"Вид":"Навык"},"duration":0,"notes":null,"parentsId":["3","13","2"],"childrenId":["5"],"constraint":null,"theme":false,"actualStart":"2020-11-18 15:30:00","actualEnd":"2020-11-18 15:30:00"},"5":{"id":"5","name":"Проект 1","fields":{"Вид":"Продукт"},"duration":0,"notes":null,"parentsId":["4","17","2"],"childrenId":[],"constraint":null,"theme":false,"actualStart":"2020-11-18 16:15:00","actualEnd":"2020-11-18 16:15:00"},"6":{"id":"6","name":"Тема 1","fields":null,"duration":0,"notes":null,"parentsId":["1"],"childrenId":["10","7","8","9"],"constraint":null,"theme":true,"actualStart":"2020-11-18 08:00:00","actualEnd":"2020-11-18 11:45:00"},"7":{"id":"7","name":"Лекция 1","fields":{"Вид":"Теория","Тип":"Интерактив","Формат":"Online"},"duration":0,"notes":"<p> Заметки к лекции 1 </p>","parentsId":["6"],"childrenId":["8","9"],"constraint":null,"theme":false,"actualStart":"2020-11-18 08:00:00","actualEnd":"2020-11-18 08:45:00"},"8":{"id":"8","name":"Лекция 2","fields":{"Вид":"Теория"},"duration":0,"notes":"<p> Заметки к лекции 2 </p>","parentsId":["7","6"],"childrenId":["16"],"constraint":null,"theme":false,"actualStart":"2020-11-18 08:45:00","actualEnd":"2020-11-18 09:30:00"},"9":{"id":"9","name":"Тренинг 1","fields":{"Вид":"Практика","Тип":"Самостоятельное"},"duration":0,"notes":"<p> План практики 1 </p>","parentsId":["7","6"],"childrenId":["3"],"constraint":null,"theme":false,"actualStart":"2020-11-18 08:45:00","actualEnd":"2020-11-18 11:45:00"},"10":{"id":"10","name":"Тема 2","fields":null,"duration":0,"notes":null,"parentsId":["6","1"],"childrenId":["11","12","13"],"constraint":null,"theme":true,"actualStart":"2020-11-18 11:45:00","actualEnd":"2020-11-18 15:30:00"}}}

makeGraph = (arrData) => {
  let arrRes = Object.entries(arrData.tasks).map(el=>el[1])

  arrRes.splice(0,1) // в дальнейшем можно будет удалить  TODO

  console.log(arrRes) // вывод всех элементов (Json)

  arrRes.forEach(el => { if ( el.theme ) {data.push(el)}}) // создание отцов

  arrRes.forEach(el => {
    allAddiction.push({
      id: el.id,
      name: el.name,
      actualStart: el.actualStart,
      actualEnd: el.actualEnd,
      // children: el.childrenId, // связь к детям
    })
  })

  // data.forEach((el, i) => {
  //   console.log(el.childrenId[i])
  //   if (el.childrenId[i] === allAddiction.id) {
  //     console.log(el.childrenId) // добавить прохождение по циклу еще 17 раз
  //     el.children = [allAddiction[i]]
  //   }
  // })


  allAddiction.forEach( (el1, i) => { // ищем совместимость родителей и детей
    data.forEach(el2 => {
      if (el1.id === el2.childrenId[0]){  // баг с нахождением связи детей "el2.childreId[0]" TODO
        el2.children = [el1]
      }
    })
  })


  console.log(allAddiction)
}

makeGraph(user)

// console.log(Object.entries(user.tasks).map(el=>el[1]))