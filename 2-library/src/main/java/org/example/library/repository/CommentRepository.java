package org.example.library.repository;

import org.example.library.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join Book b on b.id = c.book.id where c.book.id =:bookId")
    List<Comment> getAllCommentsByBookId(@Param("bookId") long bookId);
}
