package ch.gibm.facade;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.UserDAO;
import ch.gibm.entity.User;

import java.io.Serializable;

public class UserFacade implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAO();

    public User isValidLogin(String userName, String password) {
        EntityManagerHelper.beginTransaction();
        User validUser = userDAO.findUser(userName, password);
        EntityManagerHelper.commitAndCloseTransaction();
        return validUser;
    }

    public void updateUser(User user) {
        EntityManagerHelper.beginTransaction();
        User persistedUser = userDAO.find(user.getId());
        persistedUser.setName(user.getName());
        persistedUser.setPassword(user.getPassword());
        userDAO.update(user);
        EntityManagerHelper.commitAndCloseTransaction();
    }
}
