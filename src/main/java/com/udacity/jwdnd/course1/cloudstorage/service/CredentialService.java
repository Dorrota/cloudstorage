package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper mapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper mapper, EncryptionService encryptionService) {
        this.mapper = mapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials(Integer userId){
        List<Credential> credentials = mapper.getCredentials(userId);
        for (Credential credential: credentials) {
            credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return credentials;
    }

    public void saveCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String password = credential.getPassword();
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        mapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUserName(), encodedKey, encryptedPassword, credential.getUserId()));
    }

    public void updateCredentials(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String password = credential.getPassword();
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        mapper.updateCredentials(credential);
    }

    public void deleteCredential(Integer credentialId){
        mapper.deleteCredential(credentialId);
    }
}
