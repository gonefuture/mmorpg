$(function () {
	$('#sender').click(function(){
         $.ajax({
	        url: 'http://localhost:8888',
	        type: "post",
	        data: {
	            name:"张三",
	            age:22
	               },
	        timeout:3,
	        dataType: "json",
	        success: function (data ,textStatus, jqXHR) {
	            alert(data);
	        },
	        error:function (XMLHttpRequest, textStatus, errorThrown) {      
	            alert("请求失败！");
	        }
     });

    });
	
})