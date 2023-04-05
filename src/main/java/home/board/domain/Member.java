package home.board.domain;

import home.board.domain.dto.SignUpDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "Member_Id")
    private long id;

    private String provider;

    private String providerId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    private String email;
    private String dob;
    private String age;

    public Member() {

    }

    public Member(SignUpDto signUpDto) {
        this.userName = signUpDto.getUserName();
        this.nickName = signUpDto.getNickName();
        this.password = signUpDto.getPassword();
    }

    @Builder
    public Member(String provider, String providerId, String username, String email, String nickname, String password) {
        this.provider = provider;
        this.providerId = providerId;
        this.userName = username;
        this.nickName = nickname;
        this.password = password;
        this.email = email;
    }

    public static int calcAge(String dob) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        int user_year = Integer.parseInt(dob.substring(0, 4));
        int user_month = Integer.parseInt(dob.substring(5, 7));
        int user_day = Integer.parseInt(dob.substring(8, 10));

        int age = 0;

        if (month > user_month) {
            age = year - user_year;
        } else if (month < user_month) {
            age = year - user_year + 1;
        } else if (month == user_month) {
            if (day >= user_day) {
                age = year - user_year;
            } else {
                age = age = year - user_year + 1;
            }
        }
        return age;
    }
}