package com.example.springboot.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.springboot.Login;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Login, Integer>
{
}