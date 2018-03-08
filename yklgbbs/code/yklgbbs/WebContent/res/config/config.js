/**
 * Created by Administrator on 2018/2/26.
 */
function getFaceConfig() {
    var json={'face[微笑]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/5c/huanglianwx_thumb.gif"/>',
        'face[嘻嘻]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/0b/tootha_thumb.gif"/>',
        'face[哈哈]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6a/laugh.gif"/>',
        'face[可爱]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/tza_thumb.gif"/>',
        'face[可怜]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/af/kl_thumb.gif"/>',
        'face[挖鼻]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/0b/wabi_thumb.gif"/>',
        'face[吃惊]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/f4/cj_thumb.gif"/>',
        'face[害羞]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6e/shamea_thumb.gif"/>',
        'face[挤眼]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c3/zy_thumb.gif"/>',
        'face[闭嘴]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/29/bz_thumb.gif"/>',
        'face[鄙视]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/71/bs2_thumb.gif"/>',
        'face[爱你]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/lovea_thumb.gif"/>',
        'face[泪]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/9d/sada_thumb.gif"/>',
        'face[偷笑]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/19/heia_thumb.gif"/>',
        'face[亲亲]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/8f/qq_thumb.gif"/>',
        'face[生病]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/b6/sb_thumb.gif"/>',
        'face[太开心]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/58/mb_thumb.gif"/>',
        'face[白眼]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d9/landeln_thumb.gif"/>',
        'face[右哼哼]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/98/yhh_thumb.gif"/>',
        'face[左哼哼]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/zhh_thumb.gif"/>',
        'face[嘘]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/a6/x_thumb.gif"/>',
        'face[衰]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/af/cry.gif"/>',
        'face[委屈]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/73/wq_thumb.gif"/>',
        'face[吐]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/9e/t_thumb.gif"/>',
        'face[哈欠]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/cc/haqianv2_thumb.gif"/>',
        'face[抱抱]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/27/bba_thumb.gif"/>',
        'face[怒]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/7c/angrya_thumb.gif"/>',
        'face[疑问]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/5c/yw_thumb.gif"/>',
        'face[馋嘴]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/a5/cza_thumb.gif"/>',
        'face[拜拜]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/70/88_thumb.gif"/>',
        'face[思考]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/e9/sk_thumb.gif"/>',
        'face[汗]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/24/sweata_thumb.gif"/>',
        'face[困]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/kunv2_thumb.gif"/>',
        'face[睡]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/96/huangliansj_thumb.gif"/>',
        'face[钱]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/90/money_thumb.gif"/>',
        'face[失望]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/0c/sw_thumb.gif"/>',
        'face[酷]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/cool_thumb.gif"/>',
        'face[色]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/20/huanglianse_thumb.gif"/>',
        'face[哼]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/49/hatea_thumb.gif"/>',
        'face[鼓掌]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/36/gza_thumb.gif"/>',
        'face[晕]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d9/dizzya_thumb.gif"/>',
        'face[悲伤]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/1a/bs_thumb.gif"/>',
        'face[抓狂]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/62/crazya_thumb.gif"/>',
        'face[黑线]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/91/h_thumb.gif"/>',
        'face[阴险]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/yx_thumb.gif"/>',
        'face[怒骂]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/60/numav2_thumb.gif"/>',
        'face[互粉]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/89/hufen_thumb.gif"/>',
        'face[心]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/hearta_thumb.gif"/>',
        'face[伤心]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/ea/unheart.gif"/>',
        'face[猪头]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/58/pig.gif"/>',
        'face[熊猫]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6e/panda_thumb.gif"/>',
        'face[兔子]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/81/rabbit_thumb.gif"/>',
        'face[ok]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d6/ok_thumb.gif"/>',
        'face[耶]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d9/ye_thumb.gif"/>',
        'face[good]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d8/good_thumb.gif"/>',
        'face[NO]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/ae/buyao_org.gif"/>',
        'face[赞]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d0/z2_thumb.gif"/>',
        'face[来]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/come_thumb.gif"/>',
        'face[弱]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d8/sad_thumb.gif"/>',
        'face[草泥马]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/7a/shenshou_thumb.gif"/>',
        'face[神马]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/60/horse2_thumb.gif"/>',
        'face[囧]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/15/j_thumb.gif"/>',
        'face[浮云]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/bc/fuyun_thumb.gif"/>',
        'face[给力]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/1e/geiliv2_thumb.gif"/>',
        'face[围观]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/f2/wg_thumb.gif"/>',
        'face[威武]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/70/vw_thumb.gif"/>',
        'face[奥特曼]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/bc/otm_thumb.gif"/>',
        'face[礼物]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/c4/liwu_thumb.gif"/>',
        'face[钟]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d3/clock_thumb.gif"/>',
        'face[话筒]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/9f/huatongv2_thumb.gif"/>',
        'face[蜡烛]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/d9/lazhuv2_thumb.gif"/>',
        'face[蛋糕]':'<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/3a/cakev2_thumb.gif"/>'};

    return json;
}

//给在文本域中获取的特殊字符进行转义
function getContentConfig() {
    var json={'<':'&lt;','>':'&gt;',' ':'&nbsp;','\n':'<br/>',
    		  'img[':'<img src="',']img':'"/>','a[':'<a href="',']a':'">','[/a]':'</a>',
    		  '[pre]':'<pre>','[/pre]':'</pre>'};
    
    return json;
}

//获取等级，在js中执转换等级是为了减少服务器的压力
function getGrade(experience) {
    if(experience<=50){
        return '初级粉丝';
    }else if(experience>50 && experience<=150){
        return '中级粉丝';
    }else if(experience>150 && experience<=300){
        return '高级粉丝';
    }else if(experience>300 && experience<=500){
        return '正式会员';
    }else if(experience>500 && experience<=750){
        return '核心会员';
    }else if(experience>750 && experience<=1050){
        return '铁杆会员';
    }else if(experience>1050 && experience<=1400){
        return '知名人士';
    }else if(experience>1400 && experience<=1800){
        return '人气楷模';
    }else if(experience>1800 && experience<=2250){
        return '意见领袖';
    }else if(experience>2250 && experience<=3250){
        return '进阶元老';
    }else if(experience>3250 && experience<=5000){
        return '资深元老';
    }else if(experience>5000 && experience<=8000){
        return '荣耀元老';
    }else if(experience>8000){
        return '超神';
    }else{
        return '无名者';
    }
}

/*function getGrade(experience) {
    if(experience<=50){
        return '初级粉丝';
    }else if(experience>50 && experience<=150){
        return '中级粉丝';
    }else if(experience>150 && experience<=300){
        return '高级粉丝';
    }else if(experience>300 && experience<=500){
        return '正式会员';
    }else if(experience>500 && experience<=750){
        return '核心会员';
    }else if(experience>750 && experience<=1050){
        return '铁杆会员';
    }else if(experience>1050 && experience<=1400){
        return '知名人士';
    }else if(experience>1400 && experience<=1800){
        return '人气楷模';
    }else if(experience>1800 && experience<=2250){
        return '意见领袖';
    }else if(experience>2250 && experience<=3250){
        return '进阶元老';
    }else if(experience>3250 && experience<=5000){
        return '资深元老';
    }else if(experience>5000){
        return '荣耀元老';
    }else{
        return '无名者';
    }
}*/
