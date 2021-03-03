window.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById("productsButton").addEventListener('click', navigate);
    document.getElementById("transactionButton").addEventListener('click', displayError);
	document.getElementById("salesButton").addEventListener('click', displayError);
	document.getElementById("cashierButton").addEventListener('click', displayError);
});


// Navigate to products listing page
function navigate(event)
{
    window.location.assign(
		"/productListing/"
		);
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