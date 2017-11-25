function call(uri) {
	window.location = uri;
};

function callByCheck(checkInpt, uri) {
	if(checkInpt.checked){
		window.location = uri;
	}
};

function addQuantity(quantityInput,uri) {
	var quantity = quantityInput.value;
	window.location = uri + "/" + quantity;
};

$(document).ready(function(){
    $("#btn-category").click(function(){
        $("#categories").toggle(200);
    });
    $('.quantity').mask('000,000,000,000,000', {reverse: true});
    $('.quantity').blur(function(){
    	var quantity = $('.quantity').value;
    	window.location = '' + quantity;
    });
});

