package it.uniroma3.siw.giugno20.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import it.uniroma3.siw.giugno20.model.Credentials;
import it.uniroma3.siw.giugno20.repository.CredentialRepository;

@Service
public class CredentialService {
	
	@Autowired
	CredentialRepository credentialRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Transactional
	public Credentials getCredential (Long id) {
		Optional<Credentials> result = this.credentialRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials getCredential (String username) {
		Optional <Credentials> result = this.credentialRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials c) {
		c.setRole(Credentials.DEFAULT_ROLE);
		c.setPassword(this.passwordEncoder.encode(c.getPassword()));
		return this.credentialRepository.save(c);
	}
	
	@Transactional
	public List<Credentials> getAll(){
		List<Credentials> all = new ArrayList<Credentials>();
		Iterable<Credentials> credential = this.credentialRepository.findAll();
		for(Credentials c : credential)
			all.add(c);
		return all;
		
	}
	
	@Transactional
	public void deleteByUsername(String username) {
		this.credentialRepository.deleteByUsername(username);
	}
}
