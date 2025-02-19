package br.dev.ulk.investmentassistant.infraestructure.services;

import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.AccountResponseDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.CreateAccountDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.CreateUserDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.UpdateUserDTO;
import br.dev.ulk.investmentassistant.domain.models.Account;
import br.dev.ulk.investmentassistant.domain.models.BillingAddress;
import br.dev.ulk.investmentassistant.domain.models.User;
import br.dev.ulk.investmentassistant.infraestructure.repositories.AccountRepository;
import br.dev.ulk.investmentassistant.infraestructure.repositories.BillingAddressRepository;
import br.dev.ulk.investmentassistant.infraestructure.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private BillingAddressRepository billingAddressRepository;

    public UserService(
        UserRepository userRepository,
        AccountRepository accountRepository,
        BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {

        User user = new User(
            createUserDTO.getUsername(),
            createUserDTO.getEmail(),
            createUserDTO.getPassword());

        User userSaved = userRepository.save(user);
        return userSaved.getId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUser(String userId, UpdateUserDTO updateUserDTO) {
        UUID id = UUID.fromString(userId);
        Optional<User> userDB = userRepository.findById(id);
        if (userDB.isPresent()) {
            User user = userDB.get();
            user.setUsername(updateUserDTO.getUsername());
            user.setPassword(updateUserDTO.getPassword());
            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        UUID id = UUID.fromString(userId);
        boolean userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {
        User user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Account account = Account.builder()
            .description(createAccountDTO.getDescription())
            .user(user)
            .build();

        Account accountDb = accountRepository.save(account);

        BillingAddress billingAddress = BillingAddress.builder()
            .account(account)
            .street(createAccountDTO.getStreet())
            .number(createAccountDTO.getNumber())
            .build();

        billingAddress.setId(accountDb.getId());

        billingAddressRepository.save(billingAddress);

    }

    public List<AccountResponseDTO> listAccounts(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
            .stream()
            .map(ac -> new AccountResponseDTO(ac.getId().toString(), ac.getDescription()))
            .toList();
    }
}