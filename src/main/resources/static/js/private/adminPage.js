// multiFetch('/admin/schemas/files') // {GET на схемы и 
// multiFetch('/admin/users')         //      юзеров}

let user = {"name":"Задачка1.mrp","id":"0","tasks":{"11":{"id":"11","name":"Лекция 1","fields":{"Вид":"Теория"},"duration":0,"notes":"<p> Заметки к лекции 1 </p>","parentsId":["10"],"childrenId":["12","13"],"startDate":"2020-11-18 11:45:00","endDate":"2020-11-18 12:30:00","constraint":null,"theme":false},"12":{"id":"12","name":"Лекция 2","fields":{"Вид":"Теория","Тип":"Самостоятельное","Формат":"Online"},"duration":0,"notes":"<p> Заметки к лекции 2 </p>","parentsId":["11","10"],"childrenId":["17"],"startDate":"2020-11-18 12:30:00","endDate":"2020-11-18 13:15:00","constraint":null,"theme":false},"13":{"id":"13","name":"Проект1","fields":{"Вид":"Практика","Тип":"Самостоятельное"},"duration":0,"notes":"<p> План практики 1 </p>","parentsId":["11","10"],"childrenId":["4"],"startDate":"2020-11-18 12:30:00","endDate":"2020-11-18 15:30:00","constraint":null,"theme":false},"14":{"id":"14","name":"Тема 2","fields":null,"duration":0,"notes":null,"parentsId":["1"],"childrenId":["15","16","17"],"startDate":"2020-11-18 08:00:00","endDate":"2020-11-18 16:15:00","constraint":null,"theme":true},"15":{"id":"15","name":"Лекция 1","fields":{"Вид":"Теория","Тип":"Интеракив","Формат":"Offline"},"duration":0,"notes":"<p> Заметки к лекции 1 </p>","parentsId":["14"],"childrenId":["16","17"],"startDate":"2020-11-18 08:00:00","endDate":"2020-11-18 08:45:00","constraint":null,"theme":false},"16":{"id":"16","name":"Лекция 2","fields":{"Вид":"Теория"},"duration":0,"notes":"<p> Заметки к лекции 2 </p>","parentsId":["15","8","14"],"childrenId":[],"startDate":"2020-11-18 09:30:00","endDate":"2020-11-18 10:15:00","constraint":null,"theme":false},"17":{"id":"17","name":"Проект 2","fields":{"Вид":"Практика","Тип":"Самостоятельное"},"duration":0,"notes":"<p> План практики 1 </p>","parentsId":["15","12","14"],"childrenId":["5"],"startDate":"2020-11-18 13:15:00","endDate":"2020-11-18 16:15:00","constraint":null,"theme":false},"0":{"id":"0","name":"Все проекты","fields":null,"duration":0,"notes":null,"parentsId":[],"childrenId":["1"],"startDate":"2020-11-18 08:00:00","endDate":"2020-11-18 16:15:00","constraint":null,"theme":true},"1":{"id":"1","name":"Предмет 1","fields":null,"duration":0,"notes":null,"parentsId":["0"],"childrenId":["2","6","10","14"],"startDate":"2020-11-18 08:00:00","endDate":"2020-11-18 16:15:00","constraint":null,"theme":true},"2":{"id":"2","name":"Квалификации ","fields":null,"duration":0,"notes":null,"parentsId":["1"],"childrenId":["3","4","5"],"startDate":"2020-11-18 11:45:00","endDate":"2020-11-18 16:15:00","constraint":null,"theme":true},"3":{"id":"3","name":"Квалификация 1","fields":{"Вид":"Навык"},"duration":0,"notes":null,"parentsId":["9","2"],"childrenId":["4"],"startDate":"2020-11-18 11:45:00","endDate":"2020-11-18 11:45:00","constraint":null,"theme":false},"4":{"id":"4","name":"Квалификация 2","fields":{"Вид":"Навык"},"duration":0,"notes":null,"parentsId":["3","13","2"],"childrenId":["5"],"startDate":"2020-11-18 15:30:00","endDate":"2020-11-18 15:30:00","constraint":null,"theme":false},"5":{"id":"5","name":"Проект 1","fields":{"Вид":"Продукт"},"duration":0,"notes":null,"parentsId":["4","17","2"],"childrenId":[],"startDate":"2020-11-18 16:15:00","endDate":"2020-11-18 16:15:00","constraint":null,"theme":false},"6":{"id":"6","name":"Тема 1","fields":null,"duration":0,"notes":null,"parentsId":["1"],"childrenId":["10","7","8","9"],"startDate":"2020-11-18 08:00:00","endDate":"2020-11-18 11:45:00","constraint":null,"theme":true},"7":{"id":"7","name":"Лекция 1","fields":{"Вид":"Теория","Тип":"Интерактив","Формат":"Online"},"duration":0,"notes":"<p> Заметки к лекции 1 </p>","parentsId":["6"],"childrenId":["8","9"],"startDate":"2020-11-18 08:00:00","endDate":"2020-11-18 08:45:00","constraint":null,"theme":false},"8":{"id":"8","name":"Лекция 2","fields":{"Вид":"Теория"},"duration":0,"notes":"<p> Заметки к лекции 2 </p>","parentsId":["7","6"],"childrenId":["16"],"startDate":"2020-11-18 08:45:00","endDate":"2020-11-18 09:30:00","constraint":null,"theme":false},"9":{"id":"9","name":"Тренинг 1","fields":{"Вид":"Практика","Тип":"Самостоятельное"},"duration":0,"notes":"<p> План практики 1 </p>","parentsId":["7","6"],"childrenId":["3"],"startDate":"2020-11-18 08:45:00","endDate":"2020-11-18 11:45:00","constraint":null,"theme":false},"10":{"id":"10","name":"Тема 2","fields":null,"duration":0,"notes":null,"parentsId":["6","1"],"childrenId":["11","12","13"],"startDate":"2020-11-18 11:45:00","endDate":"2020-11-18 15:30:00","constraint":null,"theme":true}}}

anychart.onDocumentReady(() => {
    let data = [{
        id: "1",
        name: "Development",
        actualStart: "2018-01-15",
        actualEnd: "2018-03-10",
        children: [
          {
            id: "1_1",
            name: "Analysis",
            actualStart: "2018-01-15",
            actualEnd: "2018-01-25"
          },
          {
            id: "1_2",
            name: "Design",
            actualStart: "2018-01-20",
            actualEnd: "2018-02-04"
          },
          {
            id: "1_3",
            name: "Meeting",
            actualStart: "2018-02-05",
            actualEnd: "2018-02-05"
          },
          {
            id: "1_4",
            name: "Implementation",
            actualStart: "2018-02-05",
            actualEnd: "2018-02-24"
          },
          {
            id: "1_5",
            name: "Testing",
            actualStart: "2018-02-25",
            actualEnd: "2018-03-10"
          }
      ]
    }, 
    {
      
      id: "2",
        name: "Sanya",
        actualStart: "2018-02-29",
        actualEnd: "2018-05-10",
        children: [
          {
            id: "2_1",
            name: "Analysis",
            actualStart: "2018-02-29",
            actualEnd: "2018-02-31"
          },
          {
            id: "2_2",
            name: "Design",
            actualStart: "2018-03-31",
            actualEnd: "2018-04-25"
          },
          {
            id: "2_3",
            name: "Meeting",
            actualStart: "2018-04-25",
            actualEnd: "2018-04-25"
          },
          {
            id: "2_4",
            name: "Implementation",
            actualStart: "2018-04-25",
            actualEnd: "2018-05-04"
          },
          {
            id: "2_5",
            name: "Testing",
            actualStart: "2018-05-04",
            actualEnd: "2018-05-10"
          }]
    }
]

    var treeData = anychart.data.tree (data, 'as-tree')

    var chart = anychart.ganttProject()
    
    chart.data(treeData)
    chart.container('graph-Gant')

    chart.draw()
    chart.fitAll()


  chart.listen('rowClick', e => {
    console.log(e.target)
  });
  
  chart.listen('rowDblClick', e => {
    console.log(e)
  })

  document.getElementsByClassName('anychart-credits')[0].remove() // удаление информации о библиотеке
})

let xhr = new XMLHttpRequest()
xhr.open('GET', 'http://10.1.0.64:2000/admin/schemas/0', true)

xhr.onload = () => {
  let user = JSON.parse(xhr.response)
  console.log(user)
  let some = Object.entries(user.tasks).map(el=>el[1])
  console.log(some);
}

xhr.send()