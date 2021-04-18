package com.study.cachedemo.controller;

import com.study.cachedemo.dao.UserDAO;
import com.study.cachedemo.dto.entity.User;
import com.study.cachedemo.util.response.DefaultResponse;
import com.study.cachedemo.util.response.ResponseHandler;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http .ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserDAO<User> userDAO;
  private final ResponseHandler responseHandler;

  /**
   * 전체 조회 결과와 같이 많은 데이터를 읽어오는 로직에 캐시를 사용하면 좋으나, 해당 값이 변하지 않는다(적어도 캐시의 갱신 시간 내에)는 전제 조건이 필요하다.
   * 즉, 대량의 고정된 값을 읽어올 때 사용하면 성능의 이점이 있으나,
   * 값이 자주 변경되는 로직이라면 값 불일치 문제가 발생할 가능성이 많으므로 사용하지 않는 것이 좋다.
   **/
  @Cacheable(cacheNames = "users")
  @GetMapping("/users")
  public ResponseEntity<DefaultResponse<Object>> selectUserByAll(HttpServletRequest request){
    List<User> userList = null;

    try {
      userList = userDAO.findAll();
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return ResponseEntity.ok().body(responseHandler.createResponse(userList, request));
  }

  @Cacheable(cacheNames = "user_info", key = "#id")
  @GetMapping("users/{id}")
  public ResponseEntity<DefaultResponse<Object>> selectUserById(HttpServletRequest request,
                                                                @PathVariable String id) {
    Optional<User> user = null;
    try {
      user = Optional.of(userDAO.findById(Long.valueOf(id))).orElse(null);
    } catch (NumberFormatException e) {
      log.error(e.getMessage());
    }

    return ResponseEntity.ok().body(responseHandler.createResponse(user, request));
  }

  @PostMapping("/user")
  public ResponseEntity<DefaultResponse<Object>> insertUser(HttpServletRequest request,
                                                            @RequestBody User user) {
    User saveUser = null;
    try {
     saveUser = userDAO.save(user);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return ResponseEntity.ok().body(responseHandler.createResponse(saveUser, request));
  }

  @PutMapping("users/{id}")
  public ResponseEntity<DefaultResponse<Object>> updateUserById(HttpServletRequest request,
                                                                @PathVariable String id,
                                                                @RequestBody User userRequest) {
//    Long updateId = Long.valueOf(id);
    User updateUser = null;
    try {
      updateUser = userDAO.update(Long.valueOf(id), userRequest);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return ResponseEntity.ok().body(responseHandler.createResponse(updateUser, request));
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<DefaultResponse<Object>> deleteUserById(HttpServletRequest request,
                                                                @PathVariable String id) {
    userDAO.deleteById(Long.valueOf(id));
    return ResponseEntity.ok().body(responseHandler.createResponse(true,request));
  }
}
