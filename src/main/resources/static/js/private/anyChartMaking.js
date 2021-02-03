console.log('%cwe are using open source library https://www.anychart.com', 'color: yellow; background:black;font-size:15px')

makeAnyChart = () => {
    anychart.onDocumentReady(() => {
      console.log(infoList)
  
      let data = [...infoList]
  
      var treeData = anychart.data.tree (data, 'as-tree')
  
      var chart = anychart.ganttProject()
      
      chart.data(treeData)
      chart.container('graph-Gant')
  
      chart.draw()
      chart.fitAll()
  
      document.getElementsByClassName('anychart-credits')[0].remove() // удаление информации о библиотеке
    })
  }