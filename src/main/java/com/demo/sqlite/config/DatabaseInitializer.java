package com.demo.sqlite.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

@Component
@Slf4j
public class DatabaseInitializer {
   private final DataSource dataSource;

   public DatabaseInitializer(@Autowired DataSource dataSource) {
      this.dataSource = dataSource;
   }
}
