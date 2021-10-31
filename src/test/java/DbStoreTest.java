import org.junit.After;
import org.junit.Test;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.is;

public class DbStoreTest {

    /**
     * метод выполняется после каждого теста - очистка таблиц.
     */

    @After
    public void wipeTable() throws SQLException {
        try (Connection cn = PsqlStore.instOf().getPool().getConnection();
             PreparedStatement deletePost = cn.prepareStatement("delete from post");
             PreparedStatement deleteCandidates = cn.prepareStatement("delete from candidates");
             PreparedStatement deleteUsers = cn.prepareStatement("delete from users");
             PreparedStatement u1 = cn.prepareStatement(
                     "ALTER TABLE post ALTER COLUMN id RESTART WITH 1");
             PreparedStatement u2 = cn.prepareStatement(
                     "ALTER TABLE candidates ALTER COLUMN id RESTART WITH 1");
             PreparedStatement u3 = cn.prepareStatement(
                     "ALTER TABLE users ALTER COLUMN id RESTART WITH 1")
        ) {
            deletePost.execute();
            deleteCandidates.execute();
            deleteUsers.execute();
            u1.execute();
            u2.execute();
            u3.execute();
        }
    }

    @Test
    public void whenFindUser() {
        Store store = PsqlStore.instOf();
        User u1 = new User(0, "User1", "em@gmail.com", "12345");
        store.save(u1);
        User retrievedUser = store.findUserByEmail(u1.getEmail());
        assertThat(retrievedUser, is(u1));
    }

    @Test
    public void whenSaveUserThenFindById() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job");
        store.save(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenFindPostsById() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job");
        store.save(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb, is(post));
    }

    @Test
    public void whenFindAllCandidates() {
        Store store = PsqlStore.instOf();
        Candidate c1 = new Candidate(0, "Ivan");
        store.save(c1);
        List<Candidate> candidates = (List) store.findAllCandidates();
        assertThat(candidates.get(0).getName(), is(c1.getName()));
    }

    @Test
    public void whenFindCandidateById() {
        Store store = PsqlStore.instOf();
        Candidate c1 = new Candidate(0, "Ivan");
        store.save(c1);
        assertThat(store.findCandidateById(c1.getId()), is(c1));
    }

    @Test
    public void whenDeleteCandidate() {
        Store store = PsqlStore.instOf();
        Candidate c1 = new Candidate(0, "Ivan");
        store.save(c1);
        store.deleteCandidate(c1.getId());
        assertThat(store.findCandidateById(c1.getId()), is(nullValue()));
    }

    @Test
    public void whenUpdateCandidate() {
        Store store = PsqlStore.instOf();
        Candidate c1 = new Candidate(0, "Ivan");
        store.save(c1);
        c1.setName("Petr");
        store.save(c1);
        Candidate retrievedCandidate = store.findCandidateById(c1.getId());
        assertThat(retrievedCandidate.getName(), is("Petr"));
    }

    @Test
    public void whenFindAllPosts() {
        Store store = PsqlStore.instOf();
        Post p1 = new Post(0, "Post1");
        Post p2 = new Post(0, "Post1");
        store.save(p1);
        store.save(p2);
        List<Post> posts = (List) store.findAllPosts();
        assertThat(posts.get(0), is(p1));
        assertThat(posts.get(1), is(p2));
        assertEquals(posts.get(0), p1);
    }

    @Test
    public void whenUpdatePost() {
        Store store = PsqlStore.instOf();
        Post p1 = new Post(0, "Post1");
        store.save(p1);
        p1.setName("Changed Name");
        store.save(p1);
        Post retrievedPost = store.findById(p1.getId());
        assertThat(retrievedPost.getName(), is("Changed Name"));
    }
}
