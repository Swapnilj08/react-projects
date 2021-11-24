console.log("this is  my JS message");


const toggleSidebar = () => {
    if ($(".sidebar").is(":visible")) {
        //true
        //hide side bar if showing
        $(".sidebar").css("display", "none");
        $(".content").css("margine-left", "0%");
    } else {
        //false
        //display sidebar if not showing
        $(".sidebar").css("display", "block");
        $(".content").css("margine-left", "30%");
    }

};

const search = () => {
    // console.log("searching...");
    let query = $("#search-input").val();
    // console.log(query);

    if (query == "") {
        $(".search-result").hide();
    } else {
        //sending request to server
        let url = `http://localhost:8086/search/${query}`;
        fetch(url)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                // console.log(data);
                let text = `<div class='list-group'>`;
                data.forEach((contact) => {
                    text += `<a href='/user/contact_Details/${contact.contactid}' class='list-group-item list-group-item-action'>${contact.name}</a>`
                });
                text += `</div>`;
                //to display search data in search box
                $(".search-result").html(text);
                //to show drop down only when succesfull result found
                $(".search-result").show();
            });
        console.log(query);

    }
};