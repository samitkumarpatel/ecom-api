package net.samitkumar.ecom;

import org.springframework.boot.SpringApplication;

public class TestEcomApplication {

	public static void main(String[] args) {
		SpringApplication.from(EcomApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
