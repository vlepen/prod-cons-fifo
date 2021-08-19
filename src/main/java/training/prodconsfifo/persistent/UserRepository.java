package training.prodconsfifo.persistent;

import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    private static final String TABLE = "SUSERS";
    private static final String ID = "user_id";
    private static final String GUID = "user_guid";
    private static final String NAME = "user_name";

    public void deleteAll() {
        DBTemplate.executeUpdateStatement("DELETE FROM " + TABLE);
    }

    public User create(Long id, String guid, String name) {
        User user = new User(id, guid, name);
        DBTemplate.executeUpdateStatement(
            "INSERT INTO " + TABLE + "(" + ID + ", " + GUID + ", " + NAME + ") VALUES (?, ?, ?)",
            (preparedStatement -> {
                try {
                    preparedStatement.setLong(1, id);
                    preparedStatement.setString(2, guid);
                    preparedStatement.setString(3, name);
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to create new user " + user, e);
                }
            })
        );

        return user;
    }

    public List<User> findAll() {
        return DBTemplate.executeQueryStatement(
            "SELECT " + ID + ", " + GUID + ", " + NAME + " FROM " + TABLE,
            (rs -> {
                try {
                    return new User(rs.getLong(1), rs.getString(2), rs.getString(3));
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to retrieve users " + e);
                }
            })
        );
    }
}
