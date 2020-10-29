var num = "";
var btn = getId('btn');
var test = getId('test');

$('#findPhoto').click(function() {
    $.ajax({
        url:"find",
        type:"POST",
        dataType:"json",
        contentType : "application/json;charset=UTF-8",
        data:{
            name:"open"
        },
        success(data){
            console.log(JSON.parse(data.success));
            var data = JSON.parse(data.success);
            var resoult = "";
            for(var i in data.TextDetections){
                resoult += data.TextDetections[i].DetectedText + "\n";
            }
            alert(resoult);
        }
    })
})



function getId(obj) {
    return document.getElementById(obj);
}
//点击Run执行输入框代码
function resect(){
    var str = test.value;
    var test2 = getId("test2");
    try{
        var arr_arr_arrs = new Array();;
        eval(str.replace(/console.log\(/g,"arr_arr_arrs.push("));
        for(var i in arr_arr_arrs){
            arr_arr_arrs[i] += '\n';
        }
        test2.value = arr_arr_arrs.toString().replace(/\,/g,"");
    }
    catch(err){
        test2.value = err;
    }
}
//光标所在位置的列数
var interceptNum;
document.getElementById("test").onclick = function(event){
    var str = test.value;
    if(str != ""){
        //光标所在位置
        interceptNum = event.target.selectionStart;
    }
};
function addStringButton(event){
    var str = test.value;
    //当粘贴信息没有防止光标时，在程序的末尾添加按钮信息
    if(interceptNum == undefined){
        var addAfter = str.slice(0,str.length) + event;
    }else{//当将光标防置在某一位置时，将按钮信息添加到指定位置
        var addBefore = str;
        var addAfter = str.slice(0,interceptNum) + event + str.slice(interceptNum,str.length);
    }
    test.value = addAfter;
}
function Alert(event){
    var alerts = document.getElementById("Alert");
    alerts.style.display = event;
}
function keyUp(){
    var str = test.value;
    str = str.replace(/\r/gi,"");
    str = str.split("\n");
    n = str.length;
    line(n);
}
function line(n){
    var lineobj = getId("leftNum");
    for(var i = 1;i <= n;i ++){
        if(document.all){
            num += i + "\r\n";//判断浏览器是否是IE
        }else{
            num += i + "\n";
        }
    }
    lineobj.value = num;
    num = "";
}
(function() {
    keyUp();
})();