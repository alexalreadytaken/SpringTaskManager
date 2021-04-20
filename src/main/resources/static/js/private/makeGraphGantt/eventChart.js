// import {} from './chartMaking.js'

function eventChart (chart) {
    chart.listen ("rowClick", e => {
        const itemName = e.item.get("id") // name, id, notes === any get item for obj
        console.log(itemName) 
    })
}

export {eventChart}