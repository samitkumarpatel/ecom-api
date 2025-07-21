package net.samitkumar.ecom;

import org.springframework.boot.SpringApplication;

public class TestEcomApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(EcomApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
