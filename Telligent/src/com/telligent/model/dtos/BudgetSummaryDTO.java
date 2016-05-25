package com.telligent.model.dtos;

public class BudgetSummaryDTO {
	
 private String anualBudgetType;
 private String currentBudget;
 private String newBudget;
 private String changeBudget;
/**
 * @return the anualBudgetType
 */
public String getAnualBudgetType() {
	return anualBudgetType;
}
/**
 * @param anualBudgetType the anualBudgetType to set
 */
public void setAnualBudgetType(String anualBudgetType) {
	
	if (anualBudgetType == null || anualBudgetType.equals("0") || anualBudgetType.equals("0.0")|| anualBudgetType.equals("0.00")){
		anualBudgetType = "";
	}else if (anualBudgetType.equalsIgnoreCase("3")){
		anualBudgetType = "Hourly";
	}else if (anualBudgetType.equalsIgnoreCase("5")){
		anualBudgetType = "Office";
	}
	this.anualBudgetType = anualBudgetType;
}
/**
 * @return the currentBudget
 */
public String getCurrentBudget() {
	return currentBudget;
}
/**
 * @param currentBudget the currentBudget to set
 */
public void setCurrentBudget(String currentBudget) {
	
	if (currentBudget == null || currentBudget.equals("0") || currentBudget.equals("0.0")|| currentBudget.equals("0.00")){
		currentBudget = "";
	}
	this.currentBudget = currentBudget;
}
/**
 * @return the newBudget
 */
public String getNewBudget() {
	return newBudget;
}
/**
 * @param newBudget the newBudget to set
 */
public void setNewBudget(String newBudget) {
	
	if (newBudget == null || newBudget.equals("0") || newBudget.equals("0.0")|| newBudget.equals("0.00")){
		newBudget = "";
	}
	this.newBudget = newBudget;
}
/**
 * @return the changeBudget
 */
public String getChangeBudget() {
	return changeBudget;
}
/**
 * @param changeBudget the changeBudget to set
 */
public void setChangeBudget(String changeBudget) {
	
	if (changeBudget == null || changeBudget.equals("0") || changeBudget.equals("0.0")|| changeBudget.equals("0.00")){
		changeBudget = "";
	}
	this.changeBudget = changeBudget;
}
 
	
}
