package minipj.placepic_core.Service;

import lombok.RequiredArgsConstructor;
import minipj.placepic_core.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}
