package thien.authenticate;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtResponse implements Serializable {

    private String token;
    private Integer userId;
    private String maNV;

    public JwtResponse(String token, MyUser user) {
        this.token = token;
        this.userId = user.getUserId();
        this.maNV = user.getMaNV();
    }
}
