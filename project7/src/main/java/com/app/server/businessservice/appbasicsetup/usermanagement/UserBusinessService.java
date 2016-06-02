package com.app.server.businessservice.appbasicsetup.usermanagement;
import com.app.server.repository.appbasicsetup.usermanagement.UserRepository;
import com.app.shared.appbasicsetup.usermanagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserBusinessService {

    @Autowired
    private UserRepository userRepository;

    public void update(User entity) throws Exception {
        if (entity.isHardDelete()) {
            userRepository.delete(entity.getUserId());
        } else {
            userRepository.deletePassRecovery(entity.getDeletedPassRecoveryList());
            userRepository.update(entity);
        }
    }

    public void update(List<User> entity) throws Exception {
        for (User _user : entity) {
            if (_user.isHardDelete()) {
                userRepository.delete(_user.getUserId());
            } else {
                userRepository.deletePassRecovery(_user.getDeletedPassRecoveryList());
                userRepository.update(_user);
            }
        }
    }
}
