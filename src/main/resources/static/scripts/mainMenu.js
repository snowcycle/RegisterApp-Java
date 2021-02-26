document.addEventListener("DOMContentLoaded", () => {
	getViewProductsActionElement().addEventListener(
		"click",
		() => { window.location.assign("/productListing");  console.log("shrekswasf");});
});

function getViewProductsActionElement() {
	return document.getElementById("viewProductsButton");
}

