package com.forum.gamingforumauth.service.userProfile;

import com.forum.gamingforumauth.dto.UserProfileDTO;
import com.forum.gamingforumauth.exception.UserProfileNotFoundException;
import com.forum.gamingforumauth.model.UserProfile;
import com.forum.gamingforumauth.repository.UserProfileRepository;
import com.forum.gamingforumauth.service.GenericService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements GenericService<UserProfileDTO> {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserProfileDTO> findAll() {
        List<UserProfile> userProfiles = (List<UserProfile>) userProfileRepository.findAll();
        List<UserProfileDTO> userProfileDTOList = userProfiles.stream()
                .map(usrProfile -> {
                    usrProfile.getUser().setPassword(null);
                    return modelMapper.map(usrProfile,UserProfileDTO.class);
                })
                .collect(Collectors.toList());
        return userProfileDTOList;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (hasRole('ADMIN') or #id == authentication.principal.id)")
    @Override
    public UserProfileDTO findById(Long id) {
        UserProfile userProfile = userProfileRepository.findByUserId(id).orElse(null);
        if(userProfile == null){
            throw new UserProfileNotFoundException();
        }
        userProfile.getUser().setPassword(null);
        return modelMapper.map(userProfile, UserProfileDTO.class);
    }

    /*
    Save (POST method) is irrelevant because when we create User
    he automatically gets new UserProfile created and attached to him.

    So there is no point in creating UserProfile object, because User cannot exist
    without already created UserProfile
     */
    @Override
    public UserProfileDTO save(UserProfileDTO entity) {
        return null;
    }
    /*
    Technically, update (PUT method) acts as a save (POST method),
    because when User gets created (Registers into the system) he gets all default values
    of UserProfile, and now we update (change/create) that default (null values) values.
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') and (hasRole('ADMIN') or #id == authentication.principal.id)")
    @Override
    public UserProfileDTO update(Long id, UserProfileDTO entity) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        UserProfile userProfile = userProfileRepository.findByUserId(id).orElse(null);
        if(userProfile == null){
            throw new UserProfileNotFoundException();
        }
        // Set ID to null, because end user can put in requestBody and that causes an Error
        entity.setId(null);
        // Set User to null, because we don't care about user sent from request
        entity.setUser(null);
        modelMapper.map(entity,userProfile);
        userProfileRepository.save(userProfile);
        userProfile.getUser().setPassword(null);
        return modelMapper.map(userProfile,UserProfileDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteById(Long id) {
        UserProfile userProfile = userProfileRepository.findByUserId(id).orElse(null);
        if(userProfile == null){
            throw new UserProfileNotFoundException();
        }
        userProfileRepository.delete(userProfile);
    }
}
