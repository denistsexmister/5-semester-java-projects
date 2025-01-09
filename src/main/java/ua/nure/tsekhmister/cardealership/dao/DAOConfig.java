package ua.nure.tsekhmister.cardealership.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
public class DAOConfig {
    @Bean
    public DAOFactory getDAOFactory(@Value("${database.type}") String database) throws SQLException {
        if (database.equalsIgnoreCase("mysql")) {
            return DAOFactory.getDAOFactory(
                    "ua.nure.tsekhmister.cardealership.dao.mysql.MySqlDAOFactory");
        } else {
            return DAOFactory.getDAOFactory(
                    "ua.nure.tsekhmister.cardealership.dao.collection.ListDAOFactory");
        }
    }

}
