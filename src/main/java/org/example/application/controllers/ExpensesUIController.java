package org.example.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.application.Routes;
import org.example.application.view.App;
import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.expenses.ExpensesType;
import org.example.domain.entities.taxpayer.TaxPayer;

import java.io.IOException;

import static org.example.application.main.Main.expensesUseCases;

public class ExpensesUIController {
    @FXML private TextField txtValueSpent;
    @FXML private ComboBox<String> comboExpenses;
    @FXML private TextField txtName;

    TaxPayer taxPayer;
    private UIMode uiMode;

    @FXML
    public void initialize(){
        comboExpenses.getItems().add(ExpensesType.HEALTH.toString());
        comboExpenses.getItems().add(ExpensesType.EDUCATION.toString());
        comboExpenses.getItems().add(ExpensesType.DONATIONS.toString());
        comboExpenses.getItems().add(ExpensesType.DEPENDENTS.toString());
    }

    public void setData(TaxPayer taxPayer,UIMode uiMode){
        this.taxPayer = taxPayer;
        this.uiMode = uiMode;
    }

    public Integer getAndSaveInformationFromView() {
        Expense expense = new Expense(taxPayer);
        expense.setName(txtName.getText());
        expense.setType(ExpensesType.toEnum(comboExpenses.getSelectionModel().getSelectedItem()));
        expense.setValueSpent(Double.valueOf(txtValueSpent.getText()));

        return expensesUseCases.insert(expense);
    }

    public void registerExpense(ActionEvent actionEvent) throws IOException {
        if(isFilledTxtFields()){
            getAndSaveInformationFromView();
            App.setRoot(Routes.expensesManagement);
            ExpensesManagementUIController expensesManagement = (ExpensesManagementUIController) App.getController();
            expensesManagement.setData(taxPayer.getId(),uiMode);
        }
    }

    private boolean isFilledTxtFields() {
        return !txtName.getText().isEmpty() &&
                !txtValueSpent.getText().isEmpty() &&
                comboExpenses.getSelectionModel().getSelectedItem() != null;
    }

    public void returnPage(ActionEvent actionEvent) throws IOException {
        App.setRoot(Routes.expensesManagement);
        ExpensesManagementUIController expensesManagement = (ExpensesManagementUIController) App.getController();
        expensesManagement.setData(taxPayer.getId(),uiMode);
    }
}
