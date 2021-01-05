package Presenters;

import java.util.ArrayList;

public interface IFeedbackPresenter {

    String PrintFeedback(String Information);

    ArrayList<String > PrintFeedbackList(ArrayList<String> InfoList);

    void DocExport(ArrayList<String> e);

}
