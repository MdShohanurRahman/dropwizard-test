package org.example;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.configs.AppConfig;
import org.example.todo.TodoDAO;
import org.example.todo.TodoEntity;
import org.example.todo.TodoResource;
import org.h2.server.web.WebServlet;

public class App extends Application<AppConfig> {
    private final HibernateBundle<AppConfig> hibernateBundle =
            new HibernateBundle<AppConfig>(TodoEntity.class) {
                @Override
                public PooledDataSourceFactory getDataSourceFactory(AppConfig configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(AppConfig appConfiguration, Environment environment) {
        environment.servlets().addServlet("H2Console", new WebServlet()).addMapping("/admin/h2-console/*");

        // Configure and register resources, health checks, etc.
        final TodoDAO todoDao = new TodoDAO(hibernateBundle.getSessionFactory());
        environment.jersey().register(new TodoResource(todoDao));
    }

    public static void main(String[] args) throws Exception {
        new App().run("server", "src/main/resources/config.yml");
    }
}
