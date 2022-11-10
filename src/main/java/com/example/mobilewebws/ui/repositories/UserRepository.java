package com.example.mobilewebws.ui.repositories;

import com.example.mobilewebws.io.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
  Optional<UserEntity> findByUserId(String userId);
  List<UserEntity> findByFirstName (String firstName);
  List<UserEntity> findAll();

  @Modifying
  @Query("delete from users u where u.userId = ?1")
  void deleteByUserId(String userId);
}
