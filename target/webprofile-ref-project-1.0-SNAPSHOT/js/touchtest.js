$(function () {
    var scale = $('body').width() / 720;
    $('html').css('font-size', 100 * scale + 'px');
    var b = document.getElementsByTagName("body")[0];
    b.addEventListener("touchmove", function(e){
        e.preventDefault();
    });
    (function(){
        var startX;
        var el = document.querySelector("#div_star");
        el.addEventListener("touchstart", function (e){
            startX = e.touches[0].pageX;
            whichShow(e.touches[0].pageX);
        });
        el.addEventListener('touchmove', function (e) {
            whichShow(e.touches[0].pageX);
        });
        el.addEventListener("touchend", function(e){
            whichShow(e.changedTouches[0].pageX);
            //alert(startX+"++end++"+e.changedTouches[0].pageX)
        })
    })();//(f())()标识立即执行匿名函数

});
function whichShow(d) {
    if (d > 10 && d <= 68) {
        change(1);
        alert();
    } else if (d > 68 && d <= 136) {
        change(2);
    } else if (d > 136 && d <= 204) {
        change(3);
    } else if (d > 204 && d <= 272) {
        change(4);
    } else if (d > 272) {
        change(5);
    }
}
function change(x) {
    var star1 = $("#first");
    var star2 = $("#second");
    var star3 = $("#three");
    var star4 = $("#four");
    var star5 = $("#five");
    switch (x) {
        case 1:
            star1.css("background-image", "url('img/ff.png')");
            star2.css("background-image", "url('img/ee.png')");
            star3.css("background-image", "url('img/ee.png')");
            star4.css("background-image", "url('img/ee.png')");
            star5.css("background-image", "url('img/ee.png')");
            //star1.attr("src", "ff.png");
            //star2.attr("src", "ee.png");
            //star3.attr("src", "ee.png");
            //star4.attr("src", "ee.png");
            //star5.attr("src", "ee.png");
            break;
        case 2:
            star1.css("background-image", "url('img/ff.png')");
            star2.css("background-image", "url('img/ff.png')");
            star3.css("background-image", "url('img/ee.png')");
            star4.css("background-image", "url('img/ee.png')");
            star5.css("background-image", "url('img/ee.png')");
            //star1.attr("src", "ff.png");
            //star2.attr("src", "ff.png");
            //star3.attr("src", "ee.png");
            //star4.attr("src", "ee.png");
            //star5.attr("src", "ee.png");
            break;
        case 3:
            star1.css("background-image", "url('img/ff.png')");
            star2.css("background-image", "url('img/ff.png')");
            star3.css("background-image", "url('img/ff.png')");
            star4.css("background-image", "url('img/ee.png')");
            star5.css("background-image", "url('img/ee.png')");
            //star1.attr("src", "ff.png");
            //star2.attr("src", "ff.png");
            //star3.attr("src", "ff.png");
            //star4.attr("src", "ee.png");
            //star5.attr("src", "ee.png");
            break;
        case 4:
            star1.css("background-image", "url('img/ff.png')");
            star2.css("background-image", "url('img/ff.png')");
            star3.css("background-image", "url('img/ff.png')");
            star4.css("background-image", "url('img/ff.png')");
            star5.css("background-image", "url('img/ee.png')");
            //star1.attr("src", "ff.png");
            //star2.attr("src", "ff.png");
            //star3.attr("src", "ff.png");
            //star4.attr("src", "ff.png");
            //star5.attr("src", "ee.png");
            break;
        case 5:
            star1.css("background-image", "url('img/ff.png')");
            star2.css("background-image", "url('img/ff.png')");
            star3.css("background-image", "url('img/ff.png')");
            star4.css("background-image", "url('img/ff.png')");
            star5.css("background-image", "url('img/ff.png')");
            //star1.attr("src", "ff.png");
            //star2.attr("src", "ff.png");
            //star3.attr("src", "ff.png");
            //star4.attr("src", "ff.png");
            //star5.attr("src", "ff.png");
            break;
    }
}