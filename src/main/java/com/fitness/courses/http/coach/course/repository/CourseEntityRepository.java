package com.fitness.courses.http.coach.course.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

@Repository
public interface CourseEntityRepository extends JpaRepository<CourseEntity, Long>
{
    List<CourseEntity> findByAuthorId(Long authorId);

    Page<CourseEntity> findAllByOrderByDateTimeCreatedDesc(Pageable pageable);

    @Query("SELECT c, (SELECT COUNT(s) FROM StudentEntity s WHERE s.course = c) AS studentCount " +
            "FROM CourseEntity c " +
            "ORDER BY studentCount DESC")
    Page<Object[]> findCoursesByPopularity(Pageable pageable);

    @Query("SELECT c FROM CourseEntity c " +
            "WHERE c.title LIKE %:keyword% " +
            "OR c.shortDescription LIKE %:keyword% " +
            "OR c.about LIKE %:keyword% " +
            "OR LOWER(c.author.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.author.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<CourseEntity> findCoursesByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
