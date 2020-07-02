package com.codegym.dao.repository;

import com.codegym.dao.model.Comment;
import com.codegym.dao.model.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findRepliesByComment(Comment comment, Pageable pageable);
}
