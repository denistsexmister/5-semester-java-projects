package ua.nure.tsekhmister.cardealership.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DAOConfig {
    @Bean
    public DAOFactory getDAOFactory() throws SQLException, IOException {
        Properties prop = new Properties();
        try (FileReader fr = new FileReader("src/main/resources/application.properties")) {
            prop.load(fr);
        }

        if (prop.getProperty("database.type").equalsIgnoreCase("mysql")) {
            return DAOFactory.getDAOFactory(
                    "ua.nure.cpp.tsekhmister.practice6.dao.mysql.MySqlDAOFactory");
        } else {
            return DAOFactory.getDAOFactory(
                    "ua.nure.cpp.tsekhmister.practice6.dao.collection.ListDAOFactory");
        }
    }

}
