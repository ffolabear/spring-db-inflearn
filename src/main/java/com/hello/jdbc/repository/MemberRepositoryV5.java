package com.hello.jdbc.repository;


import com.hello.jdbc.domain.Member;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * JdbcTemplate 사용
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository {

    private final JdbcTemplate template;

    public MemberRepositoryV5(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member(member_id, money) VALUES(?,?)";
        template.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }

    public Member findById(String memberId) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        return (Member) template.queryForObject(sql, memberRowMapper(), memberId);
    }

    public void update(String memberId, int money) {
        String sql = "UPDATE member SET money = ? WHERE member_id = ?";
        template.update(sql, money, memberId);
    }

    public void delete(String memberId) {
        String sql = "DELETE FROM member WHERE member_id = ?";
        template.update(sql, memberId);
    }

    private RowMapper<Object> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString(rs.getString("member_id")));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }

}
