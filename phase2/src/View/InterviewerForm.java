package View;

import Control.InterviewerCommandHandler;

import javax.swing.*;
import java.awt.event.*;

class InterviewerForm extends JDialog {
    private JPanel contentPane;
    private JButton recommendButton;
    private JButton exitButton;
    private JTextArea applicationText;
    private JList jobApplicationList;
    private JLabel errorLabel;
    private JButton rejectButton;
    private InterviewerCommandHandler ch;

    InterviewerForm(InterviewerCommandHandler commandHandler) {
        this.ch = commandHandler;
        setContentPane(contentPane);
        setModal(true);

        this.contentPane.setBorder(BorderFactory.createTitledBorder(ch.getUsername()));
        this.applicationText.setText(ch.getAssignedApplicationsRepresentation());
        this.jobApplicationList.setListData(ch.getAssignedApplicationsIds().toArray());

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        recommendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setRecommendedApps();
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setRejectedApps();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void setRecommendedApps(){
        for (Object value:this.jobApplicationList.getSelectedValuesList()){
            String selectedString = (String) value;
            ch.recommendApplication(Long.parseLong(selectedString));
        }
    }

    private void setRejectedApps(){
        for (Object value:this.jobApplicationList.getSelectedValuesList()){
            String selectedString = (String) value;
            ch.rejectApplication(Long.parseLong(selectedString));
        }
    }
}
