package com.hello.jdbc.repository;


import com.hello.jdbc.connection.DBConnectionUtil;
import com.hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepositoryV0 {

    public Member saveMember(Member member) {
        String sql = "INSERT INTO member(member_id, money) VALUES(?,?)";
        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con =  getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setString(1, member.getMemberId());
            psmt.setInt(2, member.getMoney());
            return member;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}
