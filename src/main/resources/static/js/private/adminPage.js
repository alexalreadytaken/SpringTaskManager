// multiFetch('/admin/schemas/files') // {GET на схемы и 
// multiFetch('/admin/users')         //      юзеров}

let user = {"name":"xml 1.txt","id":"0","values":{"11":{"id":"11","name":"Лекция 1"},"12":{"id":"12","name":"Лекция 2"},"13":{"id":"13","name":"Проект1"},"14":{"id":"14","name":"Тема 2"},"15":{"id":"15","name":"Лекция 1"},"16":{"id":"16","name":"Лекция 2"},"17":{"id":"17","name":"Проект 2"},"0":{"id":"0","name":"Все проекты"},"1":{"id":"1","name":"Предмет 1"},"2":{"id":"2","name":"Квалификации "},"3":{"id":"3","name":"Квалификация 1"},"4":{"id":"4","name":"Квалификация 2"},"5":{"id":"5","name":"Проект 1"},"6":{"id":"6","name":"Тема 1"},"7":{"id":"7","name":"Лекция 1"},"8":{"id":"8","name":"Лекция 2"},"9":{"id":"9","name":"Тренинг 1"},"10":{"id":"10","name":"Тема 2"}},"dependencies":[{"parentId":"9","childId":"3"},{"parentId":"3","childId":"4"},{"parentId":"13","childId":"4"},{"parentId":"4","childId":"5"},{"parentId":"17","childId":"5"},{"parentId":"7","childId":"8"},{"parentId":"7","childId":"9"},{"parentId":"6","childId":"10"},{"parentId":"11","childId":"12"},{"parentId":"11","childId":"13"},{"parentId":"15","childId":"16"},{"parentId":"8","childId":"16"},{"parentId":"15","childId":"17"},{"parentId":"12","childId":"17"},{"parentId":"0","childId":"0"},{"parentId":"0","childId":"1"},{"parentId":"1","childId":"14"},{"parentId":"14","childId":"17"},{"parentId":"14","childId":"16"},{"parentId":"14","childId":"15"},{"parentId":"1","childId":"10"},{"parentId":"10","childId":"13"},{"parentId":"10","childId":"12"},{"parentId":"10","childId":"11"},{"parentId":"1","childId":"6"},{"parentId":"6","childId":"9"},{"parentId":"6","childId":"8"},{"parentId":"6","childId":"7"},{"parentId":"1","childId":"2"},{"parentId":"2","childId":"5"},{"parentId":"2","childId":"4"},{"parentId":"2","childId":"3"}]}

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
      ]}
]

    var treeData = anychart.data.tree (data, 'as-tree')

    var chart = anychart.ganttProject()
    
    chart.data(treeData)
    chart.container('graph-Gant')

    chart.draw()
    chart.fitAll()


// chart.listen('rowClick', e => {
//     console.log(e.target)
// });
  
chart.listen('rowDblClick', e => {
// e.preventDefault();
console.log(e)
});
})
