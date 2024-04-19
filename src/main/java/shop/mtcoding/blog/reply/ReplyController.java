package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    //댓글 삭제
    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer boardId, @PathVariable Integer replyId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(sessionUser, replyId);

        return ResponseEntity.ok(new ApiUtil<>(null));
    }


    //댓글 save 쓰기
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Reply reply = replyService.댓글쓰기(requestDTO, sessionUser);

        return ResponseEntity.ok(new ApiUtil<>(reply));
    }

}
