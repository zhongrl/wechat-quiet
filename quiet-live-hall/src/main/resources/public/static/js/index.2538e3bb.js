(function(e){function n(n){for(var a,i,c=n[0],s=n[1],g=n[2],u=0,l=[];u<c.length;u++)i=c[u],Object.prototype.hasOwnProperty.call(r,i)&&r[i]&&l.push(r[i][0]),r[i]=0;for(a in s)Object.prototype.hasOwnProperty.call(s,a)&&(e[a]=s[a]);p&&p(n);while(l.length)l.shift()();return t.push.apply(t,g||[]),o()}function o(){for(var e,n=0;n<t.length;n++){for(var o=t[n],a=!0,i=1;i<o.length;i++){var s=o[i];0!==r[s]&&(a=!1)}a&&(t.splice(n--,1),e=c(c.s=o[0]))}return e}var a={},r={index:0},t=[];function i(e){return c.p+"static/js/"+({"pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61":"pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61","pages-comment-comment":"pages-comment-comment","pages-login-login":"pages-login-login","pages-mine-mine":"pages-mine-mine","pages-order-order":"pages-order-order","pages-orderDetail-orderDetail":"pages-orderDetail-orderDetail","pages-pay-pay":"pages-pay-pay","pages-paySucc-paySucc":"pages-paySucc-paySucc","pages-work-work":"pages-work-work"}[e]||e)+"."+{"pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61":"24c87465","pages-comment-comment":"8afede3b","pages-login-login":"bdd94afa","pages-mine-mine":"bb043cc5","pages-order-order":"8436d0c8","pages-orderDetail-orderDetail":"2d7675c4","pages-pay-pay":"c42d913a","pages-paySucc-paySucc":"6294e467","pages-work-work":"20ca4d35"}[e]+".js"}function c(n){if(a[n])return a[n].exports;var o=a[n]={i:n,l:!1,exports:{}};return e[n].call(o.exports,o,o.exports,c),o.l=!0,o.exports}c.e=function(e){var n=[],o=r[e];if(0!==o)if(o)n.push(o[2]);else{var a=new Promise((function(n,a){o=r[e]=[n,a]}));n.push(o[2]=a);var t,s=document.createElement("script");s.charset="utf-8",s.timeout=120,c.nc&&s.setAttribute("nonce",c.nc),s.src=i(e);var g=new Error;t=function(n){s.onerror=s.onload=null,clearTimeout(u);var o=r[e];if(0!==o){if(o){var a=n&&("load"===n.type?"missing":n.type),t=n&&n.target&&n.target.src;g.message="Loading chunk "+e+" failed.\n("+a+": "+t+")",g.name="ChunkLoadError",g.type=a,g.request=t,o[1](g)}r[e]=void 0}};var u=setTimeout((function(){t({type:"timeout",target:s})}),12e4);s.onerror=s.onload=t,document.head.appendChild(s)}return Promise.all(n)},c.m=e,c.c=a,c.d=function(e,n,o){c.o(e,n)||Object.defineProperty(e,n,{enumerable:!0,get:o})},c.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},c.t=function(e,n){if(1&n&&(e=c(e)),8&n)return e;if(4&n&&"object"===typeof e&&e&&e.__esModule)return e;var o=Object.create(null);if(c.r(o),Object.defineProperty(o,"default",{enumerable:!0,value:e}),2&n&&"string"!=typeof e)for(var a in e)c.d(o,a,function(n){return e[n]}.bind(null,a));return o},c.n=function(e){var n=e&&e.__esModule?function(){return e["default"]}:function(){return e};return c.d(n,"a",n),n},c.o=function(e,n){return Object.prototype.hasOwnProperty.call(e,n)},c.p="./",c.oe=function(e){throw console.error(e),e};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],g=s.push.bind(s);s.push=n,s=s.slice();for(var u=0;u<s.length;u++)n(s[u]);var p=g;t.push([0,"chunk-vendors"]),o()})({0:function(e,n,o){e.exports=o("56d7")},"034f":function(e,n,o){"use strict";var a=o("90cd"),r=o.n(a);r.a},"23be":function(e,n,o){"use strict";o.r(n);var a=o("e313"),r=o.n(a);for(var t in a)"default"!==t&&function(e){o.d(n,e,(function(){return a[e]}))}(t);n["default"]=r.a},"3dfd":function(e,n,o){"use strict";o.r(n);var a=o("a475"),r=o("23be");for(var t in r)"default"!==t&&function(e){o.d(n,e,(function(){return r[e]}))}(t);o("034f");var i=o("2877"),c=Object(i["a"])(r["default"],a["a"],a["b"],!1,null,null,null);n["default"]=c.exports},"56d7":function(e,n,o){"use strict";var a=o("4ea4");o("8e6e"),o("ac6a"),o("456d");var r=a(o("bd86"));o("cadf"),o("551c"),o("f751"),o("097d"),o("6cdc"),o("1c31"),o("921b");var t=a(o("e143")),i=a(o("3dfd"));function c(e,n){var o=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);n&&(a=a.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),o.push.apply(o,a)}return o}function s(e){for(var n=1;n<arguments.length;n++){var o=null!=arguments[n]?arguments[n]:{};n%2?c(o,!0).forEach((function(n){(0,r.default)(e,n,o[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(o)):c(o).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(o,n))}))}return e}t.default.config.productionTip=!1,i.default.mpType="app";var g=new t.default(s({},i.default));g.$mount()},5768:function(e,n,o){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var a={pages:{"pages/login/login":{navigationStyle:"custom"},"pages/order/order":{navigationBarTitleText:"订单列表",enablePullDownRefresh:!0},"pages/orderDetail/orderDetail":{navigationBarTitleText:"订单详情"},"pages/work/work":{navigationBarTitleText:"工作状态"},"pages/mine/mine":{navigationBarTitleText:"我的绩效",enablePullDownRefresh:!0,onReachBottomDistance:100},"pages/pay/pay":{navigationBarTitleText:"确认账单"},"pages/paySucc/paySucc":{navigationBarTitleText:"支付成功"},"pages/comment/comment":{navigationBarTitleText:"评价"}},globalStyle:{navigationBarTextStyle:"black",navigationBarTitleText:"静享",navigationBarBackgroundColor:"#FFFFFF",backgroundColor:"#F9F9F9"}};n.default=a},"6cdc":function(e,n,o){"use strict";(function(e){var n=o("4ea4"),a=n(o("e143"));e["________"]=!0,delete e["________"],e.__uniConfig={tabBar:{color:" #4A4A4A",selectedColor:"#B09E85",borderStyle:"white",backgroundColor:"#ffffff",list:[{pagePath:"pages/order/order",text:"订单列表",iconPath:"static/tab_list.png",selectedIconPath:"static/tab_list_checked.png",redDot:!1,badge:""},{pagePath:"pages/work/work",text:"工作状态",iconPath:"static/tab_work.png",selectedIconPath:"static/tab_work_checked.png",redDot:!1,badge:""},{pagePath:"pages/mine/mine",text:"我的",iconPath:"static/tab_mine.png",selectedIconPath:"static/tab_mine_checked.png",redDot:!1,badge:""}]},globalStyle:{navigationBarTextStyle:"black",navigationBarTitleText:"静享",navigationBarBackgroundColor:"#FFFFFF",backgroundColor:"#F9F9F9"}},e.__uniConfig.router={mode:"hash",base:"/"},e.__uniConfig["async"]={loading:"AsyncLoading",error:"AsyncError",delay:200,timeout:3e3},e.__uniConfig.debug=!1,e.__uniConfig.networkTimeout={request:6e3,connectSocket:6e3,uploadFile:6e3,downloadFile:6e3},e.__uniConfig.sdkConfigs={},e.__uniConfig.qqMapKey="XVXBZ-NDMC4-JOGUS-XGIEE-QVHDZ-AMFV2",e.__uniConfig.nvue={"flex-direction":"column"},a.default.component("pages-login-login",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-login-login")]).then(function(){return e(o("a86a"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),a.default.component("pages-order-order",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-order-order")]).then(function(){return e(o("ebc4"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),a.default.component("pages-orderDetail-orderDetail",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-orderDetail-orderDetail")]).then(function(){return e(o("de87"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),a.default.component("pages-work-work",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-work-work")]).then(function(){return e(o("0806"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),a.default.component("pages-mine-mine",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-mine-mine")]).then(function(){return e(o("e550"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),a.default.component("pages-pay-pay",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-pay-pay")]).then(function(){return e(o("7559"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),a.default.component("pages-paySucc-paySucc",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-paySucc-paySucc")]).then(function(){return e(o("94f5"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),a.default.component("pages-comment-comment",(function(e){var n={component:Promise.all([o.e("pages-comment-comment~pages-login-login~pages-mine-mine~pages-order-order~pages-orderDetail-orderDet~25d19d61"),o.e("pages-comment-comment")]).then(function(){return e(o("a3ad"))}.bind(null,o)).catch(o.oe),delay:__uniConfig["async"].delay,timeout:__uniConfig["async"].timeout};return __uniConfig["async"]["loading"]&&(n.loading={name:"SystemAsyncLoading",render:function(e){return e(__uniConfig["async"]["loading"])}}),__uniConfig["async"]["error"]&&(n.error={name:"SystemAsyncError",render:function(e){return e(__uniConfig["async"]["error"])}}),n})),e.__uniRoutes=[{path:"/",alias:"/pages/login/login",component:{render:function(e){return e("Page",{props:Object.assign({isQuit:!0,isEntry:!0},__uniConfig.globalStyle,{navigationStyle:"custom"})},[e("pages-login-login",{slot:"page"})])}},meta:{id:1,name:"pages-login-login",isNVue:!1,pagePath:"pages/login/login",isQuit:!0,isEntry:!0,windowTop:0}},{path:"/pages/order/order",component:{render:function(e){return e("Page",{props:Object.assign({isQuit:!0,isTabBar:!0,tabBarIndex:0},__uniConfig.globalStyle,{navigationBarTitleText:"订单列表",enablePullDownRefresh:!0})},[e("pages-order-order",{slot:"page"})])}},meta:{id:2,name:"pages-order-order",isNVue:!1,pagePath:"pages/order/order",isQuit:!0,isTabBar:!0,tabBarIndex:0,windowTop:44}},{path:"/pages/orderDetail/orderDetail",component:{render:function(e){return e("Page",{props:Object.assign({},__uniConfig.globalStyle,{navigationBarTitleText:"订单详情"})},[e("pages-orderDetail-orderDetail",{slot:"page"})])}},meta:{name:"pages-orderDetail-orderDetail",isNVue:!1,pagePath:"pages/orderDetail/orderDetail",windowTop:44}},{path:"/pages/work/work",component:{render:function(e){return e("Page",{props:Object.assign({isQuit:!0,isTabBar:!0,tabBarIndex:1},__uniConfig.globalStyle,{navigationBarTitleText:"工作状态"})},[e("pages-work-work",{slot:"page"})])}},meta:{id:3,name:"pages-work-work",isNVue:!1,pagePath:"pages/work/work",isQuit:!0,isTabBar:!0,tabBarIndex:1,windowTop:44}},{path:"/pages/mine/mine",component:{render:function(e){return e("Page",{props:Object.assign({isQuit:!0,isTabBar:!0,tabBarIndex:2},__uniConfig.globalStyle,{navigationBarTitleText:"我的绩效",enablePullDownRefresh:!0,onReachBottomDistance:100})},[e("pages-mine-mine",{slot:"page"})])}},meta:{id:4,name:"pages-mine-mine",isNVue:!1,pagePath:"pages/mine/mine",isQuit:!0,isTabBar:!0,tabBarIndex:2,windowTop:44}},{path:"/pages/pay/pay",component:{render:function(e){return e("Page",{props:Object.assign({},__uniConfig.globalStyle,{navigationBarTitleText:"确认账单"})},[e("pages-pay-pay",{slot:"page"})])}},meta:{name:"pages-pay-pay",isNVue:!1,pagePath:"pages/pay/pay",windowTop:44}},{path:"/pages/paySucc/paySucc",component:{render:function(e){return e("Page",{props:Object.assign({},__uniConfig.globalStyle,{navigationBarTitleText:"支付成功"})},[e("pages-paySucc-paySucc",{slot:"page"})])}},meta:{name:"pages-paySucc-paySucc",isNVue:!1,pagePath:"pages/paySucc/paySucc",windowTop:44}},{path:"/pages/comment/comment",component:{render:function(e){return e("Page",{props:Object.assign({},__uniConfig.globalStyle,{navigationBarTitleText:"评价"})},[e("pages-comment-comment",{slot:"page"})])}},meta:{name:"pages-comment-comment",isNVue:!1,pagePath:"pages/comment/comment",windowTop:44}},{path:"/preview-image",component:{render:function(e){return e("Page",{props:{navigationStyle:"custom"}},[e("system-preview-image",{slot:"page"})])}},meta:{name:"preview-image",pagePath:"/preview-image"}},{path:"/choose-location",component:{render:function(e){return e("Page",{props:{navigationStyle:"custom"}},[e("system-choose-location",{slot:"page"})])}},meta:{name:"choose-location",pagePath:"/choose-location"}},{path:"/open-location",component:{render:function(e){return e("Page",{props:{navigationStyle:"custom"}},[e("system-open-location",{slot:"page"})])}},meta:{name:"open-location",pagePath:"/open-location"}}]}).call(this,o("c8ba"))},"90cd":function(e,n,o){var a=o("d195");"string"===typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);var r=o("4f06").default;r("f1d2417a",a,!0,{sourceMap:!1,shadowMode:!1})},a475:function(e,n,o){"use strict";var a=function(){var e=this,n=e.$createElement,o=e._self._c||n;return o("App",{attrs:{keepAliveInclude:e.keepAliveInclude}})},r=[];o.d(n,"a",(function(){return a})),o.d(n,"b",(function(){return r}))},d195:function(e,n,o){n=e.exports=o("2350")(!1),n.push([e.i,"uni-page-body{font-family:PingFangSC-Regular;font-size:%?24?%;color:#9b9b9b}.page{min-height:100vh;background:#f9f9f9}::-webkit-input-placeholder{color:#ccc}",""])},e313:function(e,n,o){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var a={onLaunch:function(){},onShow:function(){},onHide:function(){}};n.default=a},ecd6:function(e,n,o){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var a={appid:""};n.default=a}});