package com.fram.challenge.user;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users",
  produces = MediaType.APPLICATION_JSON_VALUE,
  consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<Map<String, String>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @GetMapping("/hierarchy")
  public ResponseEntity<Map<String, Map<String, Object>>> getUsersHierarchy() {
    return ResponseEntity.ok(userService.getUsersHierarchy());
  }

  @GetMapping("/{userName}")
  public ResponseEntity<UserSupervisorDto> getSupervisor(@PathVariable String userName) {
    return ResponseEntity.ok(userService.getSupervisor(userName));
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(userService.createUser(userDto));
  }

  @PostMapping("/assign-supervisor")
  public ResponseEntity<Object> assignSupervisor(@RequestBody UserDto userDto) {
    try {
      return ResponseEntity.ok(userService.assignSupervisor(userDto));
    } catch (IllegalArgumentException exception) { //could improve by exception mapper
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }
}
