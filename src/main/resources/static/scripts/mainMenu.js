// This is the functionality for the main menu.
import{SignOutActionClickHandler} from './master.js';

document.addEventListener("DOMContentLoaded", function(event) 
{
	startTransaction.addEventListener("click", transactionActionElement);

	viewProducts.addEventListener("click", productsActionElement);

	createEmployee.addEventListener("click", employeeActionElement);

	salesReport.addEventListener("click", productSalesReportActionElement);

	cashiersReport.addEventListener("click", cashierSalesReportActionElement);

	signOut.addEventListener("click", signOutActionElement); // signOut is the id of the sign out button
	
});


function transactionActionElement() {
	document.getElementById("errorMessage").innerHTML = "Functionality has not yet been implemented";
}

function productsActionElement() {
	window.location.href = "\productListing.html"; // go to the product listing page
}

function employeeActionElement() {
	window.location.href = "\employeeDetails.html"; // go to the employee detail page
}

function productSalesReportActionElement() {
	document.getElementById("errorMessage").innerHTML = "Functionality has not yet been implemented";

}

function cashierSalesReportActionElement() {
	document.getElementById("errorMessage").innerHTML = "Functionality has not yet been implemented";

}

function signOutActionElement() {
	SignOutActionClickHandler();
	window.location.href = "\signIn.html"; // Go to the sign in page

}