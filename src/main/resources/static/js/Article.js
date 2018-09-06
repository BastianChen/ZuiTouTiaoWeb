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
getUserHobbyModel(1);
function getUserHobbyModel(userId) {
    $.ajax({
        type: "get",
        url: "/HobbyModel/getHobbyModel",
        data: {
            "userId": userId
        },
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        success: function (data) {
            console.log(data);
            insertUserHobbyModel(data)
        },
        error: function (data) {
            console.log("查询失败！");
        },
        complete: function () {
        }
    });
}
function insertUserHobbyModel(data) {
    var userHobbyModel = data.userHobbyModelDTO;
    var text = userHobbyModel.userName;
    var subtext = userHobbyModel.userName + "的用户兴趣模型数据";
    if(userHobbyModel.hobby2Name==null){
        userHobbyModel.hobby2Name="暂无";
        userHobbyModel.hobby2Rate=0;
    }
    if(userHobbyModel.hobby3Name==null){
        userHobbyModel.hobby3Name="暂无";
        userHobbyModel.hobby3Rate=0;
    }
    if(userHobbyModel.hobby4Name==null){
        userHobbyModel.hobby4Name="暂无";
        userHobbyModel.hobby4Rate=0;
    }
    if(userHobbyModel.hobby5Name==null){
        userHobbyModel.hobby5Name="暂无";
        userHobbyModel.hobby5Rate=0;
    }
    if(userHobbyModel.hobby6Name==null){
        userHobbyModel.hobby6Name="暂无";
        userHobbyModel.hobby6Rate=0;
    }
    var myChart = echarts.init(document.getElementById('chart'),'macarons');
    var option = {
        title : {
            text: text,
            subtext: subtext
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            x : 'center',
            data:[text]
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: true},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        polar : [
            {
                indicator : [
                    {text : userHobbyModel.hobby1Name, max  : userHobbyModel.hobby1Rate},
                    {text : userHobbyModel.hobby2Name, max  : userHobbyModel.hobby1Rate},
                    {text : userHobbyModel.hobby3Name, max  : userHobbyModel.hobby1Rate},
                    {text : userHobbyModel.hobby4Name, max  : userHobbyModel.hobby1Rate},
                    {text : userHobbyModel.hobby5Name, max  : userHobbyModel.hobby1Rate},
                    {text : userHobbyModel.hobby6Name, max  : userHobbyModel.hobby1Rate}
                ],
                radius : 130
            }
        ],
        series : [
            {
                name: '用户兴趣模型数据',
                type: 'radar',
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: 'default'
                        }
                    }
                },
                data : [
                    {
                        value : [ userHobbyModel.hobby1Rate,userHobbyModel.hobby2Rate, userHobbyModel.hobby3Rate,
                            userHobbyModel.hobby4Rate,userHobbyModel.hobby5Rate,userHobbyModel.hobby6Rate],
                        name : text
                    }
                ]
            }
        ]
    };
    myChart.setOption(option);
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


