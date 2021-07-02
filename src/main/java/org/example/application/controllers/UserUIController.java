package org.example.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.application.Routes;
import org.example.application.view.App;
import org.example.domain.entities.taxpayer.TaxPayer;
import java.io.IOException;

import static org.example.application.main.Main.taxPayerUseCases;

public class UserUIController {
    @FXML private TextField txtName;
    @FXML private TextField txtAnnualIncome;
    @FXML private TextField txtTaxWithholding;

    private TaxPayer taxPayer;
    private UIMode uiMode;

    public void setData(TaxPayer taxPayer, UIMode uiMode) {
        this.taxPayer = taxPayer;
        this.uiMode = uiMode;
        if (taxPayer != null) {
            txtName.setText(taxPayer.getName());
            txtAnnualIncome.setText(taxPayer.getAnnualTaxableIncome().toString());
            txtTaxWithholding.setText(taxPayer.getTaxWithholding().toString());
        }
    }

    public String getAndSaveInformationFromView() {
        if(taxPayer == null){
            taxPayer = new TaxPayer();
        }
        taxPayer.setName(txtName.getText());
        taxPayer.setAnnualTaxableIncome(Double.valueOf(txtAnnualIncome.getText()));
        taxPayer.setTaxWithholding(Double.valueOf(txtTaxWithholding.getText()));

        switch (uiMode){
            case INSERT: {
                return taxPayerUseCases.insert(taxPayer);
            }
            case UPDATE: {
                taxPayerUseCases.update(taxPayer.getId(),taxPayer);
                return taxPayer.getId();
            }
            default: return null;
        }
    }

    public void proceedRegister(ActionEvent actionEvent) throws IOException {
        if(isFilledTxtFields()){
            App.setRoot(Routes.expensesManagement);
            ExpensesManagementUIController controller = (ExpensesManagementUIController) App.getController();
            controller.setData(getAndSaveInformationFromView(),uiMode);
        }
    }

    private boolean isFilledTxtFields() {
        return !txtAnnualIncome.getText().trim().isEmpty() && !txtTaxWithholding.getText().trim().isEmpty();
    }

    public void returnPage(ActionEvent actionEvent) throws IOException {
        App.setRoot(Routes.main);
    }
}
