package com.study.cachedemo.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic 을 사용하여 UserDTO 내의 inner class 사용을 유연하게 해주려는 의도였는데
 * 그럴 필요가 있나.. 무조건 request 를 사용할 거 같은데.. 좀 더 고민 해보는걸로
 *
 * @param <T> UserDTO.Request 용으로 Post 매핑
 * @param <R> UserDTO.Response 용으로 Get 매핑 (민감 정보 제외)
 **/
public interface UserDAO<T> {
  List<T> findAll();
  Optional<T> findById(Long id);
  T save(T t);
  T update(Long id, T t);
  void deleteById(Long id);
}
