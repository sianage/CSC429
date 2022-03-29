// specify the package
package userinterface;

// system imports

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// project imports
import impresario.IModel;

/**
 * The class containing the BalanceInquiry Receipt  for the ATM application
 */
//==============================================================
public class BalanceInquiryReceipt extends View {

    // Model
    private final String todaysDateAndTimeString;

    // GUI controls
    private Text accountNumber;
    private Text todaysDateAndTime;
    private Text currentBalance;

    private Button okButton;

    // constructor for this class
    //----------------------------------------------------------
    public BalanceInquiryReceipt(IModel trans) {
        super(trans, "BalanceInquiryReceipt");

        Calendar todaysCalendar = Calendar.getInstance();    // creation date and time
        Date todaysDateAndTime = todaysCalendar.getTime();

        DateFormat theFormatter = DateFormat.getDateTimeInstance();
        todaysDateAndTimeString = theFormatter.format(todaysDateAndTime);

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        getChildren().add(container);

        populateFields();


    }

    // Create the Node (HBox) for the title
    //-------------------------------------------------------------
    private Node createTitle() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Bank ATM ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text accountLabel = new Text("Account Number : ");
        accountLabel.setWrappingWidth(150);
        accountLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(accountLabel, 0, 0);

        accountNumber = new Text("                       ");
        grid.add(accountNumber, 1, 0);

        Text dateAndTimeLabel = new Text("Date/Time : ");
        dateAndTimeLabel.setWrappingWidth(150);
        dateAndTimeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateAndTimeLabel, 0, 1);

        todaysDateAndTime = new Text("                       ");
        grid.add(todaysDateAndTime, 1, 1);

        Text currentBalanceLabel = new Text("Current Balance  : ");
        currentBalanceLabel.setWrappingWidth(150);
        currentBalanceLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(currentBalanceLabel, 0, 2);

        currentBalance = new Text("                       ");
        grid.add(currentBalance, 1, 2);

        okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * The ultimate result of this action is that the transaction will tell the teller to
                 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
                 * It simply tells its src.model (controller) that the balance was seen, and leaves it
                 * to the src.model to decide to tell the teller to do the switch back.
                 */
                //----------------------------------------------------------
                myModel.stateChangeRequest("OK", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(okButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }

    //-------------------------------------------------------------
    public void populateFields() {
        String accountID = (String) ((IModel) myModel.getState("Account")).getState("AccountNumber");
        accountNumber.setText(accountID);

        todaysDateAndTime.setText(todaysDateAndTimeString);

        String currentBalanceString = (String) myModel.getState("BalanceAmount");
        double currentBalanceVal = Double.parseDouble(currentBalanceString);

        DecimalFormat df2 = new DecimalFormat("0.00");
        currentBalance.setText("$ " + df2.format(currentBalanceVal));

    }


    /**
     * Required by interface, but has no role here
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value) {

    }

}


