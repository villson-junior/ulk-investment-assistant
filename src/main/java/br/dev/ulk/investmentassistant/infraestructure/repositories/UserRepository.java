package br.dev.ulk.investmentassistant.infraestructure.repositories;

import br.dev.ulk.investmentassistant.domain.models.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}