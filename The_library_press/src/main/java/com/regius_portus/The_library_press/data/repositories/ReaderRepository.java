package com.regius_portus.The_library_press.data.repositories;

import com.regius_portus.The_library_press.data.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader,Long> {


    Optional<Reader> findByEmail(String email);



}
