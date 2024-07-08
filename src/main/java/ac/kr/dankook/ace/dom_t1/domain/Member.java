package ac.kr.dankook.ace.dom_t1.domain;

public class Member {

    private Long id;
    private String email;
    private String psw;
    private String nickname;
    private String keyword;
    private int callnumber; // 전화번호 부분은 - 를 제외하고 받도록 한다.

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }



    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
  


    public String getPsw() {
        return psw;
    }
    public void setPsw(String psw) {
        this.psw = psw;
    }



    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }



    public int getCallnumber() {
        return callnumber;
    }
    public void setCallnumber(int callnumber) {
        this.callnumber = callnumber;
    }
    
}
