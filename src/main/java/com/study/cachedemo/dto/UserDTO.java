package com.study.cachedemo.dto;

import lombok.Builder;
import lombok.Getter;


/**
 * view 와 model 영역에서의 데이터 전달 객체를 분리하자.
 *
 * => 분리 방안으로 클래스로 나눌수도 있지만 그렇게 되면 규모에 따라 중복 클래스를 다량으로 만들어야 된다.
 * 때문에 inner 클래스를 활용하여 분리하자.
 **/
public class UserDTO {
  // POST 매핑용
  @Getter
  @Builder
  public static class Request {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String password;
  }

  // GET 매핑용
  @Getter
  @Builder
  public static class Response {
    private String name;
    private String phoneNumber;
    private String address;
  }

}
