package br.com.notajuris.notajuris.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.notajuris.notajuris.model.cargo.Cargo;
import br.com.notajuris.notajuris.model.cargo.CargoNome;

@Repository
public interface CargoRepository  extends JpaRepository<Cargo, Integer>{

    Optional<Cargo> findByNome(CargoNome nome);

}
