package shop.mtcoding.blog.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository;

    public BoardRequest.DetailDTO 게시글상세보기(Integer boardId, User sessionUser) {
        Board board = boardJPARepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없음"));

        return new BoardRequest.DetailDTO(board, sessionUser);
    }


    public List<Board> 게시글목록조회() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return boardJPARepository.findAll(sort);

    }

    @Transactional
    public void 게시글삭제(Integer boardId, Integer sessionUserId) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글 존재안함"));

        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("삭제 권한 없음");
        }

        boardJPARepository.deleteById(boardId);

    }


    public Board 게시글수정폼(Integer boardId, Integer sessionUserId) {
        //게시글 있는지 확인
        //권한 없는 사람은 수정폼에 못들어가게
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글이 없습니다"));

        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("수정 권한이 없습니다");
        }

        return board;

    }

    @Transactional
    public void 게시글업뎃(BoardRequest.UpdateDTO requestDTO, Integer boardId, Integer sessionUserId) {
        //게시글 번호 알아야 수정 가능
        //게시글 있는지 확인
        //게시글 쓴 유저만 업뎃 가능

        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글이 없습니다"));

        if (sessionUserId != board.getUser().getId()) {
            throw new Exception403("수정 권한이 없습니다");
        }

        board.setTitle(requestDTO.getTitle());
        board.setContent(requestDTO.getContent());

    }

    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO requestDTO, User sessionUser) {
        boardJPARepository.save(requestDTO.toEntity(sessionUser));

    }

}
