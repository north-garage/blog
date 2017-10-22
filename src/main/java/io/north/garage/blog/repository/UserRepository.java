package io.north.garage.blog.repository;

import io.north.garage.blog.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    User findByEmail(String email);
}
