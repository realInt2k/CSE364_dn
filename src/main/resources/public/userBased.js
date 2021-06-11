$(document).ready(function() {
    $("#genre").on("click", function() {
        let selection = $("#genreSelection");
        if(selection.css("display") === "none") {
            selection.css("display", "block");
        } else {
            selection.css("display", "none");
        }
    })
    $("#untickAll").on("click", function() {
        $('input').each(function(){
            $(this).prop("checked", false);
        })
    })
    $("#query").on("click", function() {
        let age = $("#age").val();
        let gender = $( "#gender option:selected" ).val();
        let occupation = $( "#occupation option:selected" ).val();
        let genres = [];
        $('input:checked').each(function(){
            console.log($(this).val())
            genres.push($(this).val());
        })
        let genresInp = "";
        for(let i = 0; i < genres.length; ++i) {
            if(i + 1 < genres.length)
                genresInp+=genres[i] + "_";
            else
                genresInp+=genres[i];
        }
        let query = "http://localhost:8080/custom/users/recommendations?";
        query += "gender="+gender;
        query += "&age="+age;
        query += "&occupation="+occupation;
        query += "&genres="+genresInp;
        console.log(query);
        $.ajax({
            url: query,
            dataType: "json"
        }).success(function(data) {
            console.log(data);
            $(".MovieContainer").empty();
            if(data.movieList[0]["arg fault"]) {
                $(".MovieContainer").append(
                    "<div>" + data.movieList[0]["arg fault"] + "</div>"
                );
            } else {
                if(data.warning) {
                    let str = data["warning"];
                    $(".MovieContainer").append(
                        str.replaceAll('\n', '<br>')
                    );
                }
                let content = "";
                let cnt = 0;
                let w = 100;
                let h = 150;
                for(let i in data.movieList) {
                    cnt += 1;
                    $(".MovieContainer").append(function(){return "<div " +
                        "id="+("Image_"+cnt)+
                        " href=" + data.movieList[i].imdb + "></div>"}).after(function(){
                        if(data.movieList[i].imageLink) {
                            //console.log("1" + "   #Image_"+cnt);
                            loadImage(data.movieList[i].imageLink, w, h, $("#Image_"+cnt), data.movieList[i].title);
                        } else {
                            //console.log("2");
                            loadImage("where.png", w, h, $("#Image_"+cnt), data.movieList[i].title);
                        }
                    });
                    $("#Image_"+cnt).on('click','img',function(){
                        window.open(data.movieList[i].imdb);
                    });
                }
                function loadImage(path, width, height, target, title = "No title") {
                    $("<p>        </p><br>").appendTo(target);
                    $("<p>"+title+"</p>").appendTo(target);
                    $('<img src="'+ path +'">').load(function() {
                        $(this).width(width).height(height).appendTo(target);
                    }).error(function(e) {
                        $(this).attr("src", "where.png").width(width).height(height);
                    });
                }
            }
        }).error(function (e) {
            console.log(e);
        });
    })
});