(function () {
  /*添加头部的内容*/
  function addHeaderContent() {
    $("header").html('' +
/*      '<ul class="g-c h-left">' +
      '<li class="h-link-app"> ' +
      '<a class="a-header">下载APP</a> ' +
      '</li> ' +
      '<li class="h-tianqi"> ' +
      '<a class="a-header"> ' +
      '<span>杭州</span> ' +
      '<span>小雨</span> ' +
      '<span> ' +
      '<em>16</em>' +
      '°  / ' +
      '<em>25</em>' +
      '°  / ' +
      '</span> ' +
      '</a> ' +
      '</li> ' +
      '</ul> ' +*/
      '<ul class="h-right g-c"> ' +
      '<li style="margin-right: 14px;" class="g-active"> ' +
      '<div class="h-login"> ' +
      '<a>登录</a> ' +
      '</div> ' +
      '</li> ' +
	  '<li><a>发文</a></li> ' +
	  '<li><a>侵权投诉</a></li> '
      /*'<li><a>问答</a></li> ' +
      '<li><a>头条号</a></li> ' +
      '<li><a>图虫</a></li> ' +
      '<li><a>反馈</a></li> ' +
      '<li  class="header-more"><a>更多</a></li> ' +
      '</ul>'+ '<ul class="showMore">' +
      ' <li><a class="layer-item">关于头条</a></li> ' +
      '<li><a class="layer-item">加入头条</a></li> ' +
      '<li><a class="layer-item">媒体报道</a></li> ' +
      '<li><a class="layer-item">媒体合作</a></li>' +
      ' <li><a class="layer-item">产品合作</a></li>' +
      ' <li><a class="layer-item">合作说明</a></li> ' +
      '<li><a class="layer-item">广告投放</a></li> ' +
      '<li><a class="layer-item">联系我们</a></li> ' +
      '<li><a class="layer-item">用户协议</a></li> ' +
      '<li><a class="layer-item">投诉指引</a></li> ' +
      '<li><a class="layer-item">廉洁举报</a></li>' +
      ' </ul>'*/
    );
  }
  
 /*入口函数*/
 function init() {
   addHeaderContent();//添加头部的内容
 }
 
 init();//启动函数
  
})();