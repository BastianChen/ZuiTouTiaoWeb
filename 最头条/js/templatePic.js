/**
 * Created by bravehead on 2017/4/20.
 */
(function () {
  
  /**
   * 封装的hover方法
   * @param ele
   * @param ele1
   */
  function moreHover(ele, ele1) {
    if(ele1){
      ele.hover(function () {
        ele1.css('display','block');
      },function () {
        ele1.css('display','none');
      });
    }else{
      ele.hover(function () {
        ele.css('display','block');
      },function () {
        ele.css('display','none');
      });
    }
  }
  
  let $leftNav = $(".left-nav");
  let $navLay = $(".nav-layer");
  let $rightMore = $('.right-more');
  let $layer = $('.layer');
  
  
  /*新闻列表的nav的点击切换*/
  /**
   * @param ele
   */
  function navChangeItem(ele, ele1) {
    ele.on('click',function () {
      let nowIndex = ele.index(this);
      ele.removeClass('news-li-active').eq(nowIndex).addClass('news-li-active');
      ele1.removeClass('news-li-active').eq(nowIndex).addClass('news-li-active');
    });
  }
  
  let $newsNavLi = $('.news-nav li');
  let $hiddenNavUl = $('.hidden-nav-ul li');
  
  function init() {
    moreHover($leftNav, $navLay);  //header左边滑动显示
    moreHover($rightMore, $layer);  //header右边滑动显示
    navChangeItem($newsNavLi, $hiddenNavUl);   //新闻列表的nav
    navChangeItem($hiddenNavUl, $newsNavLi);  //滚动后的nav
  }

  init();
})();