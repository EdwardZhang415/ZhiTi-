package com.upic.serviceImpl;

import com.upic.po.Comment;
import com.upic.repository.CommentRepository;
import com.upic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CommentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public String deleteComment(long id) {
        commentRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<Comment> searchComment(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public List<Comment> searchComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findOne(long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public String updateComment(Comment comment) {
        commentRepository.saveAndFlush(comment);
        return "SUCCESS";
    }

    @Override
    public Page<Comment> findByTheSolutionId(long theSolutionId,Pageable pageable) {
        return commentRepository.findByTheSolutionId(theSolutionId,pageable);
    }

    @Override
    public Page<Comment> findByUserId(long userId, Pageable pageable) {
        return commentRepository.findByUserId(userId,pageable);
    }

    @Override
    public List<Comment> findByUserId(long userId) {
        return commentRepository.findByUserId(userId);
    }
}
