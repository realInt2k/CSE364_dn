$(document).ready(function() {
    let cnt = 0;
    $("#query").on("click", function() {
        let title = $("#title").val();
        let limit = $("#limit").val();
        let query = "http://localhost:8080/movies/recommendations?";
        query += "title="+title;
        query += "&limit="+limit;
        cnt = 0;
        $.ajax({
            url: query,
            dataType: "json"
        }).success(function(data) {
            // console.log(data);
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
                let w = 100;
                let h = 150;
                display();
                function display()
                {
                    let myobj = document.getElementById("SeeMore");
                    if(myobj)
                        myobj.remove();
                    for(let i = cnt; i< data.movieList.length; ++i) {
                        cnt += 1;
                        if(cnt % 50 === 0) {
                            $(".MovieContainer").append(
                                "<button id=\"SeeMore\"" +
                                "style=\"background-color: green; " +
                                    "font-size: 16px; border-radius: 8px; " +
                                    "color:white; " +
                                    "padding: 15px 24px; text-align: center; margin: auto; width: 100%;\">" +
                                "Click to view more..." +
                                "</button> <br>"
                            )
                            $("#SeeMore").on("click", function(){
                                console.log("clicked");
                                display();
                            })
                            break;
                        }
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
            }
        }).error(function (e) {
            console.log("error: ", e);
        });
    })
});