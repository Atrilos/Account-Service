package account.repository.loader;

import account.model.Group;
import account.model.constant.Role;
import account.repository.GroupRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class DataLoader {
    private final GroupRepository groupRepository;

    public DataLoader(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
        createRoles();
    }

    private void createRoles() {
        Collection<String> allPresentedRoles = groupRepository.findAll().stream().map(Group::getName).toList();
        long numberOfChanges = Arrays.stream(Role.values())
                .filter(r -> !allPresentedRoles.contains(r.toString()))
                .map(r -> groupRepository.save(new Group(r)))
                .count();
    }
}
