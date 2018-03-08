/**
 * Created by Administrator on 2018/2/25.
 */
//为ajax的post发送方式，send()中的超链接转义
//正转义
function transformLinkForAjaxPost(text) {
    //&进行转义
    var arr1=text.split('&');
    var temp='';
    for(var i=0;i<arr1.length;i++){
        if(i!==arr1.length-1){
            temp+=arr1[i]+'%26';
        }else{
            temp+=arr1[i];
        }
    }
    //+进行转义
    var arr2=temp.split('+');
    temp='';
    for(var j=0;j<arr2.length;j++){
        if(j!==arr2.length-1){
            temp+=arr2[j]+'%2B';
        }else{
            temp+=arr2[j];
        }
    }
    //%进行转义
    var arr3=temp.split('%');
    temp='';
    for(var k=0;k<arr3.length;k++){
        if(k!==arr3.length-1){
            temp+=arr3[k]+'%25';
        }else{
            temp+=arr3[k];
        }
    }

    return temp;
}
//反转义
//function ajaxPostTransformLink(text) {
//	//%进行转义
//    var arr3=temp.split('%25');
//    temp='';
//    for(var k=0;k<arr3.length;k++){
//        if(k!==arr3.length-1){
//            temp+=arr3[k]+'%';
//        }else{
//            temp+=arr3[k];
//        }
//    }
//    //+进行转义
//    var arr2=temp.split('%2B');
//    temp='';
//    for(var j=0;j<arr2.length;j++){
//        if(j!==arr2.length-1){
//            temp+=arr2[j]+'+';
//        }else{
//            temp+=arr2[j];
//        }
//    }
//	//&进行转义
//    var arr1=text.split('%26');
//    var temp='';
//    for(var i=0;i<arr1.length;i++){
//        if(i!==arr1.length-1){
//            temp+=arr1[i]+'&';
//        }else{
//            temp+=arr1[i];
//        }
//    }
//   
//    return temp;
//}

/*
 	示例：将text中的&替换为%26，将text中的+替换为%2b,以此类推，json字符串长度不限
 	var text='123&456+789%';
 	var json={'&':'%26','+':'%2B','%':'%25'};
 	
*/
function replaceAll(json,text){
    var temp=text;
    for(key in json){
        var arr=temp.split(key);
        var temp1='';
        for(var i=0;i<arr.length;i++){
            if(i!==arr.length-1){
                temp1+=arr[i]+json[key];
            }else{
                temp1+=arr[i];
                temp=temp1;
            }
        }
    }
    return temp;
}