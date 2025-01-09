package web.web4_backend.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import web.web4_backend.exceptions.DuplicateUsernameException;
import web.web4_backend.model.User;
import web.web4_backend.security.PasswordUtil;

import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "web4PU")
    private EntityManager em;

    public User register(String username, String password) {
        if (findByUsername(username) != null) {
            throw new DuplicateUsernameException("Duplicate username");
        }

        User user = new User(username, PasswordUtil.hash(password));
        em.persist(user);
        return user;
    }

    public User authenticate(String username, String password) {
        User user = findByUsername(username);
        if (user == null || !PasswordUtil.verify(password, user.getPassword())) {
            throw new DuplicateUsernameException("Duplicate username");
        }
        return user;
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

}
