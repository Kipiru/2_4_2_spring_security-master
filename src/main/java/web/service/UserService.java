package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAll();

    void create(User user, String[] roles);

    User readById(int id);

    void delete(User user);

    void updateUser(User user);

    User getUserByName(String name);

}
