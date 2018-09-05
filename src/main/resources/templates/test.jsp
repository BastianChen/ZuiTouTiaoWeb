<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>上传图片</title>
    <link rel="stylesheet" href="/resources/jcrop/css/jquery.Jcrop.css" type="text/css"></link>
    <script type="text/javascript" src="/resources/jcrop/js/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/jcrop/js/jquery.Jcrop.js"></script>
</head>
<body>
<form name="form" action="/Upload/uploadHeadImage" class="form-horizontal" method="post"
      enctype="multipart/form-data">
    <div class="modal-body text-center">
        <div class="zxx_main_con">
            <div class="zxx_test_list">
                <input class="photo-file" type="file" name="imgFile" id="fcupload" onchange="readURL(this);"/>
                <img alt="" src="" id="cutimg"/>
                <input type="hidden" id="id" name="id" value="1"/>
                <input type="hidden" id="x" name="x"/>
                <input type="hidden" id="y" name="y"/>
                <input type="hidden" id="w" name="w"/>
                <input type="hidden" id="h" name="h"/>
            </div>
        </div>
    </div>

    <div id="preview-pane">
        <div class="preview-container">
            <img src="" class="jcrop-preview" alt="预览">
        </div>
    </div>

    <div class="modal-footer">
        <button id="submit" onclick="">上传</button>
    </div>
</form>

<script type="text/javascript">
    //定义一个全局api，这样操作起来比较灵活
    var api = null,

        boundx,
        boundy,

        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),

        xsize = $pcnt.width(),
        ysize = $pcnt.height();

    //读取文件
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.readAsDataURL(input.files[0]);
            reader.onload = function (e) {
                $('#cutimg').removeAttr('src');
                $('#cutimg').attr('src', e.target.result);
                $pimg.removeAttr('src');
                $pimg.attr('src', e.target.result);
                //初始化插件 这种方式有问题了废弃 应该是api有改动了
                /* api = $.Jcrop('#cutimg', {
                    setSelect: [ 20, 20, 200, 200 ],
                    aspectRatio: xsize / ysize,
                    onSelect: updateCoords,
                    onChange: updateCoords
                });
                var bounds = api.getBounds();
                boundx = bounds[0];
                boundy = bounds[1];
                $preview.appendTo(api.ui.holder); */
                //这是另外一种处理方式。
                $('#cutimg').Jcrop({
                    setSelect: [500, 0, 700, 200],
                    onChange: updateCoords,
                    onSelect: updateCoords,
                    aspectRatio: 1
                }, function () {
                    var bounds = this.getBounds();
                    boundx = bounds[0];
                    boundy = bounds[1];
                    api = this;
                    $preview.appendTo(api.ui.holder);
                });
            };
            if (api != undefined) {
                api.destroy();
            }
        }

        //更新坐标
        function updateCoords(obj) {
            $("#x").val(obj.x);
            $("#y").val(obj.y);
            $("#w").val(obj.w);
            $("#h").val(obj.h);
            if (parseInt(obj.w) > 0) {
                var rx = xsize / obj.w;
                var ry = ysize / obj.h;
                $pimg.css({
                    width: Math.round(rx * boundx) + 'px',
                    height: Math.round(ry * boundy) + 'px',
                    marginLeft: '-' + Math.round(rx * obj.x) + 'px',
                    marginTop: '-' + Math.round(ry * obj.y) + 'px'
                });
            }
        };
    }
</script>
</body>
</html>