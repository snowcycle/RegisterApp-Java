// document.addEventListener("DOMContentLoaded", function(event) {
// 	const employeeIdEditElement = document.getElementById('employeeId');
    
// 	employeeIdEditElement.focus();
// 	employeeIdEditElement.select();
// });



// function validateForm(){

//     const id = document.getElementById('employeeId')      //sets the variable to the employeeId
//     const pwd = document.getElementById('password')       //sets the variable to the password
//     console.log(id.value);

//     if(id != null && pwd != null )  //makes sure that the employeeId and password are not blank 
//     {
//         console.log("!isNaN(Number(id.value)" + (!isNaN(Number(id.value))));
//         if(!isNaN(Number(id.value)))    //checks to see if the emplyeeId is a number
//         {
//             console.log("id.value" + Number(id.value));
//             if(Number(id.value) > 0)     //checks to see if the id value is greater than 0
//             {
//                 if(pwd.value.trim() !=='')      //checks to see if the pwd value doesn't equal a blank after triming the whitespaces
//                 {
//                     return true;
//                 }else{
//                     alert("Please fill out a valid Password.");  //alerts the user if the password they've typed in doesn't meet the requirements.
//                     return false;
//                 }
//             } 
//             else{
//                 alert("Please fill out a valid EmployeeId1.");   //alerts the user if the EmployeeId they've typed in doesn't meet the requirements.
//                 return false;
//             }
//         }else{
//             alert("Please fill out a valid EmployeeId.");   //alerts the user if the EmployeeId they've typed in doesn't meet the requirements.
//             return false;
//         }
//     }else{
//         alert("Please fill out a valid EmployeeId and Password."); //alerts the user if the password & employeeId they've typed in doesn't meet the requirements.
//         return false;
//     }
// }

document.addEventListener("DOMContentLoaded", function(event) {
	const employeeIdEditElement = getEmployeeIdEditElement();
	employeeIdEditElement.focus();
	employeeIdEditElement.select();
});

function validateForm() {
	const employeeIdEditElement = getEmployeeIdEditElement();
	if (isNaN(Number(employeeIdEditElement.value))
		|| (Number(employeeIdEditElement.value) <= 0)) {

		displayError("Please provide a valid employee ID.");

		employeeIdEditElement.focus();
		employeeIdEditElement.select();
		
		return false;
	}

	const passwordEditElement = getPasswordEditElement();
	if ((passwordEditElement.value == null)
		|| (passwordEditElement.value.trim() === "")) {

		displayError("Please provide a valid password. It may not be blank.");

		passwordEditElement.focus();
		passwordEditElement.select();
		
		return false;
	}

	return true;
}

//Getters and setters
function getPasswordEditElement() {
	return document.getElementById("password");
}

function getEmployeeIdEditElement() {
	return document.getElementById("employeeId");
}
//End getters and setters