package org.example.library.shell;

import lombok.RequiredArgsConstructor;
import org.example.library.dto.request.BookRequestDto;
import org.example.library.dto.response.BookResponseDto;
import org.example.library.service.BookService;
import org.example.library.service.InfoService;
import org.example.library.service.PrintService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Создание книги: ib -n <"Название книги"> -a <id автора> -g <id жанра>
 * Получение книги по id: gb <id>
 * Обновление книги: ub -i <id> -n <"Название книги"> -a <id автора> -g <id жанра>
 * Удаление книги: db <id>
 * Получение всех книг: ab
 */
@ShellComponent
@RequiredArgsConstructor
public class BookShellCommands {

    private static final String CREATED_BOOK_ID = "id созданной книги: ";
    private static final String UPDATED_BOOK_ID = "id обновленной книги: ";

    private final BookService bookService;
    private final InfoService infoService;
    private final PrintService printService;

    @ShellMethod(value = "Insert book", key = {"ib", "insert-book"})
    public void insertBook(@ShellOption(value = "-n") String bookName,
                           @ShellOption("-a") long authorId, @ShellOption("-g") long genreId) {

        BookResponseDto createdBookResponseDto = bookService.createBook(new BookRequestDto(null, bookName, authorId, genreId));

        String bookInfo = infoService.getBookInfo(createdBookResponseDto);

        printService.print(CREATED_BOOK_ID + bookInfo);
    }

    @ShellMethod(value = "Get book", key = {"gb", "get-book"})
    public void getBook(Long bookId) {

        BookResponseDto bookResponseDto = bookService.getBook(bookId);

        String bookInfo = infoService.getBookInfo(bookResponseDto);

        printService.print(bookInfo);
    }

    @ShellMethod(value = "Update book", key = {"ub", "update-book"})
    public void updateBook(@ShellOption(value = "-i") Long bookId, @ShellOption(value = "-n") String bookName,
                           @ShellOption("-a") Long authorId, @ShellOption("-g") Long genreId) {

        BookResponseDto updatedBookResponseDto = bookService.updateBook(new BookRequestDto(bookId, bookName, authorId, genreId));

        String bookInfo = infoService.getBookInfo(updatedBookResponseDto);

        printService.print(UPDATED_BOOK_ID + bookInfo);
    }

    @ShellMethod(value = "Delete book", key = {"db", "delete-book"})
    public void deleteBook(Long bookId) {
        bookService.deleteBook(bookId);
    }

    @ShellMethod(value = "Get all books", key = {"ab", "all-books"})
    public void getAllBooks() {

        List<BookResponseDto> bookResponseDtos = bookService.getAllBooks();

        List<String> bookInfos = bookResponseDtos.stream().map(infoService::getBookInfo).collect(Collectors.toList());

        printService.printList(bookInfos);
    }
}
