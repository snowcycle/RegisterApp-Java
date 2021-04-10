document.addEventListener("DOMContentLoaded", function(event) {
	getStartTransactionActionElement().addEventListener(
		"click",
		() => { displayError("Functionality has not yet been implemented."); });

	getViewProductsActionElement().addEventListener(
		"click",
		() => { window.location.assign("/productListing"); });

	getCreateEmployeeActionElement().addEventListener(
		"click",
		() => { window.location.assign("/employeeDetail"); });

	getProductSalesReportActionElement().addEventListener(
		"click",
		() => { displayError("Functionality has not yet been implemented."); });

	getCashierSalesReportActionElement().addEventListener(
		"click",
		() => { displayError("Functionality has not yet been implemented."); });

});

// Getters and setters
function getViewProductsActionElement() {
	return document.getElementById("viewProducts");
}

function getCreateEmployeeActionElement() {
	return document.getElementById("createEmployee");
}

function getStartTransactionActionElement() {
	return document.getElementById("startTransaction");
}

function getProductSalesReportActionElement() {
	return document.getElementById("salesReport");
}

function getCashierSalesReportActionElement() {
	return document.getElementById("cashiersReport");
}