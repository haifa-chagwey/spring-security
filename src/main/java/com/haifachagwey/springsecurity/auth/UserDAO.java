package com.haifachagwey.springsecurity.auth;

import java.util.Optional;

// Its important to have an interface for UserDAO, because we can switch between different implementations easily (Postgres, MongoDB, etc)
public interface UserDAO {

    Optional<User> findByUsername(String username);
}
