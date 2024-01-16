package org.example.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import java.util.Objects;

public class AppConfig extends Configuration {
    @Valid
    private DataSourceFactory database;

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        if (Objects.isNull(database)) {
            return new DataSourceFactory();
        } else {
            return database;
        }
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
