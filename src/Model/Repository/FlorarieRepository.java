package Model.Repository;

import Model.Florarie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlorarieRepository {

    private Repository repository;

    public FlorarieRepository() {
        repository = new Repository();
    }

    public List<Florarie> getFlorarii() {
        List<Florarie> florarii = new ArrayList<Florarie>();
        repository.connect();
        Connection connection = repository.getConnection();

        ResultSet resultSet = null;
        Statement statement = null;
        String query = "SELECT * FROM florarie";

        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                int idFlorarie = resultSet.getInt("idFlorarie");
                String denumire = resultSet.getString("denumire");
                String adresa = resultSet.getString("adresa");

                florarii.add(new Florarie(idFlorarie, denumire, adresa));
            }
            resultSet.close();
            statement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        repository.disconnect();

        return florarii;
    }

    public void addFlorarie(Florarie florarie) {
        repository.connect();
        Connection connection = repository.getConnection();

        PreparedStatement statement = null;
        String query = "INSERT INTO florarie (denumire, adresa) VALUES (?, ?)";
        try{
            statement = connection.prepareStatement(query);

            statement.setString(1, florarie.getDenumire());
            statement.setString(2, florarie.getAdresa());

            statement.executeUpdate();

            statement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        repository.disconnect();
    }

    public void deleteFlorarie(int idFlorarie) {
        repository.connect();
        Connection connection = repository.getConnection();

        PreparedStatement statement = null;
        String query = "DELETE FROM florarie WHERE idFlorarie = ?";

        try{
            statement = connection.prepareStatement(query);
            statement.setInt(1, idFlorarie);
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        repository.disconnect();
    }

    public void updateFlorarie(Florarie florarie) {
        repository.connect();
        Connection connection = repository.getConnection();

        PreparedStatement statement = null;
        String query = "UPDATE florarie SET denumire=?, adresa=? WHERE idFlorarie=?";

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1, florarie.getDenumire());
            statement.setString(2, florarie.getAdresa());
            statement.setInt(3, florarie.getIdFlorarie());
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        repository.disconnect();
    }
}
