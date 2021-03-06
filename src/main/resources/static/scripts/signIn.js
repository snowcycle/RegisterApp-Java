document.addEventListener("DOMContentLoaded", function(event)
{
});

function validateForm()
{
	// Validate employeeId
	let employeeId = document.forms["signIn"]["employeeId"].value;

	if (employeeId.length == 0)
	{
		alert("Please enter your ID!");
		return false;
	}

	/*
	There is no need to check if the employeeId entered is an integer.
	This is because the form entry has type="number", which means this
	function won't even be called if the entered value isn't an integer.
	
	This function also doesn't need to check for ID size because the
	input's "max" attribute takes care of that.
	*/

	// Validate password
	let password = document.forms["signIn"]["password"].value;

	if (password.length == 0)
	{
		alert("Please enter your password!");
		return false;
	}

	return true;
}
