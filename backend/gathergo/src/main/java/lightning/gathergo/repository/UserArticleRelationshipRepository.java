package lightning.gathergo.repository;

import lightning.gathergo.model.UserArticleRelationship;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserArticleRelationshipRepository extends CrudRepository<UserArticleRelationship, Integer> {
//    @Query("insert into user_article_relationship (userId, articleId) values (:userId, :articleId)")
//    UserArticleRelationship save(Long userId, Long articleId);


    @Query("select * from user_article_relationship")
    List<UserArticleRelationship> findAll();

    @Modifying
    @Query(value="insert into user_article_relationship (userId, articleId) values(:userId, :articleId)")
    void save(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    @Query("select * from user_article_relationship us where us.userId=:userId")
    List<UserArticleRelationship> findByUserId(Integer userId);
}