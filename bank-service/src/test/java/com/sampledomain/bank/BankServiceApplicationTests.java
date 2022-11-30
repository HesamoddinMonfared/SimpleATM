package com.sampledomain.bank;

import com.sampledomain.bank.controller.UserEntityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BankServiceApplicationTests {

	@Autowired
	private UserEntityController userEntityController;


	@Test
	void contextLoads() {
		assertThat(userEntityController).isNotNull();
	}

}
