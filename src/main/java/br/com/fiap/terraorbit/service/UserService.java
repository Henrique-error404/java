package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.dto.request.UserNewRequest;
import br.com.fiap.terraorbit.entity.User;
import br.com.fiap.terraorbit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repository;

    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public User update(Long id, UserNewRequest request) {
        var user = repository.findById(id).orElseThrow();

        user.setName(request.name() != null ? request.name() : user.getName());
        user.setPasswordHash(request.password() != null ? request.password() : user.getPasswordHash());

        return user;
    }
}