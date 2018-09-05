var size;
$.ajax({
    type: "get",
    url: "/Article/getArticleCount",
    dataType: 'json',
    contentType: 'application/json;charset=utf-8',
    success: function (data) {
        console.log(data);
        size = data.size;
        getArticle();
        updateCaptcha()
    },
    error: function (data) {
        console.log("查询失败！");
    },
    complete: function () {
    }
});

function updateCaptcha() {
    random = new Date().getTime()+''+Math.floor(Math.random() * Math.pow(10, 8));
     $('#captcha_img').attr('src', "/User/getCaptcha?rad="+random);
    //$('#captcha_img').attr('src', "/getCaptcha?rad="+random);
}

var getArticle = function(){
    $.ajax({
        type: "get",
        url: "/Article/getArticleByType",
        data: {
            "typeNumber": 8,
            "start": 2300
        },
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            console.log(data);
        },
        error: function (data) {
            console.log("查询失败！");
        },
        complete: function () {
        }
    });
}

// $.ajax({
//     type: "get",
//     url: "/Article/getArticleContent",
//     data: {
//         "id": 1
//     },
//     dataType: 'json',
//     contentType: 'application/json;charset=utf-8',
//     success: function (data) {
//         console.log(data);
//     },
//     error: function (data) {
//         console.log("查询失败！");
//     },
//     complete: function () {
//     }
// });


