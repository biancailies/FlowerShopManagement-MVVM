package Model.Repository;

import Model.Stoc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StocRepository {

    Repository repository;

    public StocRepository() {
        repository = new Repository();
    }

    public void addStoc(Stoc stoc) {
        repository.connect();
        Connection connection = repository.getConnection();

        String query = "INSERT INTO stoc(idFlorarie, idFloare, stoc, culoare) VALUES(?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stoc.getIdFlorarie());
            statement.setInt(2, stoc.getIdFloare());
            statement.setInt(3, stoc.getStoc());
            statement.setString(4, stoc.getCuloare());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
    }

    public void deleteStoc(int idStoc) {
        repository.connect();
        Connection connection = repository.getConnection();

        String query = "DELETE FROM stoc WHERE idStoc = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idStoc);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
    }

    public void updateStoc(Stoc stoc) {
        repository.connect();
        Connection connection = repository.getConnection();

        String query = "UPDATE stoc SET idFlorarie = ?, idFloare = ?, stoc = ?, culoare = ? WHERE idStoc = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stoc.getIdFlorarie());
            statement.setInt(2, stoc.getIdFloare());
            statement.setInt(3, stoc.getStoc());
            statement.setString(4, stoc.getCuloare());
            statement.setInt(5, stoc.getIdStoc());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
    }

    public List<Stoc> getStocuri() {
        repository.connect();
        Connection connection = repository.getConnection();

        List<Stoc> stocuri = new ArrayList<>();
        String query = "SELECT * FROM stoc";

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Stoc stoc = mapResultSetToStoc(resultSet);
                stocuri.add(stoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return stocuri;
    }

    public List<Stoc> getStocFlori(int idFlorarie) {
        repository.connect();
        Connection connection = repository.getConnection();

        List<Stoc> stocuri = new ArrayList<>();
        String query = "SELECT * FROM stoc WHERE idFlorarie = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFlorarie);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stocuri.add(mapResultSetToStoc(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return stocuri;
    }

    public List<Stoc> getStocFlorari(int idFloare) {
        repository.connect();
        Connection connection = repository.getConnection();

        List<Stoc> stocuri = new ArrayList<>();
        String query = "SELECT * FROM stoc WHERE idFloare = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFloare);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stocuri.add(mapResultSetToStoc(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return stocuri;
    }

    public List<Stoc> getStocDisponibilDinFlorarie(int idFlorarie) {
        repository.connect();
        Connection connection = repository.getConnection();

        List<Stoc> stocuri = new ArrayList<>();
        String query = "SELECT * FROM stoc WHERE idFlorarie = ? AND stoc > 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFlorarie);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stocuri.add(mapResultSetToStoc(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return stocuri;
    }

    public List<Stoc> getStocDupaFlorarieSiCuloare(int idFlorarie, String culoare) {
        repository.connect();
        Connection connection = repository.getConnection();

        List<Stoc> stocuri = new ArrayList<>();
        String query = "SELECT * FROM stoc WHERE idFlorarie = ? AND LOWER(culoare) = LOWER(?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFlorarie);
            statement.setString(2, culoare);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stocuri.add(mapResultSetToStoc(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return stocuri;
    }

    private Stoc mapResultSetToStoc(ResultSet resultSet) throws SQLException {
        Stoc stoc = new Stoc();
        stoc.setIdStoc(resultSet.getInt("idStoc"));
        stoc.setIdFlorarie(resultSet.getInt("idFlorarie"));
        stoc.setIdFloare(resultSet.getInt("idFloare"));
        stoc.setStoc(resultSet.getInt("stoc"));
        stoc.setCuloare(resultSet.getString("culoare"));
        return stoc;
    }

    public Stoc findByFlorarieFloareCuloare(int idFlorarie, int idFloare, String culoare) {
        repository.connect();
        Connection connection = repository.getConnection();

        Stoc stoc = null;
        String query = "SELECT * FROM stoc WHERE idFlorarie = ? AND idFloare = ? AND LOWER(culoare) = LOWER(?) LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFlorarie);
            statement.setInt(2, idFloare);
            statement.setString(3, culoare);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    stoc = mapResultSetToStoc(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        repository.disconnect();
        return stoc;
    }
}