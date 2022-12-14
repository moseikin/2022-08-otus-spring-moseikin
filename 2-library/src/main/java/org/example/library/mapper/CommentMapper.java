package org.example.library.mapper;

import org.example.library.domain.Comment;
import org.example.library.dto.response.CommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommentMapper extends GenericMapper<Comment, CommentResponseDto> {

    @Override
    @Mapping(source = "id", target = "commentId")
    CommentResponseDto toResponseDto(Comment entity);
}
