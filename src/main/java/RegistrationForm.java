import utils.HTTPRequests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegistrationForm extends JDialog{
    private JTextField tffirstName;
    private JTextField tflastName;
    private JTextField tfemail;
    private JPasswordField tfpassword;
    private JButton registerButton;
    private JPanel RegistrationForm;

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Create a new Account");
        setContentPane(RegistrationForm);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String firstName = tffirstName.getText();
        String lastName = tflastName.getText();
        String email = tfemail.getText();
        String password = String.valueOf(tfpassword.getPassword());

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again!",
                    JOptionPane.ERROR_MESSAGE
                    );
            return;
        }
        registerUser(firstName, lastName, email, password);
    }

    public User user;
    private void registerUser(String firstName, String lastName, String email, String password) {
//        User user = null;
//        final String DB_URL = "";
//        final String USERNAME = "root";
//        final String PASSWORD = "RCA*cij1";

        try{
//            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//            String sql = "INSERT INTO users(firstname, lastname, email, password)" + "VALUES(?, ?, ?, ?)";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, firstName);
//            preparedStatement.setString(2, lastName);
//            preparedStatement.setString(3, email);
//            preparedStatement.setString(4, password);
//
//            int addedRows = preparedStatement.executeUpdate();
//            if(addedRows > 0){
//                user = new User();
//                user.firstName = firstName;
//                user.lastName = lastName;
//                user.email = email;
//                user.password = password;
//            }
            HTTPRequests httpRequests = new HTTPRequests();

            String apiUrl = "http://localhost:8081/api/v1/auth/register";
            String requestBody = null;
            Map<String, String> requestData = new HashMap<>();
            requestData.put("firstname", firstName);
            requestData.put("lastname", lastName);
            requestData.put("email", email);
            requestData.put("password", password);

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                requestBody = objectMapper.writeValueAsString(requestData);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            String contentType = "application/json";

            HttpResponse<String> response = httpRequests.sendPostRequest(apiUrl, requestBody, contentType);
            if (response != null) {
                int statusCode = response.statusCode();
                String responseBody = response.body();
                System.out.println("Status code: " + statusCode);
                System.out.println("Response body: " + responseBody);
            } else {
                System.out.println("An error occurred while sending the POST request.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RegistrationForm form = new RegistrationForm(null);
        User user = form.user;
        if(user!=null){
            System.out.println("Successful registration of: "+user.firstName);
        }else{
            System.out.println("Registration Canceled");
        }
    }
}
