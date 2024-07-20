import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ATM Machine");

        // Create a bank account with an initial balance
        BankAccount account = new BankAccount(1000.00);

        // Create the ATM
        ATM atm = new ATM(account);

        // Create the UI
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(12);

        // Balance label
        Label balanceLabel = new Label("Balance: $" + atm.checkBalance());
        GridPane.setConstraints(balanceLabel, 0, 0);

        // Deposit
        Label depositLabel = new Label("Deposit Amount:");
        GridPane.setConstraints(depositLabel, 0, 1);
        TextField depositInput = new TextField();
        GridPane.setConstraints(depositInput, 1, 1);
        Button depositButton = new Button("Deposit");
        GridPane.setConstraints(depositButton, 2, 1);

        depositButton.setOnAction(e -> {
            double amount = Double.parseDouble(depositInput.getText());
            atm.deposit(amount);
            balanceLabel.setText("Balance: $" + atm.checkBalance());
            showAlert("Deposit Successful", "Deposited $" + amount + " successfully.");
            depositInput.clear();
        });

        // Withdraw
        Label withdrawLabel = new Label("Withdraw Amount:");
        GridPane.setConstraints(withdrawLabel, 0, 2);
        TextField withdrawInput = new TextField();
        GridPane.setConstraints(withdrawInput, 1, 2);
        Button withdrawButton = new Button("Withdraw");
        GridPane.setConstraints(withdrawButton, 2, 2);

        withdrawButton.setOnAction(e -> {
            double amount = Double.parseDouble(withdrawInput.getText());
            if (atm.withdraw(amount)) {
                balanceLabel.setText("Balance: $" + atm.checkBalance());
                showAlert("Withdrawal Successful", "Withdrew $" + amount + " successfully.");
            } else {
                showAlert("Withdrawal Failed", "Insufficient balance for withdrawal.");
            }
            withdrawInput.clear();
        });

        // Add components to the grid
        grid.getChildren().addAll(balanceLabel, depositLabel, depositInput, depositButton, withdrawLabel, withdrawInput, withdrawButton);

        // Create the scene
        Scene scene = new Scene(grid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public boolean withdraw(double amount) {
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            return true;
        } else {
            return false;
        }
    }

    public double checkBalance() {
        return account.getBalance();
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}
