package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.dto.request.UserNewRequest;
import br.com.fiap.terraorbit.entity.User;
import br.com.fiap.terraorbit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repository;
    private final PasswordEncoder encoder;

    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    public void delete(Long id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatusCode.valueOf(404)
                            );
                        }
                );
    }

    public User update(Long id, UserNewRequest request) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        user.setName(request.name() != null ? request.name() : user.getName());
        user.setPasswordHash(request.password() != null ? encoder.encode(request.password()) : user.getPasswordHash());

        return user;
    }
}