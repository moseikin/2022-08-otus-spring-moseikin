package org.example.library.shell;

import lombok.RequiredArgsConstructor;
import org.example.library.dto.request.CommentRequestDto;
import org.example.library.dto.response.CommentResponseDto;
import org.example.library.service.CommentService;
import org.example.library.service.InfoService;
import org.example.library.service.PrintService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Создание комментария: cc -b <id книги> -c <"Комментарий">
 * Получение комментария по id: gc <id>
 * Обновление комментария: uc -i <id комментария> -b <id книги> -c <"Комментарий">
 * Удаление комментария: dc <id>
 * Получение всех комментариев у книги: ac -b <id книги>
 */
@ShellComponent
@RequiredArgsConstructor
public class CommentShellCommands {

    private static final String CREATED_COMMENT = "Добавлен комментарий: ";
    private static final String UPDATED_COMMENT = "Обновленный комментарий: ";

    private final CommentService commentService;
    private final InfoService infoService;
    private final PrintService printService;

    @ShellMethod(value = "Create comment", key = {"cc", "create-comment"})
    public void createComment(@ShellOption(value = "-b") long bookId,
                              @ShellOption("-c") String content) {

        CommentResponseDto createdComment = commentService.createComment(new CommentRequestDto(null, bookId, content));

        String bookInfo = infoService.getCommentInfo(createdComment);

        printService.print(CREATED_COMMENT + bookInfo);
    }

    @ShellMethod(value = "Get comment", key = {"gc", "get-comment"})
    public void getComment(Long commentId) {

        CommentResponseDto commentResponseDto = commentService.getComment(commentId);

        String commentInfo = infoService.getCommentInfo(commentResponseDto);

        printService.print(commentInfo);
    }

    @ShellMethod(value = "Update comment", key = {"uc", "update-comment"})
    public void updateComment(@ShellOption(value = "-i") Long commentId, @ShellOption("-b") Long bookId,
                              @ShellOption("-c") String content) {

        CommentResponseDto updatedComment = commentService.updateComment(new CommentRequestDto(commentId, bookId, content));

        String commentInfo = infoService.getCommentInfo(updatedComment);

        printService.print(UPDATED_COMMENT + commentInfo);
    }

    @ShellMethod(value = "Delete comment", key = {"dc", "delete-comment"})
    public void deleteComment(Long commentId) {

        commentService.deleteComment(commentId);
    }


    @ShellMethod(value = "Get all comments", key = {"ac", "all-comments"})
    public void getAllComments(@ShellOption(value = "-b") Long bookId) {

        List<CommentResponseDto> commentResponseDtos = commentService.getAllCommentsByBookId(bookId);

        List<String> comments = commentResponseDtos.stream()
                .map(infoService::getCommentInfo)
                .collect(Collectors.toList());

        printService.printList(comments);
    }
}
