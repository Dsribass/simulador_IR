package org.example.application.main;

import org.example.application.repository.InMemoryExpensesDAO;
import org.example.application.repository.InMemoryTaxPayerDAO;
import org.example.application.view.App;
import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.expenses.ExpensesType;
import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.expenses.ExpensesDAO;
import org.example.domain.usecases.expenses.ExpensesUseCases;
import org.example.domain.usecases.incometax.FullDeclarationUseCase;
import org.example.domain.usecases.incometax.SimpleDeclarationUseCase;
import org.example.domain.usecases.taxpayers.TaxPayerDAO;
import org.example.domain.usecases.taxpayers.TaxPayerUseCases;

import java.util.Optional;

public class Main {
    private static final TaxPayerDAO taxPayerDAO = new InMemoryTaxPayerDAO();
    public static final TaxPayerUseCases taxPayerUseCases = new TaxPayerUseCases(taxPayerDAO);
    private static final ExpensesDAO expensesDAO = new InMemoryExpensesDAO();
    public static final ExpensesUseCases expensesUseCases = new ExpensesUseCases(expensesDAO,taxPayerDAO);

    public static final SimpleDeclarationUseCase simpleDeclarationUseCase = new SimpleDeclarationUseCase(taxPayerDAO);
    public static final FullDeclarationUseCase fullDeclarationUseCase = new FullDeclarationUseCase(taxPayerDAO);

    public static void main(String[] args) {
        String idTaxPlayer1 = createTaxPayer("Daniel",120000.0,0.0);
        String idTaxPlayer2 = createTaxPayer("José",80000.0,200.0);

        TaxPayer taxpayer1 = taxPayerUseCases.findOne(idTaxPlayer1).get();
        TaxPayer taxpayer2 = taxPayerUseCases.findOne(idTaxPlayer2).get();

        Expense expense = createExpenses(taxpayer1);
        Expense expense2 = createExpenses(taxpayer1);
        Expense expense3 = createExpenses(taxpayer2);

        Double valueTotalExpenses1 = expensesUseCases.getValueTotalExpenses(taxpayer1);
        Double valueTotalExpenses2 = expensesUseCases.getValueTotalExpenses(taxpayer2);
        taxpayer1.setTotalExpenses(valueTotalExpenses1);
        taxpayer2.setTotalExpenses(valueTotalExpenses2);
        App.main(args);
    }

    private static Expense createExpenses(TaxPayer taxPayer) {
        Expense expense = new Expense(taxPayer);
        expense.setName("Clinica");
        expense.setType(ExpensesType.HEALTH);
        expense.setValueSpent(16000.00);
        expensesDAO.insert(expense);
        return expense;
    }

    private static void simulateIncomeTax(TaxPayer taxPayer) {
        try {
            Double valueToPayForSimpleDeclaration = simpleDeclarationUseCase.simulateSimpleDeclaration(taxPayer);
            Double valueToPayForFullDeclaration = fullDeclarationUseCase.simulateFullDeclaration(taxPayer);

            System.out.println("\n-----Imposto de Renda-----");
            System.out.printf("\nDeclaração Simples: R$%.2f",valueToPayForSimpleDeclaration);
            System.out.printf("\nDeclaração Completa: R$%.2f",valueToPayForFullDeclaration);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static String createTaxPayer(String name,Double annualIncome, Double taxWithholding) {
        TaxPayer taxPayer = new TaxPayer();
        taxPayer.setId();
        taxPayer.setName(name);
        taxPayer.setAnnualTaxableIncome(annualIncome);
        taxPayer.setTaxWithholding(taxWithholding);

        return taxPayerUseCases.insert(taxPayer);
    }
}
