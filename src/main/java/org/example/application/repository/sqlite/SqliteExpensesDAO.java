package org.example.application.repository.sqlite;

import org.example.domain.entities.expenses.Expense;
import org.example.domain.entities.expenses.ExpensesType;
import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.expenses.ExpensesDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.application.main.Main.taxPayerUseCases;

public class SqliteExpensesDAO implements ExpensesDAO {

    private Expense getExpenseFromResultSet(ResultSet resultSet) throws SQLException {
        final TaxPayer taxPayer = taxPayerUseCases.findOne(resultSet.getString("id_taxpayer")).get();
        return new Expense(
                resultSet.getInt("id"),
                ExpensesType.toEnum(resultSet.getString("type")),
                resultSet.getString("name"),
                resultSet.getDouble("value_spent"),
                taxPayer
        );
    }

    @Override
    public List<Expense> fetchAllByTaxPayerId(String idTaxPayer) {
        String sql = "SELECT * FROM expense WHERE id_taxpayer = ?";
        List<Expense> expenses = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setString(1,idTaxPayer);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Expense expenseResult = getExpenseFromResultSet(resultSet);
                expenses.add(expenseResult);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public Optional<Expense> findOne(Integer id) {
        if(id == null){
            return Optional.empty();
        }
        String sql = "SELECT * FROM expense WHERE id = ?";
        Expense expense = null;

        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setInt(1,id);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                expense = getExpenseFromResultSet(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(expense);
    }

    @Override
    public List<Expense> fetchAll() {
        String sql = "SELECT * FROM expense";
        List<Expense> expenses = new ArrayList<>();

        try (Statement stmt = ConnectionFactory.statement()) {
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                Expense expenseResult = getExpenseFromResultSet(resultSet);
                expenses.add(expenseResult);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public Integer insert(Expense expense) {
        String sql = "INSERT INTO expense(name,type,value_spent,id_taxpayer) VALUES(?,?,?,?);";
        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setString(1,expense.getName());
            stmt.setString(2,expense.getType().toString());
            stmt.setDouble(3,expense.getValueSpent());
            stmt.setString(4,expense.getTaxPayer().getId());
            stmt.execute();
            ResultSet resultSet = stmt.getGeneratedKeys();
            return resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Integer id, Expense expense) {
        return false;
    }

    @Override
    public boolean delete(Expense expense) {
        return deleteByKey(expense.getId());
    }

    @Override
    public boolean deleteByKey(Integer id) {
        String sql = "DELETE FROM expense WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
