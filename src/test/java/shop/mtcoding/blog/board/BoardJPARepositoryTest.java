package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BoardJPARepositoryTest {

    @Autowired
    private BoardJPARepository boardJPARepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        User sessionUser = User.builder().id(1).build();
        Board board = Board.builder()
                .title("제목5")
                .content("내용5")
                .user(sessionUser)
                .build();

        boardJPARepository.save(board);

        System.out.println("save_test : " + board.getId());

    }

    @Test
    public void findById_test() {
        int id = 1;

        Optional<Board> boardOP = boardJPARepository.findById(id);

        if (boardOP.isPresent()) {
            Board board = boardOP.get();
            System.out.println("findById_test : " + board.getTitle());
        }
    }

    @Test
    public void findByJoinUser_test() {
        int id = 1;

        Optional<Board> board = boardJPARepository.findByIdJoinUser(id);

//        System.out.println("findByIdJoinUser_test : " + board.getTitle());
//        System.out.println("findByIdJoinUser_test : " + board.getUser().getUsername());

    }

    @Test
    public void findAll_test() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boardList = boardJPARepository.findAll(sort);

        System.out.println("findAll_test : " + boardList);

    }

    @Test
    public void deleteById_test() {
        int id = 1;
        boardJPARepository.deleteById(id);
        em.flush();

    }

    @Test
    public void findAll_paging_test() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 3, sort);



    }


}
