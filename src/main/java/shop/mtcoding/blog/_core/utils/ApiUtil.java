package shop.mtcoding.blog._core.utils;

public class ApiUtil<T> {
    private Integer status;
    private String msg;
    private T body;

    //성공시
    public ApiUtil(T body) {
        this.status = 200;
        this.msg = "성공";
        this.body = body;
    }

    //실패시
    public ApiUtil(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.body = null;
    }
}
