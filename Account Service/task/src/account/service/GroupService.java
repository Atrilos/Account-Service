package account.service;

import account.model.Group;
import account.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static account.configuration.messages.AdminMessages.ROLE_NOT_FOUND_ERRORMSG;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Group getByName(String name) {
        return groupRepository
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROLE_NOT_FOUND_ERRORMSG));
    }
}
