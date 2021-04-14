export default function chartMaking (data, rootTask) {
    var treeData = anychart.data.tree (data, 'as-tree')

    var chart = anychart.ganttProject()
// ------------------------ title and font-settings
    var title = chart.title()

    title.enabled (true)
    title.text (rootTask)
    
    title.fontColor("#64b5f6");
    title.fontSize(20);
    title.fontWeight(600);
    title.padding(5);

// =========================
//------- pos splitter ----- 
    chart.splitterPosition('15%')
// ==========================

// ---- turn on custom field
    var column_1 = chart.dataGrid().column(0);
    column_1.labels().fontColor("#64b5f6");
    column_1.labels().fontWeight(600);
    column_1.labels().format("{%linearIndex}.");

// =========================
    
    chart.data(treeData)
    chart.container('graph-Gant')

    chart.draw()
    chart.fitAll()
    document.getElementsByClassName('anychart-credits')[0].remove() // удаление информации о библиотеке
}

console.log('%c we are using open source library https://www.anychart.com', 'color: yellow; background:black;font-size:15px')