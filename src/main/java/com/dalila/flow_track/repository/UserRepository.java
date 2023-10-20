package com.dalila.flow_track.repository;

import com.dalila.flow_track.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


   List<User> findAllByNameContaining(String keyword);

   User findByName(String name);

   UserDetails findByLogin(String login);



}
