package home.board.web.repository;

import home.board.domain.Member;
import home.board.domain.dto.SignUpDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findById(long id);

    Member findByUserName(String username);

    Member findByNickName(String nickname);

}
