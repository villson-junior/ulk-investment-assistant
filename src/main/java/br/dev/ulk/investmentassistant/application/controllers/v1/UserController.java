package br.dev.ulk.investmentassistant.application.controllers.v1;

import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.AccountResponseDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.CreateAccountDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.CreateUserDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.UpdateUserDTO;
import br.dev.ulk.investmentassistant.domain.models.Account;
import br.dev.ulk.investmentassistant.domain.models.User;
import br.dev.ulk.investmentassistant.infraestructure.services.UserService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(
        UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        UUID userID = userService.createUser(createUserDTO);
        return ResponseEntity.created(URI.create("v1/users/" + userID.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
        @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUser(userId, updateUserDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUsers(@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> deleteUsers(@PathVariable("userId") String userId,
        @RequestBody CreateAccountDTO createAccountDTO) {
        userService.createAccount(userId, createAccountDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listUserAccounts(@PathVariable("userId") String userId) {
        List<AccountResponseDTO> accountList = userService.listAccounts(userId);
        return ResponseEntity.ok(accountList);
    }


}