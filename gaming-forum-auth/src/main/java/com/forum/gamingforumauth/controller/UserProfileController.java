package com.forum.gamingforumauth.controller;

import com.forum.gamingforumauth.controller.base.GenericController;
import com.forum.gamingforumauth.dto.UserProfileDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/user-profile")
public class UserProfileController extends GenericController<UserProfileDTO> {
}
