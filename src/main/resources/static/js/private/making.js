import any from './anyChartMaking.js';
export default function makeGraph(arrData) {
    var data = []

    console.log(arrData);
    let dataPars = Object.entries(arrData.tasksMap).map(el => el[1])
    
    dataPars.forEach(el => (el.theme) ? data.push(el) : console.log('Error!'))
    console.log(data);

    any(data)
}