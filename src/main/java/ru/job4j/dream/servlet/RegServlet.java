package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * При регистрации сначала сделать проверку что такого пользователя нет в базе  (по Email)
 * Если такого нет внести запись в БД, сделать сессию и отправить на index.html
 * Если есть то отправить на login.jsp
 */

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (PsqlStore.instOf().findUserByEmail(email) == null) {
            User user = new User(0, name, email, password);
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            PsqlStore.instOf().save(user);
        } else {
            req.setAttribute("error", "Пользователь с таким именем уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
        doGet(req, resp);
    }
}
