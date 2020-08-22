package com.upic.service;

import com.upic.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment comment);

    String deleteComment(long id);

    Page<Comment> searchComment(Pageable pageable);

    List<Comment> searchComment();

    Comment findOne(long id);

    String updateComment(Comment comment);

    Page<Comment> findByTheSolutionId(long theSolutionId,Pageable pageable);

    Page<Comment> findByUserId(long userId,Pageable pageable);

    List<Comment> findByUserId(long userId);
}
