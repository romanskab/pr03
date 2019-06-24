package com.oktenweb.pr03.dao;

import com.oktenweb.pr03.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDAO extends JpaRepository<Document, Integer> {
}
