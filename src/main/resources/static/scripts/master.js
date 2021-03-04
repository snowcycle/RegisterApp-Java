document.addEventListener("DOMContentLoaded", () => {
	const signOutActionElement = getSignOutActionElement();
	if (signOutActionElement != null) {
		signOutActionElement.addEventListener("click", signOutActionClickHandler);
	}
});

// AJAX
function ajaxGet(resourceRelativeUri, callback) {
	return ajax(resourceRelativeUri, "GET", null, callback);
}

function ajaxPut(resourceRelativeUri, data, callback) {
	return ajax(resourceRelativeUri, "PUT", data, callback);
}

function ajaxPatch(resourceRelativeUri, data, callback) {
	return ajax(resourceRelativeUri, "PATCH", data, callback);
}

function ajaxPost(resourceRelativeUri, data, callback) {
	return ajax(resourceRelativeUri, "POST", data, callback);
}

function ajaxDelete(resourceRelativeUri, callback) {
	return ajax(resourceRelativeUri, "DELETE", null, callback);
}

function ajax(resourceRelativeUri, verb, data, callback) {
	const httpRequest = new XMLHttpRequest();

	if (httpRequest == null) {
		return httpRequest;
	}

	httpRequest.onreadystatechange = () => {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if ((httpRequest.status >= 200) && (httpRequest.status < 300)) {
				handleSuccessResponse(httpRequest, callback);
			} else {
				handleFailureResponse(httpRequest, callback);
			}
		}
	};

	httpRequest.open(verb, resourceRelativeUri, true);
	if (data != null) {
		httpRequest.setRequestHeader('Content-Type', 'application/json');
		httpRequest.send(JSON.stringify(data));
	} else {
		httpRequest.send();
	}

	return httpRequest;
}

function handleSuccessResponse(httpRequest, callback) {
	clearError();

	if (callback != null) {
		let callbackResponse = { status: httpRequest.status };

		if ((httpRequest.responseText != null)
			&& (httpRequest.responseText !== "")) {

			let responseObject = JSON.parse(httpRequest.responseText);
			if (responseObject != null) {
				callbackResponse.data = responseObject;
			}
		}

		callback(callbackResponse);
	}
}

function handleFailureResponse(httpRequest, callback) {
	if ((httpRequest == null) || (httpRequest.status === 0)) {
		return;
	}

	let errorMessage = "Unable to complete the requested action.";

	if ((httpRequest.responseText != null)
		&& (httpRequest.responseText !== "")) {

		let responseObject = JSON.parse(httpRequest.responseText);

		if ((responseObject != null)
			&& (responseObject.redirectUrl != null)
			&& (responseObject.redirectUrl !== "")) {

			if (callback) {
				callback({ status: httpRequest.status });
			}
			window.location.assign(responseObject.redirectUrl);

			return;
		}

		if ((responseObject != null)
			&& (responseObject.errorMessage != null)
			&& (responseObject.errorMessage !== "")) {

			errorMessage = responseObject.errorMessage;
		}
	}

	displayError(errorMessage);

	if (callback != null) {
		callback({ status: httpRequest.status });
	}
}
// End AJAX

function isSuccessResponse(callbackResponse) {
	return ((callbackResponse != null)
		&& (callbackResponse.status != null)
		&& (callbackResponse.status >= 200)
		&& (callbackResponse.status < 300));
}

function isErrorResponse(callbackResponse) {
	return !isSuccessResponse(callbackResponse);
}

// Display error message
function clearError() {
	const errorMessageContainerElement = getErrorMessageContainerElement();

	if ((errorMessageContainerElement == null)
		|| errorMessageContainerElement.classList.contains("hidden")) {

		return;
	}

	errorMessageContainerElement.classList.add("hidden");

	const errorMessageDisplayElement = getErrorMessageDisplayElement();

	if (errorMessageDisplayElement != null) {
		errorMessageDisplayElement.innerHTML = "";
	}
}

function displayError(errorMessage) {
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
// End display error message

//Getters and setters
function getSignOutActionElement() {
	return document.getElementById("signOutImage");
}

function getErrorMessageContainerElement() {
	return document.getElementById("error");
}

function getErrorMessageDisplayElement() {
	return document.getElementById("errorMessage");
}
// End getters and setters

//Sign out
function signOutActionClickHandler() {
	ajaxDelete("/api/signOut", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/");
		}
	});
}
//End sign out