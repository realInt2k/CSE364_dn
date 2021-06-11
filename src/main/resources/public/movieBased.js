$(document).ready(function() {

    $("#query").on("click", function() {
        let title = $("#title").val();
        let limit = $("#limit").val();
        let query = "http://localhost:8080/movies/recommendations?";
        query += "title="+title;
        query += "&limit="+limit;
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
                            loadImage(data.movieList[i].imageLink, w, h, $("#Image_"+cnt), data.movieList[i].title);
                        } else {
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
            console.log("error: ", e);
        });
    })
});