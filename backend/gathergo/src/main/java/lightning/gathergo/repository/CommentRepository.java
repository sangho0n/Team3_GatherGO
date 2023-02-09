package lightning.gathergo.repository;

import lightning.gathergo.model.Comment;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Integer> {

    // 저장
    @Override
    default <S extends Comment> S save(S entity){
        return (S) save(entity.getArticleId(), entity.getUserId(), entity.getDate(), entity.getContent(), entity.getUuid());
    }

    @Query("insert into comment (articleId, userId, date, content, uuid) " +
            "values (" +
            "(select id from article where uuid = :articleUuid), " +
            "(select id from user where uuid = :userUuid), " +
            ":date, " +
            ":content, " +
            ":uuid)")
    Comment save(Integer articleId, Integer userId, Date date, String content, String uuid);

    @Query("select * from comment where uuid=:uuid")
    Comment findByUuid(String uuid);

    // 수정
    @Modifying
    @Query("update comment set content = :content, date = :date where uuid = :uuid")
    void updateByUuid(String content, Date date, String uuid);


    // 삭제
    @Query("delete from comment where uuid=:uuid")
    void deleteByUuid(String uuid);
}
