package org.project.DbContext.Interfaces;

import org.project.Entities.Library;
import org.project.Entities.User;

public interface IUserRepo {

    boolean addNewUser(User user, String encryptedPassword);
    User getUser(String email,String password);
    Library isLibrary(String email,String password);
    public String countUsers();
    boolean updateUserFunds(String id, String addedFunds);
}
