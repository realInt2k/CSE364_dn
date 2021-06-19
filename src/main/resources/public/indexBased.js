$(document).ready(function() {
    let page = window.location.pathname.split("/").pop();
    let s = window.location.href;
    let getURL = s.replaceAll(page, "")
    $("#top10").attr("onclick", "location.href='" + getURL + "userBasedRecommend.html';");
    $("#similar").attr("onclick", "location.href='" + getURL + "movieBasedRecommend.html';");
    $.ajax({
        url: getURL + "custom/users/recommendations",
        dataType: "json"
    }).success(function(data) {
        console.log(data);
        let cnt = 0;
        let w = 120;
        let h = 180;
        for(let i in data.movieList) {
            cnt += 1;
            $(".MovieContainer").append(function(){ return "<div class='card'><div classs='card-body' " +
                "id="+("Image_"+cnt)+
                " href=" + data.movieList[i].imdb + "></div></div>"}).after(function(){
                if(data.movieList[i].imageLink) {
                    //console.log("1" + "   #Image_"+cnt);
                    loadImage(data.movieList[i].imageLink, w, h, $("#Image_"+cnt), data.movieList[i].title);
                } else {
                    //console.log("2");
                    loadImage("where.png", w, h, $("#Image_"+cnt), data.movieList[i].title);
                }
            });
            $("#Image_"+cnt).on('click','img',function(){
                console.log('it works ' + data.movieList[i].imdb);
                //location.href = data.movieList[i].imdb;
                window.open(data.movieList[i].imdb);
            });
        }
        function loadImage(path, width, height, target, title = "No title") {
            //console.log(path);
            $("<p>        </p><br>").appendTo(target);
            $("<h5 class='card-title'>"+title+"</h5>").appendTo(target);
            $("<p>        </p><br>").appendTo(target);
            $('<img class="img-thumbnail" src="'+ path +'">').load(function() {
                $(this).width(width).height(height).appendTo(target);
            }).error(function(e) {
                $(this).attr("src", "where.png").width(width).height(height);
            });
        }
    }).error(function (e) {
        console.log(e);
    });
});