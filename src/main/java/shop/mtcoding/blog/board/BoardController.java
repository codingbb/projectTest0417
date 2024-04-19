package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@RestController // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardRepository boardRepository;
    private final HttpSession session;
    private final BoardService boardService;

    //글쓰기
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.게시글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    //게시글 업뎃 (수정하기)
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.게시글업뎃(reqDTO, id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    //업데이트 폼
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> updateForm(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.게시글수정폼(id, sessionUser.getId());

        return ResponseEntity.ok(new ApiUtil<>(board));
    }

    //삭제하기
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글삭제(id, sessionUser.getId());

        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    //글 목록보기
    @GetMapping("/")
    public ResponseEntity<?> index() {
        List<Board> boardList = boardService.게시글목록조회();

        return ResponseEntity.ok(new ApiUtil<>(boardList));
    }


    //글 상세보기
    @GetMapping("/api/boards/{id}/detail")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        //원래 Board board가 아니라 이렇게 DTO로 받아와야함
        BoardRequest.DetailDTO board = boardService.게시글상세보기(id, sessionUser);

        return ResponseEntity.ok(new ApiUtil<>(board));
    }
}
