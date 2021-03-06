package com.snetwork.services;

import com.snetwork.entities.Publication;
import com.snetwork.entities.User;
import com.snetwork.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    public void addPublication(Publication publication) {
        publicationRepository.save(publication);
    }

    public Page<Publication> getUserPublications(Pageable pageable, Long id) {
        return publicationRepository.findByCreator(pageable, id);
    }

    public Page<Publication> getFriendsPublications(Pageable pageable, User user) {
        return publicationRepository.findByFriend(pageable, user);
    }

    /**
     * Guarda la foto dentro de una carpeta dentro del proyecto
     * @param publication la nueva publicacion del usuario conectado
     * @throws IOException
     */
    public void savePhoto(@ModelAttribute Publication publication) throws IOException {
//        String rootPath = System.getProperty("user.home");
        File dir = new File("C:\\Users\\ERIK\\Documents\\University\\SDI\\Spring_social_network\\src\\main\\resources\\static\\img" + File.separator + "photos");
        if (!dir.exists())
            dir.mkdirs();
        File photo = new File(dir.getAbsolutePath()
                + File.separator + publication.getUser().getName() + "_" + publication.getDate().getTime() + ".png");
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(photo));
        stream.write(publication.getPhoto().getBytes());
        stream.close();
        publication.setPhotoPath(photo.getAbsolutePath());
    }
}
