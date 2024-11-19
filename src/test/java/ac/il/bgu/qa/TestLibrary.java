package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.*;
import org.junit.jupiter.api.*;

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

    @Mock
    private User mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        library = new Library(databaseService, reviewService);
    }

    /**
     * Tests for the addBook method.
     */
    @Nested
    class AddBookTests {
        @Test
        public void GivenNull_WhenAddBook_ThenIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
            Assertions.assertEquals("Invalid book.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
        }

        @Test
        public void GivenBookISBNnull_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getISBN()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
        }

        @Test
        public void GivenBookISBNwithLetters_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0A");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
        }

        @Test
        public void GivenBookISBNwithSpecialCharacters_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-0@");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
        }

        @Test
        public void GivenBookISBNincorrectCheckSum_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-1");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
        }

        @Test
        public void GivenBookISBNwrongLength_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-148410-01");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
        }

        @Test void GivenBookISBNwithNonDigits_WhenAddBook_ThenIllegalArgumentException() {
            Mockito.when(mockBook.getISBN()).thenReturn("978-3-16-184A10-0");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
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
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
            }

            @Test
            public void GivenBookTitleEmpty_WhenAddBook_ThenIllegalArgumentException() {
                Mockito.when(mockBook.getTitle()).thenReturn("");
                IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
                Assertions.assertEquals("Invalid title.", exception.getMessage());
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
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
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
            }

            @Test
            public void GivenBookAuthorEmpty_WhenAddBook_ThenIllegalArgumentException() {
                Mockito.when(mockBook.getAuthor()).thenReturn("");
                IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
                Assertions.assertEquals("Invalid author.", exception.getMessage());
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
            }

            @Test
            public void GivenBookAuthorNameStartsWithAlphabeticCharacter_WhenAddBook_ThenIllegalArgumentException() {
                Mockito.when(mockBook.getAuthor()).thenReturn("1author");
                IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
                Assertions.assertEquals("Invalid author.", exception.getMessage());
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
            }

            @Test
            public void GivenBookAuthorNameEndsWithAlphabeticCharacter_WhenAddBook_ThenIllegalArgumentException() {
                Mockito.when(mockBook.getAuthor()).thenReturn("author1");
                IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
                Assertions.assertEquals("Invalid author.", exception.getMessage());
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
            }

            @Test
            public void GivenBookAuthorNameContainsIllegalCharacters_WhenAddBook_ThenIllegalArgumentException() {
                Mockito.when(mockBook.getAuthor()).thenReturn("autho@r");
                IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
                Assertions.assertEquals("Invalid author.", exception.getMessage());
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
            }

            @Test
            public void GivenBookAuthorNameConsecutiveSpecialChar_WhenAddBook_ThenIllegalArgumentException() {
                Mockito.when(mockBook.getAuthor()).thenReturn("author--ms");
                IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
                Assertions.assertEquals("Invalid author.", exception.getMessage());
                Mockito.when(mockBook.getAuthor()).thenReturn("author" +'\'' + '\'' + "ms");
                exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
                Assertions.assertEquals("Invalid author.", exception.getMessage());
                Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
            }

            //test for successfully adding a book
            @Test
            public void GivenCorrectBook_WhenAddBook_ThenBookAdded() {
                Mockito.when(mockBook.getAuthor()).thenReturn("author");
                Mockito.when(mockBook.isBorrowed()).thenReturn(false);
                library.addBook(mockBook);
                Mockito.verify(databaseService).addBook(mockBook.getISBN(), mockBook);
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
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
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
            Mockito.verify(databaseService, Mockito.never()).addBook(Mockito.anyString(), Mockito.any(Book.class));
        }
    }


    /**
     * Tests for the registerUser method.
     */
    @Nested
    class registerUserTests {

        @BeforeEach
        public void setUp() {
            mockUser = Mockito.mock(User.class);
            // Valid user
            Mockito.when(mockUser.getId()).thenReturn("123456789876");
            Mockito.when(mockUser.getName()).thenReturn("name");
            Mockito.when(mockUser.getNotificationService()).thenReturn(Mockito.mock(NotificationService.class));
        }


        @Test
        public void GivenNullUser_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(null));
            Assertions.assertEquals("Invalid user.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }


        @Test
        public void GivenUserWithNullId_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(mockUser.getId()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }

        @Test
        public void GivenUserWithIncorrectIdLength_WhenRegisterUser_ThenRaiseIllegalArgumentException() {

            // Length 11
            Mockito.when(mockUser.getId()).thenReturn("12345678987");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));

            // Length 13
            Mockito.when(mockUser.getId()).thenReturn("1234567898765");
            exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }

        @Test
        public void GivenUserWithIncorrectIdFormat_WhenRegisterUser_ThenRaiseIllegalArgumentException() {

            // Contains a letter
            Mockito.when(mockUser.getId()).thenReturn("12345678bBaA");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));

            // Contains a special character
            Mockito.when(mockUser.getId()).thenReturn("1234567898>?!@");
            exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }

        @Test
        public void GivenUserWithNullName_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(mockUser.getName()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid user name.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }

        @Test
        public void GivenUserWithEmptyName_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(mockUser.getName()).thenReturn("");
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid user name.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }

        @Test
        public void GivenUserWithNullNotificationService_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(mockUser.getNotificationService()).thenReturn(null);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("Invalid notification service.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }

        @Test
        public void GivenExistingUser_WhenRegisterUser_ThenRaiseIllegalArgumentException() {
            Mockito.when(mockUser.getId()).thenReturn("123456789876");
            Mockito.when(databaseService.getUserById(mockUser.getId())).thenReturn(mockUser);
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.registerUser(mockUser));
            Assertions.assertEquals("User already exists.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).registerUser(Mockito.anyString(), Mockito.any(User.class));
        }

        @Test
        public void GivenCorrectUser_WhenRegisterUser_ThenUserRegistered() {
            Mockito.when(mockUser.getId()).thenReturn("123456789876");
            Mockito.when(databaseService.getUserById(mockUser.getId())).thenReturn(null);
            library.registerUser(mockUser);
            Mockito.verify(databaseService).registerUser(mockUser.getId(), mockUser);
        }
    }

    /**
     * Tests for the borrowBook method.
     */

    @Nested
    class borrowBookTests {

        @BeforeEach
        public void setUp() {
            Mockito.when(databaseService.getBookByISBN("978-3-16-148410-0")).thenReturn(mockBook);
            Mockito.when(databaseService.getUserById("user")).thenReturn(mockUser);
            Mockito.when(mockBook.isBorrowed()).thenReturn(false);
        }

        @Test
        public void GivenNullISBN_WhenBorrowBook_ThenIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.borrowBook(null, "user"));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        }

        @Test
        public void GivenNonExistentBook_WhenBorrowBook_ThenBookNotFoundException() {
            Mockito.when(databaseService.getBookByISBN("978-3-16-148410-0")).thenReturn(null);
            BookNotFoundException exception = Assertions.assertThrows(BookNotFoundException.class, () -> library.borrowBook("978-3-16-148410-0", "user"));
            Assertions.assertEquals("Book not found!", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        }

        @Test
        public void GivenNullUser_WhenBorrowBook_ThenIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.borrowBook("978-3-16-148410-0", null));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        }

        @Test
        public void GivenNullUserId_WhenBorrowBook_ThenIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.borrowBook("978-3-16-148410-0", "user1"));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        }

        @Test
        public void GivenNot12DigitUserId_WhenBorrowBook_ThenIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.borrowBook("978-3-16-148410-0", "user"));
            Assertions.assertEquals("Invalid user Id.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        }

        @Test
        public void GivenUnregisteredUser_WhenBorrowBook_ThenUserNotFoundException() {
            Mockito.when(databaseService.getUserById("123456789123")).thenReturn(null);
            UserNotRegisteredException exception = Assertions.assertThrows(UserNotRegisteredException.class, () -> library.borrowBook("978-3-16-148410-0", "123456789123"));
            Assertions.assertEquals("User not found!", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        }

        @Test
        public void GivenUserAlreadyBorrowedBook_WhenBorrowBook_ThenBookAlreadyBorrowedException() {
            Mockito.when(mockBook.isBorrowed()).thenReturn(true);
            Mockito.when(databaseService.getUserById("123456789123")).thenReturn(mockUser);
            BookAlreadyBorrowedException exception = Assertions.assertThrows(BookAlreadyBorrowedException.class, () -> library.borrowBook("978-3-16-148410-0", "123456789123"));
            Assertions.assertEquals("Book is already borrowed!", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).borrowBook(Mockito.anyString(), Mockito.anyString());
        }

        @Test
        public void GivenUserTriedToBorrowBookAgain_WhenBorrowBook_ThenBookAlreadyBorrowedException() {
            Mockito.when(mockBook.isBorrowed()).thenReturn(false);
            Mockito.when(databaseService.getUserById("123456789123")).thenReturn(mockUser);
            library.borrowBook("978-3-16-148410-0", "123456789123");
            Mockito.when(mockBook.isBorrowed()).thenReturn(true);
            BookAlreadyBorrowedException exception = Assertions.assertThrows(BookAlreadyBorrowedException.class, () -> library.borrowBook("978-3-16-148410-0", "123456789123"));
            Assertions.assertEquals("Book is already borrowed!", exception.getMessage());
            Mockito.verify(databaseService, Mockito.times(1)).borrowBook(Mockito.anyString(), Mockito.anyString());        }
    }

    /**
     * Tests for the returnBook method.
     */
    @Nested
    class returnBookTests {

        @BeforeEach
        public void setUp() {
            Mockito.when(databaseService.getBookByISBN("978-3-16-148410-0")).thenReturn(mockBook);
            Mockito.when(mockBook.isBorrowed()).thenReturn(true);
        }

        @Test
        public void GivenNullISBN_WhenReturnBook_ThenIllegalArgumentException() {
            IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> library.returnBook(null));
            Assertions.assertEquals("Invalid ISBN.", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).returnBook(Mockito.anyString());
        }

        @Test
        public void GivenNonExistentBook_WhenReturnBook_ThenBookNotFoundException() {
            Mockito.when(databaseService.getBookByISBN("978-3-16-148410-0")).thenReturn(null);
            BookNotFoundException exception = Assertions.assertThrows(BookNotFoundException.class, () -> library.returnBook("978-3-16-148410-0"));
            Assertions.assertEquals("Book not found!", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).returnBook(Mockito.anyString());
        }

        @Test
        public void GivenBookNotBorrowed_WhenReturnBook_ThenBookNotBorrowedException() {
            Mockito.when(mockBook.isBorrowed()).thenReturn(false);
            BookNotBorrowedException exception = Assertions.assertThrows(BookNotBorrowedException.class, () -> library.returnBook("978-3-16-148410-0"));
            Assertions.assertEquals("Book wasn't borrowed!", exception.getMessage());
            Mockito.verify(databaseService, Mockito.never()).returnBook(Mockito.anyString());
        }

        @Test
        public void GivenCorrectBook_WhenReturnBook_ThenBookReturned() {
            Mockito.when(mockBook.isBorrowed()).thenReturn(true);
            library.returnBook("978-3-16-148410-0");
            Mockito.verify(databaseService).returnBook("978-3-16-148410-0");
        }
    }

        //TODO: TEST notifyUserWithBookReviews METHOD

        //TODO: TEST getBookByISBN METHOD -- Denis


}
