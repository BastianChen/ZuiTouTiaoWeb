/**
 * Created by bravehead on 2017/4/17.
 */
(function () {
    let bannerImgArc = ['images/banner_1.jpg', 'images/banner_2.jpg', 'images/banner_3.jpg', 'images/banner_4.jpg', 'images/banner_5.jpg', 'images/banner_6.jpg']; //轮播图src
    let bannerTitle = ['要闻', '社会', '娱乐', '体育', '军事', '名星'];
    let bannerIndex = 0;  //轮播图下标
    let $barCtr = $('.bar-container');  //轮播图容器
    let bannerId;

    function bannerPic() {
        let $imgCta = $('.img-cta');
        let $imgNav = $('.img-nav');

        for (let i = 0; i < bannerImgArc.length; i++) {
            $imgCta.append('<img class="bannerImg" src=' + bannerImgArc[i] + '/>');
            $imgNav.append('<div class="nar-title"><a class="bar-nav-item">' + bannerTitle[i] + '</a></div>');
        }
        let $bannerImg = $('.bannerImg');  //轮播图
        let $narNavItem = $('.bar-nav-item');   //分页器
        $bannerImg.eq(bannerIndex).css({'display': 'block'});
        $narNavItem.eq(bannerIndex).addClass('bar-active');

        /**
         * 轮播图的动态切换
         * @param index
         */
        function bannerLoop(index) {
            $bannerImg.eq(index).css({'display': 'block'}).fadeIn("500").siblings().css({"display": "none"});
            $narNavItem.removeClass('bar-active').eq(index).addClass('bar-active');
        }

        bannerId = setInterval(function () {
            bannerIndex++;
            if (bannerIndex === bannerImgArc.length) {
                bannerIndex = 0;
            }
            bannerLoop(bannerIndex);  //切换
        }, 3000);

        $barCtr.hover(function (event) {
            clearInterval(bannerId);
        }, function (event) {
            // setInterval(bannerId);
            bannerId = setInterval(function () {
                bannerIndex++;
                if (bannerIndex === bannerImgArc.length) {
                    bannerIndex = 0;
                }
                bannerLoop(bannerIndex);  //切换
            }, 3000);
        });

        $narNavItem.on('mouseover', function (e) {
            bannerIndex = $narNavItem.index(this);
            bannerLoop(bannerIndex);
        })
    }

    /*搜索框的焦点的判断*/
    let $sInput = $('.s-input');
    let $lianXiang = $('.s-search-lianxiang');
    let $indexMore = $('.index-lf-more');
    let $indexMoreUl = $('.index-lf-more-ul');

    function inputAddClick() {
        $sInput.focus(function () {
            $lianXiang.css('display', 'block');
        });
        $sInput.blur(function () {
            $lianXiang.css('display', 'none');
        });
    }

    /**
     * 封装的hover方法
     * @param ele
     * @param ele1
     */
    function moreHover(ele, ele1) {
        if (ele1) {
            ele.hover(function () {
                ele1.css('display', 'block');
            }, function () {
                ele1.css('display', 'none');
            });
        } else {
            ele.hover(function () {
                ele.css('display', 'block');
            }, function () {
                ele.css('display', 'none');
            });
        }
    }

    //index-more hover显示
    function indexMoreHover() {
        moreHover($indexMore, $indexMoreUl);
        moreHover($indexMoreUl);
    }

    let $headerMore = $('.header-more');
    let $showMore = $('.showMore');

    //header-more hover显示
    function hoverHeaderMore() {
        moreHover($headerMore, $showMore);
        moreHover($showMore);
    }

    /*点击刷新*/
    function refresh() {
        $('.back-top>span').on('click', function () {
            location.reload(true);
        })
    }

    function shareMore() {
        let $shareWrap = $('.share-wrap');
        let $snsbox = $('.snsbox');
        moreHover($shareWrap, $snsbox);
    }

    function init() {
        bannerPic();//轮播图
        inputAddClick(); //搜索框的焦点的判断
        indexMoreHover();//index-more hover显示
        hoverHeaderMore();//header-more hover显示
        refresh();//点击刷新
        shareMore();//分享

        // $('a').bind('click',function () {
        //   $(this).css('color','#999')
        // })
    }

    init();

})();