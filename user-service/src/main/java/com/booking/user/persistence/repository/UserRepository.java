package com.booking.user.persistence.repository;

import com.booking.user.persistence.entity.User;
import com.booking.user.persistence.entity.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByIdAndType(String id, UserType type);
}
