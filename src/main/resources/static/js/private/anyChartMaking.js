export default function any (data) {
    var  treeData = anychart.data.tree (data, 'as-tree')

    var chart = anychart.ganttProject()
// ------------------------ title and font-settings
    var title = chart.title()

    title.enabled (true)
    title.text ('Учебный план')
    title.fontColor("#64b5f6");
    title.fontSize(20);
    title.fontWeight(600);
    title.padding(5);

// =========================
//------- pos splitter ----- 
    chart.splitterPosition('20%')
// =========================
    
    chart.data(treeData)
    chart.container('graph-Gant')

    chart.draw()
    chart.fitAll()
    document.getElementsByClassName('anychart-credits')[0].remove() // удаление информации о библиотеке
}

// console.log('%c we are using open source library https://www.anychart.com', 'color: yellow; background:black;font-size:15px')
