package com.study.cachedemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.study.cachedemo.controller.UserController;
import com.study.cachedemo.dto.entity.User;
import com.study.cachedemo.util.response.DefaultResponse;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringCacheTest {
  @Autowired
  CacheManager cacheManager;

  @Autowired
  UserController userController;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() throws Exception {
    User user = User.builder()
        .userName("test")
        .password("test")
        .phoneNumber("010-1234-1234")
        .address("Seoul")
        .build();

//    userController.insertUser(user);

    mockMvc.perform(post("/user")
        .content(new ObjectMapper().writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  private User getCachedUser(String id) {
    Cache ca = Optional.ofNullable(cacheManager.getCache("user_info")).orElse(null);
    ResponseEntity entity = (ResponseEntity) ca.get("1").get();
    DefaultResponse body = (DefaultResponse) entity.getBody();
    Optional data = (Optional) body.getData();
    User user = (User) data.get();

    return user;
  }

  @Test
  void userCachingTest() throws Exception {
    String id = "1";

    //
    MvcResult mvcResult = mockMvc.perform(get("/users/"+id))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    System.out.println("##### content1: "+content);
    ObjectMapper mapper = new ObjectMapper();
    DefaultResponse defaultResponse = mapper.readValue(content, DefaultResponse.class);

    User user = responseStrToObject(defaultResponse);
    User cacheUser = getCachedUser(id);

    assertThat(user.getUserName()).isEqualTo(cacheUser.getUserName());

  }

  User responseStrToObject(DefaultResponse defaultResponse) {
    String jsonStr = new JSONObject((Map) defaultResponse.getData()).toString();
    Gson gson = new Gson();
    return gson.fromJson(jsonStr, User.class);
  }
}
