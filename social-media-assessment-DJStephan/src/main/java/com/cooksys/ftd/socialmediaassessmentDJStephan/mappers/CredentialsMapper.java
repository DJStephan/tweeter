package com.cooksys.ftd.socialmediaassessmentDJStephan.mappers;

import org.mapstruct.Mapper;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.CredentialsDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.Credentials;
@Mapper(componentModel = "spring")
public interface CredentialsMapper {

	Credentials credentialsDtoToCredentials(CredentialsDto credentialsDto);

}
