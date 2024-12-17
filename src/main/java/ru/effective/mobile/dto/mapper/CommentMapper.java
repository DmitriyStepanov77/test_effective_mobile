package ru.effective.mobile.dto.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effective.mobile.dto.comment.CommentDto;
import ru.effective.mobile.dto.comment.NewCommentDto;
import ru.effective.mobile.model.Comment;
import ru.effective.mobile.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "task", ignore = true)
    public Comment toEntity(NewCommentDto newCommentDto);

    public CommentDto toModel(Comment comment);

    default String map (User user) {
       return user.getUsername();
    }

}
