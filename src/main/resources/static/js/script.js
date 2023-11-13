const toggelSideber = () => {
	if ($('.side-navber').is(":visible")) {
		$(".side-navber").css("display", "none");
		$(".right-content").css("margin-left", "0%");

	} else {

		$(".side-navber").css("display", "block");
		$(".right-content").css("margin-left", "20%");

	}
}
const toggelnavber = () => {
	if ($('.navber-contant').is(":visible")) {
		$(".navber-contant").css("display", "none");

	}else{
			$(".navber-contant").css("display", "block");
		
	}


}