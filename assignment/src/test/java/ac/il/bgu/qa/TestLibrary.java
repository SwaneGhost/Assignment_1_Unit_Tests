package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.mockito.*;

import static org.mockito.Mockito.mock;

// Test Naming Convention: Given<Condition>_When<Method>_Then<Result>
// Using JAVA - 19.0.2 by OpenJDK

// TODO coverage
// TODO use mock,spy and look for correct uses for isolations

public class TestLibrary {

    @Mock
    private DatabaseService databaseService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private Library library;

    private Book book;

    @BeforeEach
    public void setUp() {
        library = new Library(databaseService, reviewService);
        book = new Book("978-3-16-148410-0", "The Catcher in the Rye", "J.D. Salinger");
    }

    @Test
    public void GivenNull_WhenAddBook_ThenIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
    }
}