package com.IdeaGenerator.IdeaGenerator.Repository;

import com.IdeaGenerator.IdeaGenerator.models.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea,Integer> {
    @Query(value = "SELECT CASE WHEN EXISTS( SELECT 1 FROM idea WHERE name = :name ) THEN 1 ELSE 0 END",
           nativeQuery = true)
    int existsByName(@Param("name") String name);

    @Query(value = "SELECT * FROM idea WHERE name = :name",
            nativeQuery = true)
    Idea findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM idea WHERE name IN ?1",
            nativeQuery = true)
    int deleteAllByName(List<String> name);

    @Query(value = "SELECT * FROM ideadb.idea ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    Idea findRandom();

    @Query(value = "SELECT * FROM ideadb.idea WHERE ideaType = :type ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    Idea findRandomByType(@Param("type") String type);
}
