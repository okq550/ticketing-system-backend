package org.okq550.ticketing.repositories;

import java.util.UUID;

import org.okq550.ticketing.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
