let hideProductSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	const productLookupCodeElement = getProductLookupCodeElement();

	getProductCountElement().addEventListener("keypress", productCountKeypress);
	productLookupCodeElement.addEventListener("keypress", productLookupCodeKeypress);
	
	getSaveActionElement().addEventListener("click", saveActionClick);
	getDeleteActionElement().addEventListener("click", deleteActionClick);

	if (!productLookupCodeElement.disabled) {
		productLookupCodeElement.focus();
		productLookupCodeElement.select();
	}
});

function productLookupCodeKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
	}

	const productCountElement = getProductCountElement();
	productCountElement.focus();
	productCountElement.select();
}

function productCountKeypress(event) {
	if (event.which !== 13) { // Enter key
		return;
	}

	saveActionClick();
}

// Save
function saveActionClick(event) {
	if (!validateSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const productId = getProductId();
	const productIdIsDefined = ((productId != null) && (productId.trim() !== ""));
	const saveActionUrl = ("/api/product/"
		+ (productIdIsDefined ? productId : ""));
	const saveProductRequest = {
		id: productId,
		count: getProductCount(),
		lookupCode: getProductLookupCode()
	};

	if (productIdIsDefined) {
		ajaxPut(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayProductSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveProductRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayProductSavedAlertModal();

				if ((callbackResponse.data != null)
					&& (callbackResponse.data.id != null)
					&& (callbackResponse.data.id.trim() !== "")) {

					document.getElementById("deleteActionContainer").classList.remove("hidden");

					setProductId(callbackResponse.data.id.trim());
				}
			}
		});
	}
};

function validateSave() {
	const lookupCode = getProductLookupCode();
	if ((lookupCode == null) || (lookupCode.trim() === "")) {
		displayError("Please provide a valid product lookup code.");
		return false;
	}

	const count = getProductCount();
	if ((count == null) || isNaN(count)) {
		displayError("Please provide a valid product count.");
		return false;
	} else if (count < 0) {
		displayError("Product count may not be negative.");
		return false;
	}

	return true;
}

function displayProductSavedAlertModal() {
	if (hideProductSavedAlertTimer) {
		clearTimeout(hideProductSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideProductSavedAlertTimer = setTimeout(hideProductSavedAlertModal, 1200);
}

function hideProductSavedAlertModal() {
	if (hideProductSavedAlertTimer) {
		clearTimeout(hideProductSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

// Delete
function deleteActionClick(event) {
	const deleteActionElement = event.target;
	const deleteActionUrl = ("/api/product/" + getProductId());

	deleteActionElement.disabled = true;

	ajaxDelete(deleteActionUrl, (callbackResponse) => {
		deleteActionElement.disabled = false;

		if (isSuccessResponse(callbackResponse)) {
			window.location.replace("/");
		}
	});
};
// End delete

// Getters and setters
function getSaveActionElement() {
	return document.getElementById("saveButton");
}

function getSavedAlertModalElement() {
	return document.getElementById("productSavedAlertModal");
}

function getDeleteActionElement() {
	return document.getElementById("deleteButton");
}

function getProductId() {
	return getProductIdElement().value;
}
function setProductId(productId) {
	getProductIdElement().value = productId;
}
function getProductIdElement() {
	return document.getElementById("productId");
}

function getProductLookupCode() {
	return getProductLookupCodeElement().value;
}
function getProductLookupCodeElement() {
	return document.getElementById("productLookupCode");
}

function getProductCount() {
	return Number(getProductCountElement().value);
}
function getProductCountElement() {
	return document.getElementById("productCount");
}
// End getters and setters
