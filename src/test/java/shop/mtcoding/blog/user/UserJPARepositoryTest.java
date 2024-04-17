package shop.mtcoding.blog.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserJPARepositoryTest {

    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    public void save_test() {
        User user = User.builder()
                .username("happy")
                .password("1234")
                .email("happy@nate.com")
                .build();


        userJPARepository.save(user);

    }

    @Test
    public void findById_test() {
        int id = 1;

        Optional<User> userOP = userJPARepository.findById(id);

        if (userOP.isPresent()) {
            User user = userOP.get();
            System.out.println("findById_test : " + user.getUsername());
        }

    }

    @Test
    public void findAll_test() {
        //userJPARepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<User> userList = userJPARepository.findAll(sort);

    }


}
