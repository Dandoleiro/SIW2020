package it.uniroma3.siw.giugno20.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.giugno20.model.Credentials;

public interface CredentialRepository extends CrudRepository<Credentials, Long> {
	
	public Optional<Credentials> findByUsername(String username);
	public void deleteByUsername(String username);
	

}
