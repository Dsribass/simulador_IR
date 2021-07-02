package org.example.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.application.Routes;
import org.example.application.view.App;
import org.example.domain.entities.taxpayer.TaxPayer;

import java.io.IOException;
import java.text.DecimalFormat;

import static org.example.application.main.Main.*;

public class GenerateDeclarationUIController {
    @FXML private Label txtFullDeclaration;
    @FXML private Label txtSimpleDeclaration;

    TaxPayer taxPayer;

    public void setData(TaxPayer taxPayer){
        this.taxPayer = taxPayer;
        setDeclarationToView();
    }

    private void setDeclarationToView(){
        Double valueSimpleDeclaration = simpleDeclarationUseCase.simulateSimpleDeclaration(taxPayer);
        Double valueFullDeclaration = fullDeclarationUseCase.simulateFullDeclaration(taxPayer);
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("R$ #,##0.00");
        txtSimpleDeclaration.setText(df.format(valueSimpleDeclaration));
        txtFullDeclaration.setText(df.format(valueFullDeclaration));
    }

    public void returnPage(ActionEvent actionEvent) throws IOException {
        App.setRoot(Routes.main);
    }
}
