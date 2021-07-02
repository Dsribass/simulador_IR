package org.example.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.application.Routes;
import org.example.application.view.App;
import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.taxpayer.TaxPayer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.application.main.Main.expensesUseCases;
import static org.example.application.main.Main.taxPayerUseCases;

public class ExpensesManagementUIController {
    @FXML private Button btnAddExpense;
    @FXML private Button btnDeleteExpense;
    @FXML private Button returnPage;
    @FXML private TableView<Expense> tableViewExpenses;
    @FXML private TableColumn<Expense,String> cNameExpense;
    @FXML private TableColumn<Expense,String> cExpense;
    @FXML private TableColumn<Expense,Double> cValue;

    TaxPayer taxPayer;
    UIMode uiMode;
    ObservableList<Expense> snapshot;

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadList();
    }

    private void bindColumnsToValueSources() {
        cNameExpense.setCellValueFactory(new PropertyValueFactory<>("name"));
        cExpense.setCellValueFactory(new PropertyValueFactory<>("type"));
        cValue.setCellValueFactory(new PropertyValueFactory<>("valueSpent"));
    }

    private void bindTableViewToItemsList() {
        snapshot = FXCollections.observableArrayList();
        tableViewExpenses.setItems(snapshot);
    }

    private void loadList() {
        if(taxPayer != null){
            List<Expense> expenses = expensesUseCases.fetchAllByTaxPayer(taxPayer);
            snapshot.clear();
            snapshot.addAll(expenses);
        }
    }

    public void addExpense(ActionEvent actionEvent) throws IOException {
        App.setRoot(Routes.expenses);
        ExpensesUIController controller = (ExpensesUIController) App.getController();
        controller.setData(taxPayer,uiMode);
    }

    public void deleteExpense(ActionEvent actionEvent) {
        Expense expense = tableViewExpenses.getSelectionModel().getSelectedItem();
        if(expense != null){
            expensesUseCases.delete(expense);
            loadList();
        }
    }

    public void btnReturnPage(ActionEvent actionEvent) throws IOException {
        if (taxPayer != null) {
            App.setRoot(Routes.user);
            UserUIController userUI = (UserUIController) App.getController();
            userUI.setData(taxPayer,uiMode);
        }
    }

    public void setData(String idTaxPayer, UIMode uiMode) throws IOException {
        this.uiMode = uiMode;
        Optional<TaxPayer> taxPayerOptional = taxPayerUseCases.findOne(idTaxPayer);
        if(taxPayerOptional.isEmpty())
            App.setRoot(Routes.main);
        else {
            this.taxPayer = taxPayerOptional.get();
            loadList();
        }
    }

    public void concludeRegister(ActionEvent actionEvent) throws IOException {
        saveTotalExpenses();
        App.setRoot(Routes.main);
    }

    private void saveTotalExpenses() {
        Double valueTotalExpenses = expensesUseCases.getValueTotalExpenses(taxPayer);
        taxPayer.setTotalExpenses(valueTotalExpenses);
        taxPayerUseCases.update(taxPayer.getId(),taxPayer);
    }
}
