document.addEventListener("DOMContentLoaded", function(event) {

  getStartTransactionActionElement().addEventListener("click", () => { displayError("Functionality has not yet been implemented."); });
  getViewProductsActionElement().addEventListener("click", () => { window.location.assign("./productListing.html"); });

  getStartTransactionActionElement().addEventListener("click", () => { displayError("Functionality has not yet been implemented. Functionality"); });
  //getViewProductsActionElement().addEventListener("click", () => { window.location.assign("/src/main/resources/templates/productDetail.html"); });

  getCreateEmployeeActionElement().addEventListener("click", () => { window.location.assign("/employeeDetail"); });
  getProductSalesReportActionElement().addEventListener("click", () => { displayError("Functionality has not yet been implemented."); });
  getCashierSalesReportActionElement().addEventListener("click", () => { displayError("Functionality has not yet been implemented."); });
  //Additional functionality
  signOut().addEventListener("click", () => {window.location.assign("/signIn");});
});

// Getters and setters
function getViewProductsActionElement() {
	return document.getElementById("viewProductsButton");
}

function getCreateEmployeeActionElement() {
	return document.getElementById("createEmployeeButton");
}

function getStartTransactionActionElement() {
	return document.getElementById("startTransactionButton");
}

function getProductSalesReportActionElement() {
	return document.getElementById("productSalesReportButton");
}

function getCashierSalesReportActionElement() {
	return document.getElementById("cashierSalesReportButton");
}

function signOut() {
  return document.getElementById("signOutImage");
}
// End getters and setters
