import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApplicationDatabase extends AbstractDatabase{
    private HashMap<Long, Application> applicationDatabase;

    public ApplicationDatabase() {
        applicationDatabase = new HashMap<>();
    }

    // Form in bracket is (application ID, application object)
    public void createNewApplication(Application application){
        applicationDatabase.put(application.getApplicationID(), application);
    }

    //This ID must be application ID
    public void removeItemByID(long removeID){
        applicationDatabase.remove(removeID);
    }

    //Since each application ID is unique, this returns 1 application object
    public Application getApplicationByApplicationID(long applicationID){
        for(int i = 0; i<applicationDatabase.size();i++){
            Application item = applicationDatabase.get(i);
            if (item.getApplicationID() == applicationID){
                return item;
            }
        }
        return null;
    }

    //Get a list of applications by its applicant ID
    public List<Application> getApplicationByApplicantID(long applicantID){
        List<Application> applicationList = new ArrayList<Application>();
        for(int i = 0; i<applicationDatabase.size();i++){
            Application item = applicationDatabase.get(i);
            if (item.getApplicantID() == applicantID){
                applicationList.add(item);
            }
        }
        return applicationList;
    }

    //Get a list of applications by its Job ID
    public List<Application> getApplicationByJobID(long jobID){
        List<Application> applicationList = new ArrayList<Application>();
        for(int i = 0; i<applicationDatabase.size();i++){
            Application item = applicationDatabase.get(i);
            if (item.getJobID() == jobID){
                applicationList.add(item);
            }
        }
        return applicationList;
    }

    //Get a list of applications by its Firm ID
    public List<Application> getApplicationByFirmID(long firmID){
        List<Application> applicationList = new ArrayList<Application>();
        for(int i = 0; i<applicationDatabase.size();i++){
            Application item = applicationDatabase.get(i);
            if (item.getFirmID() == firmID){
                applicationList.add(item);
            }
        }
        return applicationList;
    }

    public void printApplicationsByApplicantID(String username, long firmId){
        // applicant ids are the username
    }

    //The 3 print methods
    //print a String which adds all the toString of applications with that applicant ID
    public void printApplicationsByApplicantID(long applicantID){
        StringBuilder allApplication = new StringBuilder();
        for (int i = 0;i<applicationDatabase.size();i++){
            Application item = applicationDatabase.get(i);
            if (item.getApplicantID() == applicantID)
            allApplication.append(item.toString()+";  ");
        }
        System.out.println(allApplication);
    }

    //print a String …… job ID
    public void printApplicationsByJobID(long jobID){
        StringBuilder allApplication = new StringBuilder();
        for (int i = 0;i < applicationDatabase.size();i++){
            Application item = applicationDatabase.get(i);
            if (item.getJobID() == jobID)
                allApplication.append(item.toString());
        }
        System.out.println(allApplication);
    }

    //print a String …… firmID
    public void printApplicationsByFirmID(long firmID){
        StringBuilder allApplication = new StringBuilder();
        for (int i = 0;i<applicationDatabase.size();i++){
            Application item = applicationDatabase.get(i);
            if (item.getFirmID() == firmID)
                allApplication.append(item.toString());
        }
        System.out.println(allApplication);
    }

    public void printOpenApplicationsByJobID(long jobId) {
        // prints applications that are not closed (filled/expired)
        // this will be called when the HR wants to set up an interview
    }
}
