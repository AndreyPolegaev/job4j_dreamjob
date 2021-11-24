package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @author Andrey Polegaev
 * @version 1.0
 * Methods for data base - dream job. CRUD operations.
 */

public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    /**
     * new FileReader("db.properties") - работа с Postgres
     * new FileReader("./src/test/resources/db.properties") - работа с h2 для тестирования
     */
    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    /**
     * Вернуть pool для класса тестирования
     */
    public BasicDataSource getPool() {
        return pool;
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getTimestamp("time").toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return posts;
    }

    /**
     * извлечь список городов (города добавляются при создании схемы таблицы)
     */
    @Override
    public List<City> findAllCities() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM cities")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(new City(
                            it.getInt("id"),
                            it.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return cities;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return candidates;
    }

    /**
     * @return List<Candidate> кандидаты, добавленные за последние сутки
     */
    @Override
    public List<Candidate> currentCandidates() {
        List<Candidate> currentCandidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
             "SELECT * FROM candidates where time BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW()")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    currentCandidates.add(new Candidate(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return currentCandidates;
    }

    /**
     * @return List<Post> вакансии, добавленные за последние сутки
     */
    @Override
    public List<Post> currentPost() {
        List<Post> currentPost = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
              "SELECT * FROM post where time BETWEEN NOW() - INTERVAL '24 HOURS' AND NOW()")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    currentPost.add(new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getTimestamp("time").toLocalDateTime()));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return currentPost;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    @Override
    public Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidates (name, time, city_fk) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setTimestamp(2, candidate.getCreated());
            ps.setInt(3, candidate.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE candidates SET name = (?) WHERE id = (?)")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
          PreparedStatement ps = cn.prepareStatement("INSERT INTO post (name, time) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setTimestamp(2, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return post;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = (?) WHERE id = (?)")
        ) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates where id = (?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = new Candidate(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return rsl;
    }

    @Override
    public void deleteCandidate(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM candidates where id = (?)")
        ) {
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
    }

    @Override
    public Post findById(int id) {
        Post rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post where id = (?)")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = new Post(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getTimestamp("time").toLocalDateTime()
                            );
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore", e);
        }
        return rsl;
    }

    @Override
    public void save(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users (name, email, password) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore save User method", e);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("SELECT *from users WHERE email = (?)")
        ) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception in PsqlStore findUserByNEmail method", e);
        }
        return null;
    }
}


