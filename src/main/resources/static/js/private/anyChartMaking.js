export default function any (data) {
    var  treeData = anychart.data.tree (data, 'as-tree')

    var chart = anychart.ganttProject()

    chart.data(treeData)
    chart.container('graph-Gant')

    chart.draw()
    chart.fitAll()
    document.getElementsByClassName('anychart-credits')[0].remove() // удаление информации о библиотеке
}

console.log('%c we are using open source library https://www.anychart.com', 'color: yellow; background:black;font-size:15px')
