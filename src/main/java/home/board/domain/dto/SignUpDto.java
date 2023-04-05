package home.board.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

@Data
public class SignUpDto {

    private String userName;

    private String nickName;

    private String password;

}
