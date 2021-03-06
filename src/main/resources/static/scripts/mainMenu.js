window.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById("productsButton").addEventListener('click', goToProductListing);
	document.getElementById("employeeButton").addEventListener('click', goToEmployeeDetail);
    document.getElementById("transactionButton").addEventListener('click', displayError);
	document.getElementById("salesButton").addEventListener('click', displayError);
	document.getElementById("cashierButton").addEventListener('click', displayError);
});

// Hide button from users that aren't managers
if(EmployeeClassification.isElevatedUser())
{
	document.getElementById("employeeButton").style.visbility="hidden";
	document.getElementById("salesButton").style.visbility="hidden";
	document.getElementById("cashierButton").style.visbility="hidden";
}


// Navigate to products listing page
function goToProductListing(event)
{
    window.location.assign(
		"/productListing/"
		);
}

// Navigate to employee detail page
function goToEmployeeDetail(event)
{
	window.location.assign("/employeeDetail/");
}

// Display error message
function displayError(errorMessage) {
    errorMessage = "Functionality has not yet been implemented.";
	if ((errorMessage == null) || (errorMessage === "")) {
		return;
	}

	const errorMessageDisplayElement = getErrorMessageDisplayElement();
	const errorMessageContainerElement = getErrorMessageContainerElement();

	if ((errorMessageContainerElement == null)
		|| (errorMessageDisplayElement == null)) {

		return;
	}

	errorMessageDisplayElement.innerHTML = errorMessage;
	if (errorMessageContainerElement.classList.contains("hidden")) {
		errorMessageContainerElement.classList.remove("hidden");
	}
}