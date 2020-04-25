const add_column = (event, row_id) => {
    $(`#${row_id}`).after(`
        <div class="col">
            <div class="card" style="width: 18rem;">
                <img src="${event.img}" class="card-img-top" alt="Image of the event">
                <div class="card-body">
                    <p class="card-text">${event.description}</p>
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
            $("#index-container").after(`<div class='row' id="line-${row_number}"></div>`)
        }

        add_column(event, row_id);
  });
};

$(() => {
    axios.get("/events")
        .then((res) => {
            console.log(res);
            add_events_to_page(res.data);
        })
        .catch((error) => {
            console.log(error);
        });
});