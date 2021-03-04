package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.models.enums.EmployeeClassification;

public class EmployeeType {
	private int classification;
	public int getClassification() {
		return this.classification;
	}
	public EmployeeType setClassification(final int classification) {
		this.classification = classification;
		return this;
	}

	private String displayLabel;

	public String getDisplayLabel() {
		return this.displayLabel;
	}

	public EmployeeType setDisplayLabel(final String displayLabel) {
		this.displayLabel = displayLabel;
		return this;
	}

	public static EmployeeType[] allEmployeeTypes() {
		final EmployeeClassification[] employeeClassifications =
			EmployeeClassification.values();
		final EmployeeType[] employeeTypes =
			new EmployeeType[employeeClassifications.length];

		for (int i = 0; i < employeeClassifications.length; i++) {
			employeeTypes[i] = new EmployeeType(employeeClassifications[i]);
		}

		return employeeTypes;
	}

	public EmployeeType() {
		this(-1, StringUtils.EMPTY);
	}

	public EmployeeType(final EmployeeClassification employeeClassification) {
		this(
			employeeClassification.getClassification(),
			employeeClassification.getDisplayLabel());
	}

	public EmployeeType(final int classification, final String displayLabel) {
		this.displayLabel = displayLabel;
		this.classification = classification;
	}
}
