package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    final private String INSERT_NEW = "INSERT INTO utilTestDB(name, lastName, age) VALUES (?, ?, ?)";
    final private String GET_ALL = "SELECT * FROM utilTestDB";
    final private String DELETE_ID = "DELETE FROM utilTestDB WHERE id = ?";
    final private String DELETE_ALL = "TRUNCATE utilTestDB";
    final private String DELETE_TABLE = "DROP TABLE utilTestDB";
    final private String CREATE_TABLE = "CREATE TABLE utilTestDB " +
                                        "(id BIGINT not null PRIMARY KEY AUTO_INCREMENT, " +
                                        " name VARCHAR(45) not null, " +
                                        " lastName VARCHAR(45) not null, " +
                                        " age TINYINT not null)"; //+ " PRIMARY KEY ( id ))";


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(CREATE_TABLE);

        } catch(SQLSyntaxErrorException ex) {
            // Skip ex
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(DELETE_TABLE);

        } catch(SQLSyntaxErrorException ex) {
            // Skip ex
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = new Util().getConnection();
             Statement statement = connection.createStatement()) {

            //statement.execute(String.format(INSERT_NEW, name, lastName, age));
            statement.execute(String.format("INSERT INTO utilTestDB(name, lastName, age) VALUES ('%s', '%s', %d)", name, lastName, age));

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ID)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {

            ResultSet res = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();

            while (res.next()) {
                long id = res.getLong("id");
                String name = res.getString("name");
                String lastName = res.getString("lastName");
                byte age = res.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);

                list.add(user);
            }

            return list;

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    public void cleanUsersTable() {

        try (Connection connection = new Util().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
