package br.com.xvidros.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.xvidros.api.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
