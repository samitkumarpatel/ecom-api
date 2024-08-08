package net.samitkumar.ecomdb;

import org.springframework.boot.SpringApplication;

public class TestEcomDbApplication {

	public static void main(String[] args) {
		SpringApplication.from(EcomDbApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
