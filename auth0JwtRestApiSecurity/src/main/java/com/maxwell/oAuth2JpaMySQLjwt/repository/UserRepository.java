package com.maxwell.oAuth2JpaMySQLjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxwell.oAuth2JpaMySQLjwt.domain.AppUser;
import java.lang.String;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
  AppUser findByUsername(String username);
}
