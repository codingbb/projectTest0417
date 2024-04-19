package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    //댓글 삭제
    @DeleteMapping("/api/replies/{id}")
    public String delete(@PathVariable Integer boardId, @PathVariable Integer replyId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(sessionUser, replyId);

        return "redirect:/board/" + boardId;
    }


    //댓글 save 쓰기
    @PostMapping("/api/replies")
    public String save(@RequestBody ReplyRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글쓰기(requestDTO, sessionUser);

        return "redirect:/board/" + requestDTO.getBoardId();
    }

}
