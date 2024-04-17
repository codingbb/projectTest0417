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
        private Integer userId;
        private String username;
        private Boolean isBoardOwner;
        private Boolean isReplyOwner;
        private List<ReplyDTO> replies = new ArrayList<>();

        public DetailDTO(Integer id, String title, String content, Integer userId, String username, Boolean isBoardOwner, Boolean isReplyOwner, List<ReplyDTO> replies) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.userId = userId;
            this.username = username;
            this.isBoardOwner = isBoardOwner;
            this.isReplyOwner = isReplyOwner;
            this.replies = replies;
        }

        @Data
        public class ReplyDTO {
            private Integer id;
            private String comment;


            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.comment = reply.getComment();
                this.user = new UserDTO(reply);
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
