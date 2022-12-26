package org.example.library.repository;

import org.example.library.domain.Comment;

import java.util.List;

public interface CommentRepository {

    void createComment(Comment comment);

    Comment getById(long id);

    List<Comment> getAllCommentsByBookId(long id);

    Comment updateComment(Comment comment);

    void deleteById(long id);
}
