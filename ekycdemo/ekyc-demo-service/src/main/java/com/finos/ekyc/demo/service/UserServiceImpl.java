package com.finos.ekyc.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finos.ekyc.demo.model.User;
import com.finos.ekyc.demo.model.UserDto;
import com.finos.ekyc.demo.repository.UserDao;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<UserDto> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list.stream().map(UserDto::new).collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		userDao.deleteById(id);
	}

	@Override
	public UserDto findOne(String username) {
		return new UserDto(userDao.findByUsername(username));
	}

	@Override
	public UserDto findById(Long id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.map(UserDto::new).orElse(new UserDto());
	}

    @Override
    public UserDto update(UserDto userDto) {
        Optional<User> optionalUser = userDao.findById(userDto.getId());
        if(optionalUser.isPresent()) {
            BeanUtils.copyProperties(userDto, optionalUser.get(), "password");
            userDao.save(optionalUser.get());
        }
        return userDto;
    }

    @Override
    public UserDto save(UserDto user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUsername());
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
	    newUser.setRoles(user.getRoles());
		return new UserDto(userDao.save(newUser));
    }
}
