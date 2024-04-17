package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJPARepository userJPARepository;

    @Transactional
    public User 회원수정(Integer id, UserRequest.UpdateDTO requestDTO) {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));

        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());
        return user;

    }

    public User 회원조회(Integer id) {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다."));

        return user;
    }

    public User 로그인(UserRequest.LoginDTO requestDTO) {
        User sessionUser = userJPARepository.findByUsernameAndPassword(requestDTO.getUsername(), requestDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다"));

        return sessionUser;

    }

    @Transactional
    public void 회원가입(UserRequest.JoinDTO requestDTO) {
        Optional<User> userOP = userJPARepository.findByUsername(requestDTO.getUsername());

        if (userOP.isPresent()) {
            throw new Exception400("중복된 유저 네임입니다");
        }

        userJPARepository.save(requestDTO.toEntity());

    }

}
