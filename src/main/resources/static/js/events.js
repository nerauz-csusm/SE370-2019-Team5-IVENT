import {toggleNavBtn} from "/js/tools.js";
import {formArrayToObject, makeAlert} from "/js/form.js";

let fileAsBinary;

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

function toggleDiv(elemToShow, elemToHide) {
    elemToShow.show();
    elemToHide.hide();
}

function createCard(parent, event) {
    parent.append(`
        <div class="card mb-3">
            <div class="row no-gutters">
                <div class="col-md-4">
                    <img src="${event.images && event.images[0] ? event.images[0].url : "https://discordapp.com/channels/633228748526714880/633228748526714882/706956957247864843"}" class="card-img-top" alt="Image of the event">
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <p class="card-text text-center">${event.name} - ${event.dates.timezone}</p>
                        <p><b>Date:</b> ${event.dates.start.localDate} at ${event.dates.start.localTime}</p>
                        ${check_genre(event)}
                        ${check_price(event)}
                    </div>
                </div>
            </div>
        </div>
        `);
}

function createUserCard(parent, event) {
    parent.append(`
        <div class="card mb-3">
            <div class="row no-gutters">
                <div class="col-md-4">
                    <img src="${event.image}" class="card-img-top" alt="Image of the event">
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <p class="card-text text-center">${event.title}</p>
                        <p><b>Date:</b> ${event.beginDate} at ${event.endDate}</p>
                        <p><b>Price:</b> $${event.minPrice} to $${event.maxPrice}</p>   
                    </div>
                </div>
            </div>
        </div>
        `);
}

function  addEventsToPage(events, callback) {
    const parent = $('#breadcrumb');
    const eventsDiv = $('#events').length === 0 ? $('<div id="events"></div>') : $('#events');

    if ($('#events').length === 0)
        parent.after(eventsDiv)
    events.forEach((event) => {
        callback(eventsDiv, event)
    });
}

function getUserEvents() {
    axios.get(`/event?id=${localStorage.getItem('id')}`)
        .then((res) => {
            const events = res.data.data;

            addEventsToPage(events, createUserCard);
        })
        .catch((error) => {
            console.log(error);
        });
}

function createEvents() {
    const events = JSON.parse(localStorage.getItem('events'));

    if (events)
        addEventsToPage(events, createCard);
    getUserEvents();
}

function displayEvents() {
    const eventDiv = $('#events');

    getUserEvents();
    toggleDiv(eventDiv, $('#form'))
}

function createForm() {
    const parent = $('#breadcrumb');

    parent.after(`
     <form id="form">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" placeholder="Title of the event" name="title">
            </div>
            <div class="form-group col-md-6">
                <label for="organization">Organization</label>
                <input type="text" class="form-control" id="organization" placeholder="Organizer of the event" name="organizations">
            </div>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" rows="3" placeholder="Describe your event" name="description"></textarea>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="min-price">Min price</label>
                <input type="number" class="form-control" id="min-price" placeholder="Minimum price" name="minPrice">
            </div>
            <div class="form-group col-md-6">
                <label for="max-price">Max price</label>
                <input type="number" class="form-control" id="max-price" placeholder="Maximum price" name="maxPrice">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="begin-date">Begin Date</label>
                <input type="text" class="form-control picker" id="begin-date" placeholder="Beginning date" name="beginDate">
            </div>
            <div class="form-group col-md-6">
                <label for="end-date">End Date</label>
                <input type="text" class="form-control picker" id="end-date" placeholder="Ending date" name="endDate">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="image">Image</label>
                <input type="file" class="form-control-file" id="image">
            </div>
            <div class="col-md-6 my-auto">
                <button type="submit" class="btn btn-primary" id="create-event">Create</button>
            </div>
        </div>        
    </form>
    `);

    $('.picker').datepicker({});

    $('#form').submit(function(e) {
        e.preventDefault();

        const formData = formArrayToObject($(this));

        formData["creatorId"] = localStorage.getItem("id");

        if (fileAsBinary)
            formData["image"] = fileAsBinary;

        axios.post('/event', formData)
            .then(function (response) {
                console.log(response)
                makeAlert('form', 'alert-success', response.data.message);
            })
            .catch(function (error) {
                if (error.response.status === 400)
                    makeAlert('form', 'alert-danger', error.response.data.message);
                else
                    console.log({ error });
            });
    });

    $('#image').change(function() {
        if (this.files && this.files[0]) {
            let reader = new FileReader();
            reader.onload = (e) => fileAsBinary = e.target.result;

            reader.readAsDataURL(this.files[0]);
        }
    });
}

function displayForm() {
    const formDiv = $('#form');

    if (formDiv.length === 0)
        createForm();
    toggleDiv(formDiv, $('#events'))
}

const toggleDisplay = {
    "my-events": displayEvents,
    "create-event": displayForm
};

function onBreadcrumbItemClick(e) {
    let currentTarget = $(e.currentTarget);
    let lastTarget = $('.active');

    lastTarget.removeClass('active');
    currentTarget.addClass('active');

    toggleDisplay[currentTarget[0].id]();
};

$(() => {
    toggleNavBtn();

    $('.over').click(onBreadcrumbItemClick);

    createEvents();
})