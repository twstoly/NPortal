import Exceptions.ApiExceptions;
import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oDepartmentNewsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import models.Department;
import models.DepartmentNews;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oDepartmentNewsDao departmentNewsDao;
        Sql2oNewsDao newsDao;
        Sql2oUserDao userDao;
        Sql2oDepartmentDao departmentDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/call_api";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "1111111");


        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);


        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        get("/departments", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(departmentDao.getAll());
        });

        get("/departments/:id", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null ){
                throw new ApiExceptions(404, String.format("No department with id: %s exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        post("/departments/:id/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null ){
                throw new ApiExceptions(404, String.format("No department with id: %s exists", req.params("id")));
            } else {
                user.setDepartmentId(departmentId);
                userDao.add(user);
                res.status(201);
                return gson.toJson(user);
            }
        });

        get("/users", "application/json", (req, res) -> {
            if (userDao.getAll().size() == 0) {
                return "{\"message\":\"I'm sorry, but no users are here yet.\"}";
            } else{
                return gson.toJson(userDao.getAll());
            }
        });

        get("/users/:id", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = userDao.findById(userId);
            Department department = departmentDao.findById(userToFind.getDepartmentId());
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("user", userToFind);
            jsonMap.put("department", department);
            return gson.toJson(jsonMap);
        });

        post("/news/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        get("/news", "application/json", (req, res) -> {
            if (newsDao.getAll().size() == 0) {
                return "{\"message\":\"I'm sorry, but no news are currently listed.\"}";
            } else{
                return gson.toJson(newsDao.getAll());
            }
        });

        post("/departments/:id/news/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null ){
                throw new ApiExceptions(404, String.format("No department with id: %s exists", req.params("id")));
            } else {
                DepartmentNews news = gson.fromJson(req.body(), DepartmentNews.class);
                news.setDepartmentId(departmentId);
                departmentNewsDao.add(news);
                res.status(201);
                return gson.toJson(news);
            }
        });

        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            List<DepartmentNews> news = departmentDao.getAllNewsByDepartment(departmentToFind.getId());
            if (departmentToFind == null ){
                throw new ApiExceptions(404, String.format("No department with id: %s exists", req.params("id")));
            } else if(news.size() == 0) {
                return "{\"message\":\"I'am sorry there are no news in this department currently.\"}";
            } else {
                return gson.toJson(news);
            }
        });

        //this should allow us see users of a given department and link to their news(that department news)
        get("/departments/:id/users/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null ){
                throw new ApiExceptions(404, String.format("No department with id: %s exists", req.params("id")));
            } else {
                Map<String, Object> model = new HashMap<>();
                List<User> users = departmentDao.getAllUsersByDepartment(departmentToFind.getId());
                String news = String.format("/departments/%s/news", req.params("id"));
                if (users.size() == 0) {
                    String message = "I'm sorry, there are no users currently.";
                    model.put("department", departmentToFind);
                    model.put("message", message);
                    model.put("departmentNews", news);
                    return gson.toJson(model);
                }else {
                    model.put("department", departmentToFind);
                    model.put("departmentUsers", users);
                    model.put("departmentNews", news);
                    return gson.toJson(model);
                }
            }
        });

        exception(ApiExceptions.class, (exception, req, res) -> {
            ApiExceptions err = exception;
            Map<String, Object> model = new HashMap<>();
            model.put("status", err.getStatusCode());
            model.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(model));
        });


        after((req, res) ->{
            res.type("application/json");
        });
    }
}




























