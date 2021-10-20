package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job"));
        store.save(new Post(1, "Python Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        Post postFindById = store.findById(1);
        System.out.println(postFindById);
        store.save(new Candidate(0, "Java developer"));
        for (Candidate temp : store.findAllCandidates()) {
            System.out.println(temp);
        }
        Candidate candidate = store.findCandidateById(1);
        System.out.println(candidate);
        store.deleteCandidate(1);
        System.out.println(store.findAllCandidates());
    }
}
