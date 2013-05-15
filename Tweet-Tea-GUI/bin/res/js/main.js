function initialize(){

	var found = "not found";
	for(var propertyName in window) {
		if (propertyName == "java") found = "found";
	}

	$("body").append(found);

	try{
		
		java.print();
		
	}
	catch(e){
		 $("body").append("<br>"+e.message+"<br>");
	}

}