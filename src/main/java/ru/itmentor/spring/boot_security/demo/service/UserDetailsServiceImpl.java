package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.entity.Person;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.repository.PersonRepo;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepo;
import ru.itmentor.spring.boot_security.demo.security.PersonDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final PersonRepo personRepo;

    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, RoleRepo roleRepo, PersonRepo personRepo) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.personRepo = personRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepo.findByUsername(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }

    public Person findPersonById (Long id) {
        Optional<Person> person = personRepo.findById(id);
        return person.get();
    }

    public List<Person> allPersons() {
        return personRepo.findAll();
    }

    public Role findRoleById () {
        Optional<Role> roleFromBd = roleRepo.findById(1L);
        return roleFromBd.get();
    }

    public void update(Person person){
        personRepo.save(person);
    }

    public void save(Person person) {
        person.setRoles(Collections.singletonList(findRoleById()));
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepo.save(person);
    }

    public boolean deleteUser(Long id) {
        if (personRepo.findById(id).isPresent()) {
            personRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
