package ru.job4j.dream.servlet;

import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * загрузка из БД кандидатов и вакансий за последние сутки
 */

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        req.setAttribute("currentCandidates", PsqlStore.instOf().currentCandidates());
        req.setAttribute("currentPost", PsqlStore.instOf().currentPost());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
