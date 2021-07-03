package org.example.application.repository.sqlite;

import org.example.domain.entities.taxpayer.TaxPayer;
import org.example.domain.usecases.taxpayers.TaxPayerDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteTaxPayerDAO implements TaxPayerDAO {

    private TaxPayer getTaxPayerFromResultSet(ResultSet resultSet) throws SQLException {
        return new TaxPayer(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getDouble("annual_taxable_income"),
                resultSet.getDouble("tax_withholding")
        );
    }

    @Override
    public Optional<TaxPayer> findOne(String id) {
        String sql = "SELECT * FROM tax_payer where id = ?;";
        TaxPayer taxPayer = null;

        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setString(1,id);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                taxPayer = getTaxPayerFromResultSet(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(taxPayer);
    }

    @Override
    public List<TaxPayer> fetchAll() {
        String sql = "SELECT * FROM tax_payer;";
        List<TaxPayer> taxPayers = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                TaxPayer taxPayerResult = getTaxPayerFromResultSet(resultSet);
                taxPayers.add(taxPayerResult);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return taxPayers;
    }

    @Override
    public String insert(TaxPayer taxPayer) {
        String sql = "INSERT INTO tax_payer VALUES(?,?,?,?);";
        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setString(1,taxPayer.getId());
            stmt.setString(2,taxPayer.getName());
            stmt.setDouble(3,taxPayer.getAnnualTaxableIncome());
            stmt.setDouble(4,taxPayer.getTaxWithholding());
            stmt.execute();
            return taxPayer.getId();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(String id, TaxPayer taxPayer) {
        String sql = "UPDATE tax_payer SET name= ?, annual_taxable_income=?, tax_withholding=? WHERE id = ?;";

        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setString(1,taxPayer.getName());
            stmt.setDouble(2,taxPayer.getAnnualTaxableIncome());
            stmt.setDouble(3,taxPayer.getTaxWithholding());
            stmt.setString(4,id);

            stmt.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(TaxPayer taxPayer) {
        return deleteByKey(taxPayer.getId());
    }

    @Override
    public boolean deleteByKey(String id) {
        String sql = "DELETE FROM tax_payer WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.preparedStatement(sql)) {
            stmt.setString(1, id);
            stmt.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
