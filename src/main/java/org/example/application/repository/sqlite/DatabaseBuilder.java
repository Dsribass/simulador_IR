package org.example.application.repository.sqlite;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {
    public DatabaseBuilder() {
        if(isDatabaseNotCreated())
            buildTables();
    }

    private boolean isDatabaseNotCreated() {
        return !Files.exists(Paths.get("database.db"));
    }

    private void buildTables() {
        try(Statement statement = ConnectionFactory.statement()){
            statement.addBatch(createTableTaxPayer());
            statement.addBatch(createTableExpense());

            statement.executeBatch();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String createTableTaxPayer() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE tax_payer (\n");
        sb.append("id TEXT NOT NULL UNIQUE,\n");
        sb.append("name VARCHAR(50),\n");
        sb.append("annual_taxable_income DECIMAL(19,4) NOT NULL,\n");
        sb.append("tax_withholding DECIMAL(19,4) NOT NULL\n");
        sb.append(");\n");

        System.out.println(sb.toString());
        return sb.toString();
    }
    public String createTableExpense(){
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE expense (\n");
        sb.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n");
        sb.append("name VARCHAR(50) NOT NULL,\n");
        sb.append("type VARCHAR(40) NOT NULL,\n");
        sb.append("value_spent DECIMAL(19,4) NOT NULL,\n");
        sb.append("id_taxpayer TEXT NOT NULL,\n");
        sb.append("FOREIGN KEY(id_taxpayer) REFERENCES tax_payer(id)\n");
        sb.append(");\n");

        System.out.println(sb.toString());
        return sb.toString();
    }
}
