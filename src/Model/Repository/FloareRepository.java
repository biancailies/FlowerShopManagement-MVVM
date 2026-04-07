package Model.Repository;

import Model.Floare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FloareRepository {

    Repository repository;

    public FloareRepository() {
        repository = new Repository();
    }

    public void addFloare(Floare floare) {
        repository.connect();
        Connection connection = repository.getConnection();

        String query = "INSERT INTO floare(denumire, imagine) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, floare.getDenumire());
            statement.setString(2, floare.getImagine());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
    }

    public void updateFloare(Floare floare) {
        repository.connect();
        Connection connection = repository.getConnection();

        String query = "UPDATE floare SET denumire = ?, imagine = ? WHERE idFloare = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, floare.getDenumire());
            statement.setString(2, floare.getImagine());
            statement.setInt(3, floare.getIdFloare());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
    }

    public void deleteFloare(int idFloare) {
        repository.connect();
        Connection connection = repository.getConnection();

        String query = "DELETE FROM floare WHERE idFloare = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFloare);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
    }

    public List<Floare> getFlori() {
        repository.connect();
        Connection connection = repository.getConnection();

        List<Floare> flori = new ArrayList<>();
        String query = "SELECT * FROM floare";

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Floare floare = new Floare();
                floare.setIdFloare(resultSet.getInt("idFloare"));
                floare.setDenumire(resultSet.getString("denumire"));
                floare.setImagine(resultSet.getString("imagine"));
                flori.add(floare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return flori;
    }

    public List<Floare> getFloriSortedByDenumire() {
        repository.connect();
        Connection connection = repository.getConnection();

        List<Floare> flori = new ArrayList<>();
        String query = "SELECT * FROM floare ORDER BY denumire ASC";

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Floare floare = new Floare();
                floare.setIdFloare(resultSet.getInt("idFloare"));
                floare.setDenumire(resultSet.getString("denumire"));
                floare.setImagine(resultSet.getString("imagine"));
                flori.add(floare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return flori;
    }

    public Floare searchFloare(String denumire) {
        repository.connect();
        Connection connection = repository.getConnection();

        Floare floare = null;
        String query = "SELECT * FROM floare WHERE LOWER(denumire) = LOWER(?) LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, denumire);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    floare = new Floare();
                    floare.setIdFloare(resultSet.getInt("idFloare"));
                    floare.setDenumire(resultSet.getString("denumire"));
                    floare.setImagine(resultSet.getString("imagine"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return floare;
    }
}