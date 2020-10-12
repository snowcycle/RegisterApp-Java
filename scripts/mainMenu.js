
window.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById("start_TransactionButton").addEventListener("click", function() {
        displayError("Functionality has not yet been implemented");
    });  
    document.getElementById("view_ProductsButton").addEventListener("click", function() {
        location.assign("/productListing")});
    document.getElementById("Create_EmployeeButton").addEventListener("click", function() {
        location.assign("/employeeDetail")});
    document.getElementById("Sales_ReportButton").addEventListener("click", function() {
        displayError("Functionality has not yet been implemented")});
    document.getElementById("Cashier_ReportButton").addEventListener("click", function() {
        displayError("Functionality has not yet been implemented")});
    document.getElementById("signOutImg").addEventListener("click", function() {
            location.assign("/")});
    }); 

