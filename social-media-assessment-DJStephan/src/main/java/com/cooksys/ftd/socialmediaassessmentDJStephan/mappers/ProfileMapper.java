package com.cooksys.ftd.socialmediaassessmentDJStephan.mappers;

import org.mapstruct.Mapper;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.ProfileDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Profile;
@Mapper(componentModel = "spring")
public interface ProfileMapper {

	Profile profileDtoToProfile(ProfileDto profileDto);

}
