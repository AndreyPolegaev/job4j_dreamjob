package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface Store {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    List<Candidate> currentCandidates();

    List<Post> currentPost();

    void save(Post post);

    void save(Candidate candidate);

    Post findById(int id);

    Candidate create(Candidate candidate);

    Candidate findCandidateById(int id);

    void deleteCandidate(int id);

    void save(User user);

    User findUserByEmail(String email);

    BasicDataSource getPool();

    List<City> findAllCities();
}
