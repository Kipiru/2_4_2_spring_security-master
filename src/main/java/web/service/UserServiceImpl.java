package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.getAll();

    }


    @Override
    @Transactional
    public void create(User user, String[] roles) {
        user.setRoles(roleService.getRoleSet(roles));
        userDao.create(user);
    }


    @Override
    @Transactional(readOnly = true)
    public User readById(int id) {
        return userDao.readById(id);
    }


    @Override
    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User userFromDb = userDao.readById(user.getId());
        userFromDb.setName(user.getName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setAge(user.getAge());
        userDao.updateUser(userFromDb);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

}
