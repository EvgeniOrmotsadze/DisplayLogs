
$( document ).ready(function() {
     getResult();
     $("#click").click(function(){
         updateInterval();
    });
});

function getResult(){
    $.ajax({
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        mimeType: 'application/json',
        async: true,
        url: "/getResult",
        success: function (result) {
            console.log(result);
            display(result);
        }
    });
}

function display(result){
    var obj = result[0];
    $("#table tbody").empty();
    $("#table tbody").append("<tr><td>WARNING</td><td>" + obj.WARNING + "</td></tr> " +
                             "<tr><td>INFO</td><td>" + obj.INFO + "</td></tr>" +
                             "<tr><td>ERROR</td><td>" +obj.ERROR +"</td></tr>");
    $("#current_time").html("given system time : " + result[1]);
    $("#current_interval").html("current interval is :  " + result[2] + " sec");
}

function updateInterval(){
    var second = $("#interval").val();
    if(second != "") {
        $.ajax({
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            async: true,
            url: "/updateInterval/" + second,
            success: function (result) {
                if (result == true) {
                    getResult();
                } else {
                    alert("can't update interval, please check interval value!")
                }
            }
        });
    }else{
        alert('please set second')
    }
}
