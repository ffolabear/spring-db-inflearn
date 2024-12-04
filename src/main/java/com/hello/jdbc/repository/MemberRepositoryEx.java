package com.hello.jdbc.repository;

import com.hello.jdbc.domain.Member;
import java.sql.SQLException;

public interface MemberRepositoryEx {

    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;

}
