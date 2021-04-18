package com.study.cachedemo.service;

import com.study.cachedemo.dao.UserDAO;
import com.study.cachedemo.dto.entity.User;
import com.study.cachedemo.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDAO<User> {
  private final UserRepository userRepository;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findById(Long id) {
    return Optional.of(userRepository.findById(id)).orElse(null);
  }

  @Override
  public User save(User request) {
    return userRepository.save(request);
  }

  @Override
  public User update(Long id, User request) {
//    Request user = this.findById(id).orElse(null);

    User updateUser = User.builder()
//        .id(user.getId())
        .id(id)
        .userName(request.getUserName())
        .phoneNumber(request.getPhoneNumber())
        .password(request.getPassword())
        .address(request.getAddress())
        .build();
    return this.save(updateUser);
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
