package br.dev.ulk.investmentassistant.infraestructure.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.CreateUserDTO;
import br.dev.ulk.investmentassistant.application.controllers.v1.payloads.UpdateUserDTO;
import br.dev.ulk.investmentassistant.domain.models.User;
import br.dev.ulk.investmentassistant.infraestructure.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a new user")
        void shouldCreateUser() {
            // arrange
            User user = new User(
                "username",
                "username@test.com",
                "password123"
            );
            user.setId(UUID.randomUUID());
            user.setCreatedAt(LocalDateTime.now());

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            CreateUserDTO createUserDTO = new CreateUserDTO(
                "username",
                "username@test.com",
                "password123");

            // act
            UUID userId = userService.createUser(createUserDTO);

            // assert
            assertNotNull(userId);

            User userCaptured = userArgumentCaptor.getValue();

            assertEquals(createUserDTO.getUsername(), userCaptured.getUsername());
            assertEquals(createUserDTO.getEmail(), userCaptured.getEmail());
            assertEquals(createUserDTO.getPassword(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            // arrange
            User user = new User(
                "username",
                "username@test.com",
                "password123"
            );
            user.setId(UUID.randomUUID());
            user.setCreatedAt(LocalDateTime.now());

            doThrow(new RuntimeException()).when(userRepository).save(any());

            CreateUserDTO createUserDTO = new CreateUserDTO(
                "username",
                "username@test.com",
                "password123");

            // act & assert
            assertThrows(RuntimeException.class, () -> userService.createUser(createUserDTO));
        }

    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {
            // arrange
            User user = new User(
                "username",
                "username@test.com",
                "password123"
            );
            user.setId(UUID.randomUUID());
            user.setCreatedAt(LocalDateTime.now());

            doReturn(Optional.of(user))
                .when(userRepository)
                .findById(uuidArgumentCaptor.capture());

            // act
            Optional<User> userOptional = userService.getUserById(user.getId().toString());

            // assert
            assertTrue(userOptional.isPresent());
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {
            // arrange
            UUID uuid = UUID.randomUUID();
            doReturn(Optional.empty())
                .when(userRepository)
                .findById(uuidArgumentCaptor.capture());

            // act
            Optional<User> userOptional = userService.getUserById(uuid.toString());

            // assert
            assertTrue(userOptional.isEmpty());
            assertEquals(uuid, uuidArgumentCaptor.getValue());
        }

    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {
            // arrange
            User user = new User(
                "username",
                "username@test.com",
                "password123"
            );
            user.setId(UUID.randomUUID());
            user.setCreatedAt(LocalDateTime.now());

            List<User> userTestList = List.of(user);

            doReturn(userTestList)
                .when(userRepository)
                .findAll();

            // act
            List<User> userList = userService.listUsers();

            // assert
            assertNotNull(userList);
            assertEquals(userTestList.size(), userList.size());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists() {

            // arrange
            doReturn(true)
                .when(userRepository)
                .existsById(uuidArgumentCaptor.capture());

            doNothing()
                .when(userRepository)
                .deleteById(uuidArgumentCaptor.capture());

            UUID userId = UUID.randomUUID();

            // act
            userService.deleteById(userId.toString());

            // assert
            List<UUID> uuidList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, uuidList.get(0));
            assertEquals(userId, uuidList.get(1));

            verify(userRepository, times(1)).existsById(uuidList.get(0));
            verify(userRepository, times(1)).deleteById(uuidList.get(1));

        }

        @Test
        @DisplayName("Should delete user with success when user NOT exists")
        void shouldDeleteUserWithSuccessWhenUserNotExists() {

            // arrange
            doReturn(false)
                .when(userRepository)
                .existsById(uuidArgumentCaptor.capture());

            UUID userId = UUID.randomUUID();

            // act
            userService.deleteById(userId.toString());

            // assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                .existsById(uuidArgumentCaptor.getValue());

            verify(userRepository, times(0))
                .deleteById(any());
        }

    }

    @Nested
    class updateUser {

        @Test
        @DisplayName("Should update user by id  when user exists and username and password is filled")
        void shouldUpdateUserByIdWhenUserExistsAndUsernameAndPasswordIsFilled() {
            // arrange
            UpdateUserDTO updateUserDTO = new UpdateUserDTO(
                "newUsername",
                "newPassword"
            );

            User user = new User(
                "username",
                "username@test.com",
                "password123"
            );
            user.setId(UUID.randomUUID());
            user.setCreatedAt(LocalDateTime.now());

            doReturn(Optional.of(user))
                .when(userRepository)
                .findById(uuidArgumentCaptor.capture());

            doReturn(user)
                .when(userRepository)
                .save(userArgumentCaptor.capture());

            // act
            userService.updateUser(user.getId().toString(), updateUserDTO);

            // assert
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());

            User userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getUsername(), userCaptured.getUsername());
            assertEquals(user.getPassword(), userCaptured.getPassword());

            verify(userRepository, times(1))
                .findById(uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                .save(user);
        }

    }
}