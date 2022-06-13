package com.sac.backend.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select * from post p where p.is_public = 1", nativeQuery = true)
    List<Post> listAllPublic();

    @Query(value = "select * from post p where p.is_public = 1 and p.id = ?1", nativeQuery = true)
    Optional<Post> findByIdPublic(Long id);
}
