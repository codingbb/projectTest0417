package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardRequest {

    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private UserDTO user;
        private Boolean isOwner;    //board 오너
        private List<ReplyDTO> replies = new ArrayList<>();

        public DetailDTO(Board board, User sessionUser, List<Reply> replies) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new UserDTO(board.getUser());
            this.isOwner = false;

            if (sessionUser != null) {
                if (sessionUser.getId() == board.getUser().getId()) {
                    isOwner = true;
                }
            }

            this.replies = replies.stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();

        }

        @Data
        public class ReplyDTO {
            private Integer id;
            private String comment;
            private Boolean isReplyOwner;
            private UserDTO user;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.comment = reply.getComment();
                this.isReplyOwner = false;
                this.user = new UserDTO(reply.getUser());

                if (sessionUser != null) {
                    if (sessionUser.getId() == reply.getUser().getId()) {
                        isReplyOwner = true;
                    }
                }
            }
        }

        @Data
        public class UserDTO {
            private Integer id;
            private String username;

            public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

    }


    @Data
    public static class UpdateDTO {
        private String title;
        private String content;
    }

    @Data
    public static class SaveDTO {
        private String title;
        private String content;

        // DTO를 클라이언트로 부터 받아서, PC에 전달하기 위해 사용
        public Board toEntity(User user){
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }
}
