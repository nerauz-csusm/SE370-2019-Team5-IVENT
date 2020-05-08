const check_genre = (event) => {
    if (event.classifications && event.classifications[0] && event.classifications[0].genre && event.classifications[0].genre.name !== "Undefined") {
        let html_string = `<p><b>Genre:</b> ${event.classifications[0].genre.name}`;

        return event.classifications[0].subGenre !== undefined ? html_string + `, ${event.classifications[0].subGenre.name}` : html_string;
    }
    return "";
}

const check_price = (event) => {
    if (event.priceRanges !== undefined && event.priceRanges[0] !== undefined)
        return `<p><b>Price:</b> $${event.priceRanges[0].min} to $${event.priceRanges[0].max}</p>`;
    return "";
}

const add_column = (event, row_id) => {
    $(`#${row_id}`).append(`
        <div class="col d-flex justify-content-center mt-3 mb-3">
            <div class="card" style="width: 18rem;">
                <img src="${event.images[0] ? event.images[0].url : "https://discordapp.com/channels/633228748526714880/633228748526714882/706956957247864843"}" class="card-img-top" alt="Image of the event">
                <div class="card-body">
                    <p class="card-text text-center">${event.name} - ${event.dates.timezone}</p>
                    <p><b>Date:</b> ${event.dates.start.localDate} at ${event.dates.start.localTime}</p>
                    ${check_genre(event)}
                    ${check_price(event)}
                    <span class="pull-right"><i class="fas fa-star"></i></span>
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

    $('.fas').click(function() {
        this.style.color !== 'gold' ? $(this).css('color', 'gold') : $(this).css('color', '');
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