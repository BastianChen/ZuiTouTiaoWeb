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