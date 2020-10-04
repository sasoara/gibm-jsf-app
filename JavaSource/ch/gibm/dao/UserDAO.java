package ch.gibm.dao;

import ch.gibm.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserDAO extends GenericDAO<User> {

    private static final long serialVersionUID = 1L;

    public UserDAO() {
        super(User.class);
    }

    public void delete(User user) {
        super.delete(user.getId());
    }

    public User findUser(String name, String password) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userName", name);
        parameters.put("userPassword", password);

        return super.findOneResult(User.FIND_USER, parameters);
    }

}
