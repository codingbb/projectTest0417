package shop.mtcoding.blog.user;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.utils.ApiUtil;


@RequiredArgsConstructor
@RestController
public class UserController {
    
    private final HttpSession session;
    private final UserService userService;

    //업데이트
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> update(@RequestBody UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newSessionUser = userService.회원수정(sessionUser.getId(), reqDTO);
        session.setAttribute("sessionUser", newSessionUser);

        return ResponseEntity.ok(new ApiUtil<>(newSessionUser));
    }

    //업데이트 폼
    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원조회(sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(user));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
        User user = userService.회원가입(reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO reqDTO) {
        User sessionUser = userService.로그인(reqDTO);
        session.setAttribute("sessionUser", sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
