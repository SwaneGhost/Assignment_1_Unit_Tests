package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.*;;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.mockito.*;

public class TestLibrary {

    @Mock
    private DatabaseService databaseService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private Library library;

    private Book book;

    @Mock
    private Book mockBook;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        library = new Library(databaseService, reviewService);
        book = new Book("978-3-16-148410-0", "Diary of a whimpy Denis", "L. Shmilovich");
    }

    @Test
    public void GivenNull_WhenAddBook_ThenIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
    }

    @Test
    public void GivenBookISBNwithLetters_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0A");
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
    }

    @Test
    public void GivenBookISBNwithSpecialCharacters_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0@");
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
    }

    @Test
    public void GivenBookISBNincorrectCheckSum_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
    }

    @Test
    public void GivenBookISBNwrongLength_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-01");
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
    }


}
