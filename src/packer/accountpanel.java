package packer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class accountpanel extends JPanel {
    
    private final Runnable onChange;

    private final JLabel urlLabel = new JLabel("URL:");
    private final JLabel nameLabel = new JLabel("Name:");
    private final JLabel passLabel = new JLabel("Password:");
    private final JLabel emailLabel = new JLabel("Email:");
    private final JToggleButton showBtn = new JToggleButton("Show");

    private final JTextField urlField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JPasswordField passField = new JPasswordField();
    private final JTextField emailField = new JTextField();
    
    String ELetter = "E";

    private final JButton urlEdit = new JButton(ELetter);
    private final JButton nameEdit = new JButton(ELetter);
    private final JButton passEdit = new JButton(ELetter);
    private final JButton emailEdit = new JButton(ELetter);
    private final char hiddenEcho = passField.getEchoChar();
    
    public accountpanel(){
        this(null);
    }

    public accountpanel(Runnable onChange) {
        this.onChange = onChange;
        urlField.setFont(new java.awt.Font("Consolas", 0, 12));

        setupEditButton(urlEdit, urlField);
        setupEditButton(nameEdit, nameField);
        setupEditButton(passEdit, passField);
        setupEditButton(emailEdit,emailField);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        
        showBtn.addActionListener(e -> {
            boolean show = showBtn.isSelected();
            passField.setEchoChar(show ? (char) 0: hiddenEcho);
            showBtn.setText(show?"Hide":"Show");
        });

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(urlLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(urlField))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailField))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(passLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passField)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showBtn, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(emailEdit, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(passEdit, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameEdit, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(urlEdit, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(urlLabel)
                    .addComponent(urlField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(urlEdit))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailEdit))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameEdit))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passEdit)
                    .addComponent(showBtn)
                    .addComponent(passField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
        );
    }

    private void setupEditButton(JButton edit, JTextField field) {
        field.setEditable(false);
        edit.addActionListener(e -> {
            boolean editing = !field.isEditable();
            field.setEditable(editing);
            edit.setText(editing ? "OK" : "E");
            if (editing) {
                field.requestFocusInWindow();
            } else if (onChange != null) {
                onChange.run();
            }
        });
    }

    public String getUrl() {
        return urlField.getText();
    }

    public String getAccountName() {
        return nameField.getText();
    }
    
    public String getEmail(){
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passField.getPassword());
    }
    
    public void setData(String url, String user, String password, String email){
        urlField.setText(url);
        nameField.setText(user);
        passField.setText(password);
        emailField.setText(email);
    }
}