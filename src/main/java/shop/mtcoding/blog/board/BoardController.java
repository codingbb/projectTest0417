package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@Controller // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {

    private final BoardRepository boardRepository;
    private final HttpSession session;
    private final BoardService boardService;

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글쓰기(reqDTO, sessionUser);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글업뎃(reqDTO, id, sessionUser.getId());
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = boardService.게시글수정폼(id, sessionUser.getId());

        request.setAttribute("board", board);
        return "board/update-form";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글삭제(id, sessionUser.getId());

        return "redirect:/";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Board> boardList = boardService.게시글목록조회();
        request.setAttribute("boardList", boardList);
        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardRequest.DetailDTO board = boardService.게시글상세보기(id, sessionUser);

        request.setAttribute("board", board);
        return "board/detail";
    }
}
