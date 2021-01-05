package Controllers;

/**
 * Stores other controllers as a facade.
 *
 * @version 1.1
 */

public class UserAccountSystem {

    private Registrar registrar;
    private Messenger messenger;
    private TalkOrganizer talkOrganizer;
    private UserAccount userAccount;
    private ViewUserInformation userInfo;
    private UseCaseStorage ucs;
    private FeedbackController fbc;

    /**
     *  Initialize the other controllers using the provided useCaseStorage
     * @param useCaseStorage takes in a UseCaseStorage object and stores it
     */
    // create instances of the controllers
    public UserAccountSystem(UseCaseStorage useCaseStorage) {

        this.userAccount = new UserAccount(useCaseStorage);
        this.registrar = new Registrar(useCaseStorage);
        this.messenger = new Messenger(useCaseStorage);
        this.talkOrganizer = new TalkOrganizer(userAccount, useCaseStorage);
        this.userInfo = new ViewUserInformation(userAccount, useCaseStorage);
        this.fbc = new FeedbackControllerFactory().getFeedbackController();
        this.ucs = useCaseStorage;
    }

    /**
     * @param fbc the feedbackcontroller
     */

    public void setFeedbackController(FeedbackController fbc) {
        this.fbc = fbc;
    }
    /**
     * Gets the UserAccount for this conference
     * @return the UserAccount controller object
     */

    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    /**
     * Gets the UseCaseStorage for this conference
     * @return the UseCaseStorage object
     */
    public UseCaseStorage getUseCaseStorage() {
        return ucs;
    }

    /**
     * Gets the Registrar for this conference
     * @return the Registrar object
     */
    public Registrar getRegistrar() {
        return registrar;
    }

    /**
     * Gets the Messenger for this conference
     * @return the Messenger object controller
     */

    public Messenger getMessenger() {
        return messenger;
    }

    /**
     * Gets the TalkOrganizer for this conference
     * @return the TalkOrganizer object controller stored
     */
    public TalkOrganizer getTalkOrganizer() {
        return talkOrganizer;
    }

    /**
     * Gets the ViewUserInformation for this conference
     * @return the ViewUserInformation object stored.
     */
    public ViewUserInformation getUserInfo() {
        return this.userInfo;
    }

    /**
     * Gets the FeedbackController for this conference
     * @return the FeedbackController object stored
     */
    public FeedbackController getFeedbackController() {
        return this.fbc;
    }
}
