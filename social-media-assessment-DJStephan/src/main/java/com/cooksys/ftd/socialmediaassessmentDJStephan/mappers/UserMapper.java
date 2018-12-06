package com.cooksys.ftd.socialmediaassessmentDJStephan.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.UserDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto userToUserDto(User user);

	UserDto[] usersToUserDtos(List<User> users);
	
	
	

}
