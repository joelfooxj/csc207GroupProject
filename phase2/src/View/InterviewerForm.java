package View;

import Control.InterviewerCommandHandler;
import Control.Queries.JobApplicationQuery;
import Model.JobApplicationDatabase.jobAppFilterKeys;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;

class InterviewerForm extends JDialog {
    private JPanel contentPane;
    private JButton recommendButton;
    private JButton exitButton;
    private JScrollPane appScroll;
    private JTextArea applicationText;
    private JList jobApplicationList;
    private JLabel errorLabel;
    private JButton rejectButton;
    private JButton seeCVButton;
    private JButton seeCoverLetterButton;
    private JButton seeReferenceLettersButton;
    private InterviewerCommandHandler iCH;

    InterviewerForm(InterviewerCommandHandler commandHandler) {
        this.iCH = commandHandler;
        setContentPane(contentPane);
        setModal(true);
        this.applicationText.setEditable(false);

        this.contentPane.setBorder(BorderFactory.createTitledBorder(iCH.getUsername()));
        this.updateForm();

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

        seeCVButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCVButton();
            }
        });

        seeCoverLetterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCoverLetterButton();
            }
        });

        seeReferenceLettersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRefLettersButton();
            }
        });

        jobApplicationList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedAppID = (String) jobApplicationList.getSelectedValue();
                if (selectedAppID != null) {
                    checkCVCLRefButtonEnable(selectedAppID);
                   rejectButton.setEnabled(true);
                   recommendButton.setEnabled(true);
                }

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

    private void setRecommendedApps() {
        for (Object value : this.jobApplicationList.getSelectedValuesList()) {
            String selectedString = (String) value;
            iCH.recommendApplication(Long.parseLong(selectedString));
        }
        updateForm();
    }

    private void setRejectedApps() {
        for (Object value : this.jobApplicationList.getSelectedValuesList()) {
            String selectedString = (String) value;
            iCH.rejectApplication(Long.parseLong(selectedString));
        }
        updateForm();
    }

    private HashMap<jobAppFilterKeys, Object> filterHM(String applicationID) {
        HashMap<jobAppFilterKeys, Object> newfilter = new HashMap<jobAppFilterKeys, Object>() {
            {
                put(jobAppFilterKeys.APPLICATION_ID, Long.parseLong(applicationID));
                put(jobAppFilterKeys.OPEN, Boolean.TRUE);
            }
        };
        return newfilter;
    }


    private void checkCVCLRefButtonEnable(String inJobAppID) {
        List<String> requiredDocsList = this.iCH.filter.getJobAppsFilter(filterHM(inJobAppID)).getRequiredDocuments();
        this.seeCVButton.setEnabled(requiredDocsList.contains("CV"));
        this.seeCoverLetterButton.setEnabled(requiredDocsList.contains("Cover Letter"));
        this.seeReferenceLettersButton.setEnabled(requiredDocsList.contains("Reference Letters"));
    }

    private void onCVButton(){
        String selectedAppID = (String) this.jobApplicationList.getSelectedValue();
        String inCV = this.iCH.filter.getJobAppsFilter(filterHM(selectedAppID)).getResume();
        GUI.messageBox("CV", inCV);
    }

    private void onCoverLetterButton(){
        String selectedAppID = (String) this.jobApplicationList.getSelectedValue();
        String inCoverLetter = this.iCH.filter.getJobAppsFilter(filterHM(selectedAppID)).getCoverLetter();
        GUI.messageBox("Cover Letter", inCoverLetter);
    }

    private void onRefLettersButton(){
        String selectedAppID = (String) this.jobApplicationList.getSelectedValue();
        String inRefLetters = this.iCH.filter.getJobAppsFilter(filterHM(selectedAppID)).getResume();
        GUI.messageBox("Reference Letters", inRefLetters);
    }

    private void updateForm() {
        setButtonState(false);
        HashMap<jobAppFilterKeys, Object> filter = new HashMap<>();
        filter.put(jobAppFilterKeys.INTERVIEWER_ID, iCH.getInterviewerID());
        filter.put(jobAppFilterKeys.OPEN, Boolean.TRUE);
        JobApplicationQuery jobApplicationQuery = iCH.filter.getJobAppsFilter(filter);
        List<String> inJobAppList = jobApplicationQuery.getJobAppsID();
        if (inJobAppList.isEmpty()) {
            this.jobApplicationList.setListData(inJobAppList.toArray());
            this.applicationText.setText("You have no application assigned to you.");
        } else {
            this.jobApplicationList.setListData(inJobAppList.toArray());
            this.applicationText.setText(jobApplicationQuery.getPrintout());
        }
    }

    private void setButtonState(boolean enabled) {
        recommendButton.setEnabled(enabled);
        rejectButton.setEnabled(enabled);
        seeCoverLetterButton.setEnabled(enabled);
        seeCVButton.setEnabled(enabled);
        seeReferenceLettersButton.setEnabled(enabled);
    }
}
