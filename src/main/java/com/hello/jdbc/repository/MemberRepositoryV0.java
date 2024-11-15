package com.hello.jdbc.repository;


import com.hello.jdbc.connection.DBConnectionUtil;
import com.hello.jdbc.domain.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "INSERT INTO member(member_id, money) VALUES(?,?)";
        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setString(1, member.getMemberId());
            psmt.setInt(2, member.getMoney());
            psmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            //여기서 exception 이 생기면 connection 은 종료되지 않음
            close(con, psmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberId);
            rs = psmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e.getMessage());
            throw e;
        } finally {
            close(con, psmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "UPDATE member SET money = ? WHERE member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, money);
            psmt.setString(2, memberId);
            int resultSize = psmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e.getMessage());
            throw e;
        } finally {
            //여기서 exception 이 생기면 connection 은 종료되지 않음
            close(con, psmt, null);
        }
    }


    public void delete(String memberId) throws SQLException {
        String sql = "DELETE FROM member WHERE member_id = ?";

        Connection con = null;
        PreparedStatement psmt = null;

        try {
            con = getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberId);
            psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e.getMessage());
            throw e;
        } finally {
            //여기서 exception 이 생기면 connection 은 종료되지 않음
            close(con, psmt, null);
        }

    }


    private void close(Connection con, Statement stmt, ResultSet rs) {
        //ResultSet 은 결과조회할 때 사용
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("rs error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("stmt error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("con error", e);
            }
        }

    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}
