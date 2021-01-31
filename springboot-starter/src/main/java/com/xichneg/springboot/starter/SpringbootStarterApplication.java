package com.xichneg.springboot.starter;

import com.xichneg.springboot.starter.common.DateConfigUsingImport;
import com.xichneg.springboot.starter.common.EnableImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DateConfigUsingImport.class)
@EnableImport
public class SpringbootStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootStarterApplication.class, args);
	}

}
