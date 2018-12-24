package com.maxwell.oAuth2JpaMySQLjwt.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maxwell.oAuth2JpaMySQLjwt.domain.Permission;
import com.maxwell.oAuth2JpaMySQLjwt.domain.Role;
import com.maxwell.oAuth2JpaMySQLjwt.domain.AppUser;
import com.maxwell.oAuth2JpaMySQLjwt.service.UserService;

@RestController // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /user (after Application path)
public class UserController {
	
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public static String uploadDirectory = System.getProperty("user.dir")+"/userFiles";
	
	@Autowired
	public UserController(UserService userService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping(path="/{userId}") // Map ONLY GET Requests
	public  @ResponseBody AppUser getUser(@PathVariable int userId) {
		return userService.getUser(userId);
	}
	
	@PutMapping(path="/{userId}")
	public @ResponseBody AppUser editUser(@PathVariable int userId, @RequestBody AppUser user) {
		AppUser existingUser = userService.findById(userId);
        Assert.notNull(existingUser, "User not found");
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        userService.saveEdits(existingUser);
        return existingUser;
	}
	
	@DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable int userId) {
        AppUser userToDel = userService.findById(userId);
        userService.deleteUser(userToDel);
    }
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody List<AppUser> getUsers(Optional<Integer> pageNumber,Optional<Integer> itemsPerPage) {
		// This returns a JSON or XML with the users
		return userService.getUsers();
	}
	
	@GetMapping(path = "/public")
	public String publicApi() {
		return "Welcome to the public area.";
	}
	
	@PostMapping("/add")
	public @ResponseBody AppUser addNewUser(@RequestBody AppUser user
			) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		/*user.setRoles(new HashSet<Role>());
		
		Role userRole = new Role("user","simplest user");
		user.getRoles().add(userRole);
		userRole.setPermissions(new HashSet<Permission>());
		
		Role adminRole = new Role("admin","simplest admin");
		user.getRoles().add(adminRole);
		adminRole.setPermissions(new HashSet<Permission>());
		
		Role customerRole = new Role("customer","simplest customer");
		user.getRoles().add(customerRole);
		customerRole.setPermissions(new HashSet<Permission>());
		
		Permission deletePermission = new Permission("delete", "allows for record deletions");
		Permission readPermission = new Permission("read", "allows for record reads");
		Permission writePermission = new Permission("write", "allows for record writes");
		
		userRole.getPermissions().add(readPermission);
		userRole.getPermissions().add(writePermission);
		adminRole.getPermissions().add(deletePermission);
		customerRole.getPermissions().add(readPermission);*/

		userService.addNewUser(user);
		// This returns a JSON or XML with the users
		return user;
	}
	
	@PostMapping("/upload") //https://www.youtube.com/watch?v=Hef5pJkNCvA
	public String uploadFile(@RequestParam("files") MultipartFile[] files) {
		StringBuilder fileNames = new  StringBuilder();
		for(MultipartFile file:files) {
			Path fileNameAndPath = Paths.get(uploadDirectory,file.getOriginalFilename());
			
			try {
				Files.write(fileNameAndPath, file.getBytes());
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
		                .path("/downloadFile/")
		                .path(file.getOriginalFilename())
		                .toUriString();
				fileNames.append(fileDownloadUri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "files uploaded"+fileNames.toString();
	}
	
	/*@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }*/
	
	
}
