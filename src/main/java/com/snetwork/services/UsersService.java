package com.snetwork.services;

import com.snetwork.entities.Request;
import com.snetwork.entities.User;
import com.snetwork.repositories.RequestsRepository;
import com.snetwork.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Obtiene todos los usuario de la aplicacion menos el logeado
     * @param pageable
     * @param id el id del usuario logeado
     * @return
     */
    public Page<User> getAllMinusTarget(Pageable pageable, Long id) {
        return usersRepository.findAllMinusTarget(pageable, id);
    }

    /**
     * Obtiene el resto de usuarios registrados en la aplicacion
     * @param pageable la paginacion
     * @param user el usuario logeado
     * @return el resto de usuarios
     */
    public Page<User> getOthersUsers(Pageable pageable, User user) {
        List<Request> friendRequests = getFriendsRequest(user.getId());
        Page<User> users = getAllMinusTarget(pageable, user.getId());
        boolean sendedRequest = false;
        for (User item : users) {
            for (Request request : friendRequests) {
                if (request.getSender().getId() == user.getId() && request.getReceiver().getId() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(User.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(User.SENDED_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
                else if (request.getReceiver().getId() == user.getId() && request.getSender().getId() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(User.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(User.ACCEPT_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
            }
            if (!sendedRequest) item.setStatus(User.SEND_FRIEND_REQUEST);
            sendedRequest = false;
        }
        return users;
    }

    /**
     * Obtiene una lista de amigos paginada en base al id del usuario logeado y el texto
     * de búsqueda que ha introducido el usuario
     * @param pageable la paginación
     * @param text el texto en el que se basa la búsqueda
     * @param id el usuario logeado
     * @return los usuarios que coinciden con la búsqueda
     */
    public Page<User> getFriendBySearch(Pageable pageable, String text, Long id) {
        List<Request> friendRequests = getFriendsRequest(id);
        Page<User> users = usersRepository.searchUser(pageable, text, id);
        boolean sendedRequest = false;
        for (User item : users) {
            for (Request request : friendRequests) {
                if (request.getSender().getId() == id && request.getReceiver().getId() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(User.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(User.SENDED_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
                else if (request.getReceiver().getId() == id && request.getSender().getId() == item.getId()) {
                    if (request.isAccepted()) {
                        item.setStatus(User.FRIENDS);
                        sendedRequest = true;
                    }
                    else {
                        item.setStatus(User.ACCEPT_FRIEND_REQUEST);
                        sendedRequest = true;
                    }
                }
            }
            if (!sendedRequest) item.setStatus(User.SEND_FRIEND_REQUEST);
            sendedRequest = false;
        }
        return users;
    }

    public Page<User> getFriends(Pageable pageable, Long id) {
        return usersRepository.findFriends(pageable, id);
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    /**
     * Obtiene el usuario dado su email
     * @param email el email del usuario
     * @return el usuario
     */
    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    /**
     * Obtiene las peticiones de amistad recibidas
     * @param id el id del usuario que recibe las peticiones
     * @return las peticiones
     */
    private List<Request> getFriendsRequest(Long id) {
        return requestsRepository.findByUserId(id);
    }



    /**
     * This method has the logic of the button
     * @param friend The friend selected in the table
     * @param principal The user logged
     */
    public void buttonAction(User friend, Principal principal) {
        if (friend.getStatus().equals(User.SEND_FRIEND_REQUEST)) {
            User user = getUserByEmail(principal.getName());
            requestsService.addFriendsQuest(new Request(user, friend, false));
        }
        else if (friend.getStatus().equals(User.SENDED_FRIEND_REQUEST)) logger.info("Peticion ya enviada");
        else if (friend.getStatus().equals(User.ACCEPT_FRIEND_REQUEST)) {
            User user = getUserByEmail(principal.getName());
            requestsService.acceptFriendRequest(friend.getId(), user.getId());
        }
        else if (friend.getStatus().equals(User.FRIENDS)) System.out.println("You're already friends!");;
    }


    /**
     * Retrieves the friend object from the id
     * @param id the friend id
     * @return the friend
     */
    public User getFriendRequest(Long id, Page<User> friends) {
        for (User friend : friends) {
            if (friend.getId() == id) return friend;
        }
        throw new NullPointerException("Amigo no seleccionado");
    }

    public void deleteUser(Long id) {
        usersRepository.deletePublications(id);
        usersRepository.deleteRequests(id);
        usersRepository.deleteById(id);
    }
}
