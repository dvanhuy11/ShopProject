package com.demo.Shop.admin.user;
import com.Shop.common.entity.Role;
import com.Shop.common.entity.User;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepos;
	
	@Autowired
	private RoleRepository roleRepos;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll(){
		return (List<User>) userRepos.findAll();
	}
	public List<Role> listRoles(){
		return (List<Role>) roleRepos.findAll();
	}
	
	public void save(User user) {
boolean isUpdatingUser = (user.getId() != null);
		
		if(isUpdatingUser)
		{
			User existingUser = userRepos.findById(user.getId()).get();
			if(user.getPassword().isEmpty())
			{
				user.setPassword(existingUser.getPassword());
			}
			else {
				encodedPassWord(user);
			}
		}else {
			encodedPassWord(user);
		}
		userRepos.save(user);
	}
	
	private void encodedPassWord(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepos.getUserByEmail(email);
		
		if(userByEmail == null) return true;
		
		boolean createNew = (id == null);
		if(createNew)
		{
			if(userByEmail != null ) return false;
		}
		else {
			if(userByEmail.getId() != id) return false;
		}
			
		return true;
	}
	public User get(int id) throws UserNotFoundException{
		try {
			return userRepos.findById(id).get();
		}
		catch(NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID" +id);
		}
	}
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepos.countById(id);
		if(countById == null || countById==0) {
			throw new UserNotFoundException("Could not find any user with ID" +id);
		}
		userRepos.deleteById(id);
	}
}
