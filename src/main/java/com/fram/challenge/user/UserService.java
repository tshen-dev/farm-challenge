package com.fram.challenge.user;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repo;

  public UserDto createUser(UserDto userDto) {
    if (isValidUserToCreate(userDto)) {
      var supervisor = StringUtils.defaultIfBlank(userDto.getSupervisor(), "");
      repo.save(new User(userDto.getUserName(), supervisor));
    }
    return userDto;
  }

  private boolean isValidUserToCreate(UserDto userDto) {
    return StringUtils.isNotBlank(userDto.getUserName())
        && !repo.existsById(userDto.getUserName())
        && (StringUtils.isBlank(userDto.getSupervisor()) || repo.existsById(userDto.getSupervisor()));
  }

  @Transactional
  public UserDto assignSupervisor(UserDto userDto) {
    var userName = userDto.getUserName();
    var supervisor = userDto.getSupervisor();
    if (isValidAssign(userName, supervisor)) {
      repo.findById(userName).ifPresent(item -> item.setSupervisor(supervisor));
      return userDto;
    }
    throw new IllegalArgumentException();
  }

  private boolean isValidAssign(String userName, String supervisor) {
    if (isChildrenContain(userName, supervisor)) {
      var parentChain = getParentChain(supervisor);
      throw new IllegalArgumentException("hierarchies make nonsense that contain loops or multiple roots: " + parentChain);
    }
    return !StringUtils.equals(userName, supervisor)
        && StringUtils.isNoneBlank(userName, supervisor)
        && repo.existsById(userName)
        && repo.existsById(supervisor);
  }

  private String getParentChain(String userName) {
    var supervisor = repo.findById(userName).map(User::getSupervisor).orElse(StringUtils.EMPTY);
    StringBuilder result = new StringBuilder(userName);

    while (StringUtils.isNotBlank(supervisor)) {
      result.insert(0, supervisor + " => ");
      supervisor = repo.findById(supervisor).map(User::getSupervisor).orElse(StringUtils.EMPTY);
    }

    return result.toString();
  }

  private boolean isChildrenContain(String userName, String findChild) {
    var children = repo.findBySupervisor(userName);
    var isChildrenContain = children.stream().anyMatch(user -> user.getUserName().equals(findChild));
    if (isChildrenContain) {
      return true;
    } else if (!children.isEmpty()) {
      for (User child : children) {
        if (isChildrenContain(child.getUserName(), findChild)) {
          return true;
        }
      }
    }
    return false;
  }

  public Map<String, String> getUsers() {
    return repo.findAll().stream()
        .collect(Collectors.toMap(User::getUserName, User::getSupervisor));
  }

  public Map<String, Map<String, Object>> getUsersHierarchy() {
    return repo.findBySupervisor("").stream().findFirst()
        .map(user -> Map.of(user.getUserName(), findChildMap(user.getUserName())))
        .orElse(Map.of());
  }

  private Map<String, Object> findChildMap(String userName) {
    var children = repo.findBySupervisor(userName);
    if (children.isEmpty()) {
      return Map.of();
    } else {
      Map<String, Object> results = new HashMap<>();
      for (User child : children) {
        results.put(child.getUserName(), findChildMap(child.getUserName()));
      }
      return results;
    }
  }

  public UserSupervisorDto getSupervisor(String userName) {
    var value = new UserSupervisorDto();
    var supervisor = repo.findById(userName)
        .map(User::getSupervisor)
        .orElse(StringUtils.EMPTY);
    var supervisorSupervisor = repo.findById(supervisor)
        .map(User::getSupervisor)
        .orElse(StringUtils.EMPTY);
    value.setSupervisor(supervisor);
    value.setSupervisorSupervisor(supervisorSupervisor);

    return value;
  }
}
