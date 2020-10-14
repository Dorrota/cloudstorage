package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String addOrUpdateCredentials(@ModelAttribute Credential credential, Authentication authentication){
        if (credential.getCredentialId() == null){
            credential.setUserId(userService.getUser(authentication.getName()).getUserId());
            credentialService.saveCredential(credential);
        } else {
            credentialService.updateCredentials(credential);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId){
        credentialService.deleteCredential(credentialId);
        return "redirect:/home";
    }
}
