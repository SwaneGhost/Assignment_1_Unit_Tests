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

    @Nested
    class registerUserTests {

        @Mock
        User user;

        @BeforeEach
        public void setUp() {
            user = Mockito.mock(User.class);
            Mockito.when(user.getId()).thenReturn("123456789876");
            Mockito.when(user.getName()).thenReturn("name");
            Mockito.when(user.getNotificationService()).thenReturn(Mockito.mock(NotificationService.class));
        }

        @Test
        public void GivenNullUser_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(null));
            Assertions.assertEquals("Invalid user.", exception.getMessage());
        }

        @Test
        public void GivenUserWithNullId_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(user.getId()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
        }

        @Test
        public void GivenUserWithIncorrectIdLength_WhenRegisterUser_ThenRaiseIllegalArgumentException() {

            // Length 11
            Mockito.when(user.getId()).thenReturn("12345678987");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());

            // Length 13
            Mockito.when(user.getId()).thenReturn("1234567898765");
            exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
        }

        @Test
        public void GivenUserWithIncorrectIdFormat_WhenRegisterUser_ThenRaiseIllegalArgumentException() {

            // Contains a letter
            Mockito.when(user.getId()).thenReturn("12345678bBaA");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());

            // Contains a special character
            Mockito.when(user.getId()).thenReturn("1234567898>?!@");
            exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
        }

        @Test
        public void GivenUserWithNullName_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(user.getName()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid user name.", exception.getMessage());
        }

        @Test
        public void GivenUserWithEmptyName_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(user.getName()).thenReturn("");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid user name.", exception.getMessage());
        }

        @Test
        public void GivenUserWithNullNotificationService_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(user.getNotificationService()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("Invalid notification service.", exception.getMessage());
        }

        @Test
        public void GivenExistingUser_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(user.getId()).thenReturn("123456789876");
            Mockito.when(databaseService.getUserById(user.getId())).thenReturn(user);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(user));
            Assertions.assertEquals("User already exists.", exception.getMessage());
        }

    }
        //TODO: TEST borrowBook METHOD

        //TODO: TEST returnBook METHOD

        //TODO: TEST notifyUserWithBookReviews METHOD

        //TODO: TEST getBookByISBN METHOD


}
