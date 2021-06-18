$(document).ready(function() {
    let page = window.location.pathname.split("/").pop();
    let s = window.location.href;
    let getURL = s.replaceAll(page, "")
    $("#home").attr("onclick", "location.href='" + getURL + "index.html';");
    $("#top10").attr("onclick", "location.href='" + getURL + "userBasedRecommend.html';");
    let cnt = 0;
    let nRow = 0;
    $("#query").on("click", function() {
        let title = $("#title").val();
        let limit = $("#limit").val();
        let query = getURL+"movies/recommendations?";
        query += "title="+title;
        query += "&limit="+limit;
        cnt = 0;
        nRow = 0;
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
                let w = "90%";
                let h = "100%";
                let justRemove = false;
                display();
                function display()
                {
                    let myobj = document.getElementById("SeeMore");
                    if(myobj) {
                        cnt -= 1;
                        justRemove = true;
                        myobj.remove();
                    }
                    for(let i = cnt; i< data.movieList.length; ++i) {
                        cnt += 1;
                        if(!justRemove && cnt % 50 === 0)
                        {
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
                                display();
                            })
                            break;
                        } else if (cnt % 50 === 0 && justRemove) {
                            justRemove = false;
                        }
                        let newPoster = null;
                        $(".MovieContainer").append(function(){
                            let res = "";
                            if(cnt % 4 === 1) {
                                nRow += 1;
                                res += "<div class='row' id=row_"+nRow+"></div>"
                            }
                            return res;
                        }).after(function(){
                            let res = "";
                            res += "<div "+"id="+("Image_"+cnt)+" href=" + data.movieList[i].imdb + " class='column'" + "></div>";
                            newPoster = $(res);
                        }).after(function(){
                            if(data.movieList[i].imageLink) {
                                loadImage(data.movieList[i].imageLink, w, h, newPoster, data.movieList[i].title);
                            } else {
                                loadImage("where.png", w, h, newPoster, data.movieList[i].title);
                            }
                        }).after(function(){
                            let target = $("#row_"+nRow);
                            newPoster.appendTo(target);
                            newPoster.on('click','img',function(){
                                window.open(data.movieList[i].imdb);
                            });
                        });
                    }
                    function loadImage(path, width, height, target, title = "No title") {
                        $("<br>").appendTo(target);
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