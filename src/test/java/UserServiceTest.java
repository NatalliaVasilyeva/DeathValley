import by.java.entity.Account;
import by.java.service.AccountService;
import by.java.service.ServiceFactory;
import by.java.service.exception.ServiceException;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class UserServiceTest {


    private AccountService accountService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Initial setup...");
        System.out.println("Code executes only once");
    }

    @Before
    public void setUp() {

        accountService = ServiceFactory.getInstance().getAccountService();
    }

    @Test
    public void whenReturnAccountWithId() throws ServiceException {
        //expected
        Account account = new Account.AccountBuilder().withId(1).withAccount(50).withUser(1).build();
        //real
        Account accountFromDb = accountService.findAccountById(1).get();
        //result
        Assert.assertEquals(account, accountFromDb);
    }

    @Test
    public void whenReturnUserWithId_false() throws ServiceException {
        //expected
        Account account = new Account.AccountBuilder().withId(1).withAccount(50).withUser(2).build();
        //real
        Account accountFromDb = accountService.findAccountById(1).get();
        //result
        Assert.assertNotEquals(account, accountFromDb);
    }


    @AfterClass
    public static void tearDown() {
        System.out.println("Tests finished");
    }

    @After
    public void afterMethod() {
        System.out.println("Code executes after each test method");
    }

}
