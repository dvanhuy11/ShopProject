package com.demo.Shop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String newPassword = "hello12345";
		String encodedPassword = passwordEncoder.encode(newPassword);
		System.out.println(encodedPassword);
		boolean matches =passwordEncoder.matches(newPassword, encodedPassword);
		assertThat(matches).isTrue();
	}
}
