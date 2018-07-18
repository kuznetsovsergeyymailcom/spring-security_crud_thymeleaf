package spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.app.models.Role;
import spring.app.models.User;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/rest")
public class MyRestController {

    private final UserService userService;

    @Autowired
    public MyRestController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping(value = "/show-all-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        List<User> list = userService.getAllUsers();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {

        userService.addUser(user);
        List<User> list = userService.getAllUsers();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/edit-user/{id}")
    public ResponseEntity<List<User>> editUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        userService.updateUser(user);

        List<User> list = userService.getAllUsers();
        return ResponseEntity.ok().body(list);
    }

    // return response ok only
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity.BodyBuilder deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok();
    }

}
