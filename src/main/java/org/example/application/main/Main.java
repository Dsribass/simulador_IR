package org.example.application.main;

import org.example.application.repository.inmemory.InMemoryExpensesDAO;
import org.example.application.repository.inmemory.InMemoryTaxPayerDAO;
import org.example.application.repository.sqlite.DatabaseBuilder;
import org.example.application.repository.sqlite.SqliteExpensesDAO;
import org.example.application.repository.sqlite.SqliteTaxPayerDAO;
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

public class Main {
    private static final TaxPayerDAO taxPayerDAO = new SqliteTaxPayerDAO();
    private static final ExpensesDAO expensesDAO = new SqliteExpensesDAO();
    public static final TaxPayerUseCases taxPayerUseCases = new TaxPayerUseCases(taxPayerDAO);
    public static final ExpensesUseCases expensesUseCases = new ExpensesUseCases(expensesDAO,taxPayerDAO);

    public static final SimpleDeclarationUseCase simpleDeclarationUseCase = new SimpleDeclarationUseCase(taxPayerDAO);
    public static final FullDeclarationUseCase fullDeclarationUseCase = new FullDeclarationUseCase(taxPayerDAO);

    public static void main(String[] args) {
        buildDatabaseIfMissing();
        App.main(args);
    }

    private static void buildDatabaseIfMissing(){
        DatabaseBuilder db = new DatabaseBuilder();
    }
}
