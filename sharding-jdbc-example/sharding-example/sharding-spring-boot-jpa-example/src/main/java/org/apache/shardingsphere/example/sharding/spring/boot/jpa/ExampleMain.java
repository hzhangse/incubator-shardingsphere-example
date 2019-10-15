/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.example.sharding.spring.boot.jpa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.shardingsphere.example.core.api.ExampleExecuteTemplate;
import org.apache.shardingsphere.example.core.api.entity.Order;
import org.apache.shardingsphere.example.core.api.entity.OrderItem;
import org.apache.shardingsphere.example.core.api.service.ExampleService;
import org.apache.shardingsphere.example.core.jpa.repository.OrderItemRepositoryImpl;
import org.apache.shardingsphere.example.core.jpa.repository.OrderRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.apache.shardingsphere.example.core.jpa")
@EntityScan(basePackages = "org.apache.shardingsphere.example.core.jpa.entity")
@SpringBootApplication(exclude = JtaAutoConfiguration.class)
public class ExampleMain {
    
    public static void main(final String[] args) throws SQLException {
    	ConfigurableApplicationContext applicationContext = SpringApplication.run(ExampleMain.class, args);
    	ExampleExecuteTemplate.run(applicationContext.getBean(ExampleService.class));
       /* try (ConfigurableApplicationContext applicationContext = SpringApplication.run(ExampleMain.class, args)) {
            ExampleExecuteTemplate.run(applicationContext.getBean(ExampleService.class));
        }*/
    }

 
}
