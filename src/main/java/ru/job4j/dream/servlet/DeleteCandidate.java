package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class DeleteCandidate extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        PsqlStore.instOf().deleteCandidate(Integer.parseInt(req.getParameter("id")));
        try {
            Files.delete(Path.of("C:\\images\\" + req.getParameter("id") + "." + "jpg"));
        } catch (NoSuchFileException noSuchFileException) {
            LOG.debug("Image doesnt exist in the directory", noSuchFileException);
            noSuchFileException.printStackTrace();
        }
        doGet(req, resp);
    }
}
