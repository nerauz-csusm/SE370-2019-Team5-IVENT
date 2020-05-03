const add_column = (event, row_id) => {
    $(`#${row_id}`).append(`
        <div class="col">
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
        if ((index % 4) == 0) {
            row_id = `row-${row_number}`;
            $("#index-container").before(`<div class='row' id="${row_id}"></div>`)
            row_number++;
        }

        add_column(event, row_id);
  });
};

$(() => {
    axios.get("https://app.ticketmaster.com/discovery/v2/events.json?apikey=l84bOGkhL4pmDdQQ2yzJGQAkoldSX3aW")
        .then((res) => {
            add_events_to_page(res.data['_embedded'].events);
        })
        .catch((error) => {
            console.log(error);
        });
});