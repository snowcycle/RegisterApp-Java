document.addEventListener("DOMContentLoaded",function(event){
	const changeID = getchangeID();
	changeID.focus();
	changeID.select();
});

function getchangePword(){
//getter for password
return document.getElementById("password");	
}	

function getchangeID(){
//getter for ID 
return document.getElementById("number");	
}

function ValidationEvent(){
// This part deals with invalid inputs for the Employee ID 
	const changeID = getchangeID();
	if(isNaN(Number(changeID.value))){
		displayError("Enter a real ID");
		changeID.focus();
		changeID.select();
		return false;	
}
	else if (Number(changeID.value) <= 0){
		displayError("Enter a real ID");
		changeID.focus();
		changeID.select();
		return false;
}
//This part deals with invalid inputs for the Password 
const changePword = getchangePword();
 if (changePword.value == null){
		displayError("Enter real password");
		changePword.focus();
		changePword.select();
		return false;
 }
 else if (changePword.value.trim() === ""){
	 displayError("Enter a real password);
	 changePword.focus();
	 changePword.select();
	 return false;
	 
 }
 // Since this is a validation function, it should return true if valid input
 
 return true;
 
}