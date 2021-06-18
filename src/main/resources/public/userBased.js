$(document).ready(function() {
    let page = window.location.pathname.split("/").pop();
    let s = window.location.href;
    let getURL = s.replaceAll(page, "")
    $("#home").attr("onclick", "location.href='" + getURL + "index.html';");
    $("#similar").attr("onclick", "location.href='" + getURL + "movieBasedRecommend.html';");

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
        let selection = $("#genreSelection");
        if(selection.css("display") === "block") {
            selection.css("display", "none");
        }
        let age = $("#age").val();
        let gender = $( "#gender option:selected" ).val();
        let occupation = $( "#occupation option:selected" ).val();
        let genres = [];
        $('input:checked').each(function(){
            // console.log($(this).val())
            genres.push($(this).val());
        })
        let genresInp = "";
        for(let i = 0; i < genres.length; ++i) {
            if(i + 1 < genres.length)
                genresInp+=genres[i] + "_";
            else
                genresInp+=genres[i];
        }
        let query = getURL + "custom/users/recommendations?";
        query += "gender="+gender;
        query += "&age="+age;
        query += "&occupation="+occupation;
        query += "&genres="+genresInp;
        // console.log(query);
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
                let cnt = 0;
                let w = "90%";
                let h = "100%";
                let nRow = 0;
                for(let i = cnt; i< data.movieList.length; ++i) {
                    cnt += 1;
                    let newPoster = null;
                    $(".MovieContainer").append(function(){
                        let res = "";
                        if(cnt % 5 === 1) {
                            nRow += 1;
                            res += "<div class='row' id=row_"+nRow+"></div>"
                        }
                        return res;
                    }).after(function(){
                        let res = "";
                        res += "<div "+"id="+("Image_"+cnt)+" href=" + data.movieList[i].imdb + " class='column5'" + "></div>";
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
        }).error(function (e) {
            console.log(e);
        });
    })
});