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

//-------- timeline style ------------- 
    
    // ---- turn on custom field
    var column_1 = chart.dataGrid().column(0);
    column_1.labels().fontColor("#64b5f6");
    column_1.labels().fontWeight(600);
    column_1.labels().format("{%linearIndex}.");

    
    // var column_2 = chart.dataGrid().column(1);
    // column_2.labels().useHtml(true);
    // column_2.labels().format(
    //   "<span style='color:#dd2c00;font-weight:bold'>{%custom_field} </span>" +
    //   "{%name}: <span style='color:#64b5f6'>{%progress}</span>"
    // );
// =========================
    
    chart.data(treeData)
    chart.container('graph-Gant')

    chart.draw()
    chart.fitAll()
    document.getElementsByClassName('anychart-credits')[0].remove() // удаление информации о библиотеке
}

// console.log('%c we are using open source library https://www.anychart.com', 'color: yellow; background:black;font-size:15px')