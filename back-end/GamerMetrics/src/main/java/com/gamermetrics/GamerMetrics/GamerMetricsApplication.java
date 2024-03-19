package com.gamermetrics.GamerMetrics;

import com.gamermetrics.GamerMetrics.config.StartUpSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GamerMetricsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamerMetricsApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(StartUpSeeder seeder) {
		return args -> seeder.run();
	}

}
