package shop.mtcoding.blog.reply;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyJPARepository replyJPARepository;
    private final BoardJPARepository boardJPARepository;

    @Transactional
    public void 댓글삭제(User sessionUser, Integer replyId) {
        Reply reply = replyJPARepository.findById(replyId)
                .orElseThrow(() -> new Exception404("없는 댓글은 삭제 불가"));

        if (reply.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("삭제 권한이 없음");
        }

        replyJPARepository.deleteById(replyId);

    }


    @Transactional
    public Reply 댓글쓰기(ReplyRequest.SaveDTO requestDTO, User sessionUser) {
        Board board = boardJPARepository.findById(requestDTO.getBoardId())
                        .orElseThrow(() -> new Exception404("없는 게시글에는 댓글 작성 불가"));

        return replyJPARepository.save(requestDTO.toEntity(sessionUser, board));

    }
}
