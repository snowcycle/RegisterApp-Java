document.addEventListener("DOMContentLoaded", function(event) {
	const employeeIdEditElement = getEmployeeId();
	employeeIdEditElement.focus();
	employeeIdEditElement.select();
);

//Get elements for the Employee Id and Password
function getEmployeeId() {
	return document.getElementById("employeeId");
}

function getPassword() {
	return document.getElementById("password");
}

//Validates the Id is not blank and is a number; Validates that Password is not null
function validateForm() {
	const employeeIdEditElement = getEmployeeId();
	if(isNaN(Number(employeeIdEditElement.value)) || (Number(employeeIdEditElement.value) <= 0))
	{
		displayError("Employee Id must be a positive numerical value.");

		employeeIdEditElement.focus();
		employeeIdEditElement.select();
		return false;
	}

	const passwordEditElement = getPassword();
	if ((passwordEditElement.value == null) || (passwordEditElement.value.trim() === ""))
	{
		displayError("Password must be valid and cannot be blank.");

		passwordEditElement.focus();
		passwordEditElement.select();
		return false;

	}
	return true;
}
