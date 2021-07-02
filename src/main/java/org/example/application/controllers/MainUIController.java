package org.example.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.application.Routes;
import org.example.application.view.App;
import org.example.domain.entities.taxpayer.TaxPayer;

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.taxPayerUseCases;

public class MainUIController {
    @FXML private TableView<TaxPayer> tableViewUsers;
    @FXML private TableColumn<TaxPayer,String> cName;
    @FXML private TableColumn<TaxPayer,Double> cExpenses;
    @FXML private TableColumn<TaxPayer,Double> cAnnualIncome;
    @FXML private TableColumn<TaxPayer,Double> cTaxWithholding;

    ObservableList<TaxPayer> snapshot;

    @FXML
    private void initialize() {
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadList();
    }

    private void bindColumnsToValueSources() {
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cExpenses.setCellValueFactory(new PropertyValueFactory<>("totalExpenses"));
        cAnnualIncome.setCellValueFactory(new PropertyValueFactory<>("annualTaxableIncome"));
        cTaxWithholding.setCellValueFactory(new PropertyValueFactory<>("taxWithholding"));
    }

    private void bindTableViewToItemsList() {
        snapshot = FXCollections.observableArrayList();
        tableViewUsers.setItems(snapshot);
    }

    private void loadList() {
        List<TaxPayer> taxPayers = taxPayerUseCases.fetchAll();
        snapshot.clear();
        snapshot.addAll(taxPayers);
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        App.setRoot(Routes.user);
        UserUIController userUI = (UserUIController) App.getController();
        userUI.setData(null,UIMode.INSERT);
    }

    public void updateUser(ActionEvent actionEvent) throws IOException {
        TaxPayer taxPayer = tableViewUsers.getSelectionModel().getSelectedItem();
        if (taxPayer != null) {
            App.setRoot(Routes.user);
            UserUIController userUI = (UserUIController) App.getController();
            userUI.setData(taxPayer,UIMode.UPDATE);
        }
    }

    public void deleteUser(ActionEvent actionEvent) throws IOException {
        TaxPayer taxPayer = tableViewUsers.getSelectionModel().getSelectedItem();
        if (taxPayer != null) {
            taxPayerUseCases.delete(taxPayer);
            loadList();
        }
    }

    public void generateDeclaration(ActionEvent actionEvent) throws IOException {
        TaxPayer taxPayer = tableViewUsers.getSelectionModel().getSelectedItem();
        if (taxPayer != null) {
            App.setRoot(Routes.generateDeclaration);
            GenerateDeclarationUIController generateDeclaration = (GenerateDeclarationUIController) App.getController();
            generateDeclaration.setData(taxPayer);
        }
    }
}
