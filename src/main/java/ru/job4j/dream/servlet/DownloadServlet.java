package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

/**
 * Обход папки с фотографиями, если имя файла совпадает
 * с id кандидата то скачиваем (выводим на сайт)
 */
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        Properties cfg = new Properties();
        try (InputStream in = DownloadServlet.class.getClassLoader().getResourceAsStream(
                "photoPath.properties")) {
            cfg.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = req.getParameter("name");
        File downloadFile = null;
        for (File file : new File(cfg.getProperty("path")).listFiles()) {
            if (file.getName().startsWith(name)) {
                downloadFile = file;
                break;
            }
        }
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\""
                + downloadFile.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(downloadFile)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}

