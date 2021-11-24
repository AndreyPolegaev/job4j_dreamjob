package ru.job4j.dream.servlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Из БД достаем список городов запиываем в List.
 * При обращении в метод GET через AJAX сериализуем лист и отправляем ответ потоком вывода.
 */
public class CityServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    private final List<City> cities = PsqlStore.instOf().findAllCities();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        resp.setContentType("json");
        PrintWriter output = resp.getWriter();
        String json = GSON.toJson(cities);
        resp.setCharacterEncoding("UTF-8");
        output.write(json);
        output.flush();
        output.close();
    }
}
