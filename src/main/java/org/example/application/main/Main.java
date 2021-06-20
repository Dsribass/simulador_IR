package org.example.application.main;

import org.example.domain.entities.incometax.FullDeclaration;
import org.example.domain.entities.incometax.IncomeTaxDeclaration;
import org.example.domain.entities.incometax.SimpleDeclaration;
import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.expenses.ExpensesType;
import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.taxpayers.TaxPayerUseCases;
import org.example.domain.usecases.taxpayers.TaxPayerDAO;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final TaxPayerDAO taxPayerDAO = new TaxPayerDAO();
    private static final TaxPayerUseCases taxPayerUseCases = new TaxPayerUseCases(taxPayerDAO);

    public static void main(String[] args) {
        Integer idTaxPlayer = createTaxPayer();

        Optional<TaxPayer> optionalTaxPayer = taxPayerUseCases.findOne(idTaxPlayer);
        if(optionalTaxPayer.isEmpty()){
            System.out.println("Contribuinte não encontrado");
            return;
        }
        TaxPayer taxPayer = optionalTaxPayer.get();
        System.out.println("\nInformações do Contribuinte");
        System.out.println(taxPayer);
        taxPayer.getExpenses().forEach(System.out::println);

        simulateIncomeTax(taxPayer);
    }

    private static void simulateIncomeTax(TaxPayer taxPayer) {
        IncomeTaxDeclaration simpleDeclaration = new SimpleDeclaration();
        IncomeTaxDeclaration fullDeclaration = new FullDeclaration();

        try {
            Double valueToPayForSimpleDeclaration = simpleDeclaration.simulateIncomeTaxDeclaration(taxPayer);
            Double valueToPayForFullDeclaration = fullDeclaration.simulateIncomeTaxDeclaration(taxPayer);

            System.out.println("\n-----Imposto de Renda-----");
            System.out.printf("\nDeclaração Simples: R$%.2f",valueToPayForSimpleDeclaration);
            System.out.printf("\nDeclaração Completa: R$%.2f",valueToPayForFullDeclaration);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static Integer createTaxPayer() {
        TaxPayer taxPayer = new TaxPayer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Contribuinte:");
        System.out.print("\nRenda anual: ");
        taxPayer.setAnnualTaxableIncome(scanner.nextDouble());
        System.out.print("Valor pago na fonte: ");
        taxPayer.setTaxWithholding(scanner.nextDouble());

        setExpenses(taxPayer, scanner);


        try {
            return taxPayerUseCases.insert(taxPayer);
        }catch (Exception e){
            System.err.println(e.getMessage());
        };
        return null;
    }

    private static void setExpenses(TaxPayer taxPayer, Scanner scanner) {
        System.out.println("Despesas: ");
        System.out.print("Quantos gastos deseja cadastrar(-1 para finalizar) ? ");
        int length = scanner.nextInt();
        for (int i = 0;i < length;i++){
            System.out.println("\nTipo do Gasto");
            System.out.println("1 - Saúde");
            System.out.println("2 - Educação");
            System.out.println("3 - Dependentes");
            System.out.println("4 - Doações");
            System.out.print("=> ");
            int value = scanner.nextInt();
            Expense expense = new Expense();
            switch (value) {
                case 1:
                    expense.setType(ExpensesType.HEALTH);
                    break;
                case 2:
                    expense.setType(ExpensesType.EDUCATION);
                    break;
                case 3:
                    expense.setType(ExpensesType.DEPENDENTS);
                    break;
                case 4:
                    expense.setType(ExpensesType.DONATIONS);
                    break;
            }
            scanner.nextLine();
            System.out.print("Nome do Gasto: ");
            expense.setName(scanner.nextLine());
            System.out.print("Valor do Gasto: ");
            expense.setValueSpent(scanner.nextDouble());

            taxPayer.addExpense(expense);
        }
    }

}
