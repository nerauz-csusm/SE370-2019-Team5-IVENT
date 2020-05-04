const add_column = (event, row_id) => {
    $(`#${row_id}`).append(`
        <div class="col d-flex justify-content-center text-center mt-3 mb-3">
            <div class="card" style="width: 18rem;">
                <img src="${event.images[0].url}" class="card-img-top" alt="Image of the event">
                <div class="card-body">
                    <p class="card-text">${event.name}</p>
                </div>
            </div>
        </div>    
    `);
};


const add_events_to_page = (events) => {
    let row_number = 1;
    let row_id;

    events.forEach((event, index) => {
        if ((index % 3) == 0) {
            row_id = `row-${row_number}`;
            $("#index-container").before(`<div class='row' id="${row_id}"></div>`)
            row_number++;
        }

        add_column(event, row_id);
  });
};

const filterCallback = (event, eventNames) => {
    const isIncluded = eventNames.includes(event.name);

    if (!isIncluded)
        eventNames.push(event.name);

    return !isIncluded;
}

$(() => {
    axios.get("https://app.ticketmaster.com/discovery/v2/events?countryCode=US&apikey=l84bOGkhL4pmDdQQ2yzJGQAkoldSX3aW&sort=name,asc&size=200")
        .then((res) => {
            let eventNames = [];
            let filteredEvents = res.data['_embedded'].events.filter((event) => filterCallback(event, eventNames));

            add_events_to_page(filteredEvents);
        })
        .catch((error) => {
            console.log(error);
        });
});