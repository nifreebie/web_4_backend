package web.web4_backend.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import web.web4_backend.model.Point;
import web.web4_backend.model.User;
import web.web4_backend.util.AreaChecker;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class PointService {
    @PersistenceContext(unitName = "web4PU")
    private EntityManager em;

    public Point addPoint(float x, float y, float r, Long userId) {
        long startTime = System.nanoTime();

        User user = em.find(User.class, userId); // Используем текущую сессию для загрузки
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Point point = new Point(x, y, r);
        point.setUser(user);
        point.setHit(AreaChecker.isHit(x, y, r));
        point.setCreatedAt(LocalDateTime.now());
        point.setExecutionTime((System.nanoTime() - startTime) / 1000000);

        em.persist(point);
        return point;
    }

    public List<Point> getUserPoints(Long userId) {
        return em.createQuery(
                        "SELECT DISTINCT p FROM Point p LEFT JOIN FETCH p.user WHERE p.user.id = :userId ORDER BY p.createdAt DESC",
                        Point.class
                )
                .setParameter("userId", userId)
                .getResultList();
    }

    public void clearUserPoints(Long userId) {
        em.createQuery("DELETE FROM Point p WHERE p.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
