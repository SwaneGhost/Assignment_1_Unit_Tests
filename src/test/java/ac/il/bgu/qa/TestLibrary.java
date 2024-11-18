package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;

import org.mockito.*;

public class TestLibrary {

    //TODO: CHECK WITH THE INSTRUCTOR IF IT IS CORRECT TO CHECK SPECIFIC EXCEPTION MESSAGES IN OUR TESTS

    @Mock
    private DatabaseService databaseService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private Library library;

    @Mock
    private Book mockBook;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        library = new Library(databaseService, reviewService);
    }

    @Test
    public void GivenNull_WhenAddBook_ThenIllegalArgumentException() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
        Assertions.assertEquals("Invalid book.", exception.getMessage());
    }

    @Test
    public void GivenBookISBNnull_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn(null);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
    }

    @Test
    public void GivenBookISBNwithLetters_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0A");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
    }

    @Test
    public void GivenBookISBNwithSpecialCharacters_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0@");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
    }

    @Test
    public void GivenBookISBNincorrectCheckSum_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-1");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
    }

    @Test
    public void GivenBookISBNwrongLength_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-01");
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
    }

    @Nested
    class GivenBookTitleTests {
        @BeforeEach
        public void setUp() {
            Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0");
        }

        @Test
        public void GivenBookTitleNull_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getTitle()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid title.", exception.getMessage());
        }

        @Test
        public void GivenBookTitleEmpty_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getTitle()).thenReturn("");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid title.", exception.getMessage());
        }
    }

    @Nested
    class GivenBookAuthorTests {
        @BeforeEach
        public void setUp() {
            Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0");
            Mockito.when(mockBook.getTitle()).thenReturn("title");
        }

        @Test
        public void GivenBookAuthorNull_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getAuthor()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid author.", exception.getMessage());
        }

        @Test
        public void GivenBookAuthorEmpty_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getAuthor()).thenReturn("");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid author.", exception.getMessage());
        }

        @Test
        public void GivenBookAuthorNameStartsWithAlphabeticCharacter_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getAuthor()).thenReturn("1author");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid author.", exception.getMessage());
        }

        @Test
        public void GivenBookAuthorNameEndsWithAlphabeticCharacter_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getAuthor()).thenReturn("author1");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid author.", exception.getMessage());
        }

        @Test
        public void GivenBookAuthorNameContainsSpecialCharacters_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getAuthor()).thenReturn("author@");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid author.", exception.getMessage());
        }

        @Test
        public void GivenBookAuthorNameConsecutiveBackslashes_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getAuthor()).thenReturn("author\\");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid author.", exception.getMessage());
        }
    }

    @Test
    public void GivenBookIsBorrowed_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0");
        Mockito.when(mockBook.getTitle()).thenReturn("title");
        Mockito.when(mockBook.getAuthor()).thenReturn("author");
        Mockito.when(mockBook.isBorrowed()).thenReturn(true);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        Assertions.assertEquals("Book with invalid borrowed state.", exception.getMessage());
    }

    @Test
    public void GivenBookAlreadyExistInDataBase_WhenAddBook_ThenIllegalArgumentException() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0");
        Mockito.when(mockBook.getTitle()).thenReturn("title");
        Mockito.when(mockBook.getAuthor()).thenReturn("author");
        Mockito.when(mockBook.isBorrowed()).thenReturn(false);
        Mockito.when(databaseService.getBookByISBN(mockBook.getISBN())).thenReturn(mockBook);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        Assertions.assertEquals("Book already exists.", exception.getMessage());
    }

    @Test
    public void GivenBookCorrectly_WhenAddBook_ThenBookAdded() {
        Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0");
        Mockito.when(mockBook.getTitle()).thenReturn("title");
        Mockito.when(mockBook.getAuthor()).thenReturn("author");
        Mockito.when(mockBook.isBorrowed()).thenReturn(false);
        Mockito.when(databaseService.getBookByISBN(mockBook.getISBN())).thenReturn(null);
        library.addBook(mockBook);
        Mockito.verify(databaseService).addBook(mockBook.getISBN(), mockBook);
    }


    //TODO: TEST registerUser METHOD

    //TODO: TEST borrowBook METHOD

    //TODO: TEST returnBook METHOD

    //TODO: TEST notifyUserWithBookReviews METHOD

    //TODO: TEST getBookByISBN METHOD

}
