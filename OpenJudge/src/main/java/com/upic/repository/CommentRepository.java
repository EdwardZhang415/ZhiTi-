package com.upic.repository;

import com.upic.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByUserId(long userId);

    Page<Comment> findByUserId(long userId, Pageable pageable);

    Page<Comment> findByTheSolutionId(long theSolutionId, Pageable pageable);
}
