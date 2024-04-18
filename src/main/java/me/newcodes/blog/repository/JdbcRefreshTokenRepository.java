package me.newcodes.blog.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javax.sql.DataSource;
import me.newcodes.blog.domain.RefreshToken;
import me.newcodes.blog.domain.User;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public class JdbcRefreshTokenRepository implements RefreshTokenRepository {
    private final DataSource dataSource;

    public JdbcRefreshTokenRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<RefreshToken> findByUserId(Long userId) {
        String sql = "SELECT * FROM refresh_tokens WHERE user_id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setUserId(rs.getLong("user_id"));
                refreshToken.setRefreshToken(rs.getString("refresh_token"));
                return Optional.of(refreshToken);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String refresh_token) {
        String sql = "SELECT * FROM refresh_tokens WHERE refresh_token = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, refresh_token);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setUserId(rs.getLong("user_id"));
                refreshToken.setRefreshToken(rs.getString("refresh_token"));
                return Optional.of(refreshToken);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        String sql = "insert into refresh_tokens(user_id, refresh_token) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, refreshToken.getUserId());
            pstmt.setString(2, refreshToken.getRefreshToken());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                refreshToken.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return refreshToken;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
