package ru.itmentor.spring.boot_security.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
