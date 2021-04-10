//Task 3 - Sign in verification

document.addEventListener("DOMContentLoaded", function(event) {

	const employeeIdEditElement = getEmployeeIdEditElement();

	employeeIdEditElement.focus();

	employeeIdEditElement.select();

});



function validateForm() {

	const employeeIdEditElement = getEmployeeIdEditElement();

	//Check the id
	if (isNaN(Number(employeeIdEditElement.value))

		|| (Number(employeeIdEditElement.value) <= 0)) {



		displayError("Please provide a valid employee ID. Please try again.");



		employeeIdEditElement.focus();

		employeeIdEditElement.select();

		

		return false;

	}



	const passwordEditElement = getPasswordEditElement();

	//check to see if password is equal to null

	if ((passwordEditElement.value == null)

		|| (passwordEditElement.value.trim() === "")) {



		displayError("Please provide a valid password. It may not be blank. Please try again.");



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
