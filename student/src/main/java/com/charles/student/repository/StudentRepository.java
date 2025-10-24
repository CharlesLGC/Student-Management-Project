package com.charles.student.repository;

import java.util.List;

import com.charles.student.entity.Student;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "CAST(s.id AS string) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByName(@Param("name") String name);
}
