package shop.mtcoding.blog.reply;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ReplyJPARepositoryTest {

    @Autowired
    private ReplyJPARepository replyJPARepository;

    @Autowired
    private EntityManager em;


    @Test
    public void findByBoardId_test() {
        Integer boardId = 4;
        List<Reply> replies = replyJPARepository.findByBoardId(boardId);

        System.out.println("findByBoardId_test : " + replies.size());

    }


}
