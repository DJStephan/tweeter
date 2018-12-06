package com.cooksys.ftd.socialmediaassessmentDJStephan.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.ftd.socialmediaassessmentDJStephan.dtos.HashTagDto;
import com.cooksys.ftd.socialmediaassessmentDJStephan.entities.HashTag;

@Mapper(componentModel = "spring")
public interface HashTagMapper {

	HashTagDto[] hashTagsToHashTagDtos(List<HashTag> hashTags);

}
