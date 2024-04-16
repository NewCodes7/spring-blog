package me.newcodes.blog.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import me.newcodes.blog.domain.Article;
import org.springframework.jdbc.datasource.DataSourceUtils;
import java.sql.*;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcBlogRepository implements BlogRepository {
    private final DataSource dataSource;
    public JdbcBlogRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Article save(Article article) {
        String sql = "insert into article(title, content) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContent());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                article.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return article;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select * from article";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Article> articles = new ArrayList<>();
            while(rs.next()) {
                Article article = new Article();
                article.setId(rs.getLong("id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                articles.add(article);
            }
            return articles;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "select * from article where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Article article = new Article();
                article.setId(rs.getLong("id"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                return Optional.of(article);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from article where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate(); // executeUpdate를 사용하여 영향을 받은 행의 수를 반환
            if (affectedRows == 0) {
                throw new IllegalStateException("No article deleted with id: " + id);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
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