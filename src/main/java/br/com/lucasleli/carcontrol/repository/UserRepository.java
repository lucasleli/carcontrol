package br.com.lucasleli.carcontrol.repository;

import br.com.lucasleli.carcontrol.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
