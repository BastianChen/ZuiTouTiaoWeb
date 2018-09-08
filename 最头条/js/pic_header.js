/**
 * Created by bravehead on 2017/4/17.
 */
(function () {
  
  /*添加图谱界面头部的内容*/
  function addHeaderContent() {
    $("header").html('' +
      '<ul class="left topNav" style="margin-top:7px;">' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="index.html" target="_blank">推荐</a>' +
      '</li> ' +
      '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">热点</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">视频</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="#" target="_blank">图片</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateText.html" target="_blank">段子</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">娱乐</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">科技</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">汽车</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">体育</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">财经</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">军事</a>' +
      '</li> ' +
	  '<li class="tb-item">' +
      '<a class="tb-link" href="templateNews.html" target="_blank">国际</a>' +
      '</li> ' +
/*	  '<li class="tb-item left-nav" data-node="moreNav">'+
	  '<a class="tb-link moreNav" href="javascript:;" >更多</a>'+
	  '<div class="nav-layer">'+
	  '<ul>'+
	  '<li>'+
	  '<a href="#" class="layer-item" >探索</a>'+
	  '</li>'+
	  '<li>'+
	  '<a href="#" class="layer-item" >育儿</a>'+
	  '</li>'+
	  '<li>'+
	  '<a href="#" class="layer-item" >养生</a>'+
	  '</li>'+
	  '<li>'+
	  '<a href="#" class="layer-item" >美文</a>'+
	  '</li>'+
	  '<li>'+
	  '<a href="#" class="layer-item" >游戏</a>'+
	  '</li>'+
	  '<li>'+
	  '<a href="#" class="layer-item" >历史</a>'+
	  '</li>'+
	  '<li>'+
	  '<a href="#" class="layer-item" >美食</a>'+
	  '</li>'+
	  '</ul>'+
	  '</div>'+
	  '</li>'+*/
	  '</ul>'+
      '<ul class="h-right g-c"> ' +
      '<li style="margin-right: 14px;" class="g-active"> ' +
      '<div class="h-login"> ' +
      '<a>登录</a> ' +
      '</div> ' +
      '</li> ' +
	  '<li><a>发文</a></li> ' +
	  '<li><a>侵权投诉</a></li> '+
	  '</ul>'
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