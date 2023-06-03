package com.demo.Shop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.Shop.common.entity.Role;
import com.Shop.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repos;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userBach = new User("truongquangbach2002@gmail.com","hello","Bach","Truong");
		userBach.addRole(roleAdmin);
		
		User savedUser =repos.save(userBach);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User user1 = new User("abc@gmail.com","abc","Huy","Tran");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		user1.addRole(roleEditor);
		user1.addRole(roleAssistant);
		User savedUser = repos.save(user1);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUser() {
		Iterable<User> listUsers = repos.findAll();
		listUsers.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userBach = repos.findById(1).get();
		System.out.println(userBach);
		assertThat(userBach).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userBach = repos.findById(1).get();
		userBach.setEnabled(true);
		userBach.setEmail("truongquangbach@gmail.com");
		repos.save(userBach);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User user1 =repos.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSaleperson = new Role(2);
		
		user1.getRoles().remove(roleEditor);
		user1.addRole(roleSaleperson);
		repos.save(user1);
	}
	@Test
	public void testDeleteUser() {
		int userId =2;
		repos.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email ="abc@gmail.com";
		User user = repos.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
}
