package Services;

import dataAccess.DataAccessFacade;
import dataAccess.StorageType;
import dataAccess.DataAccessFacade.Pair;
import model.Periodical;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rajkumar on 7/9/2015.
 */
public class PeriodicalService {
    private static HashMap<Pair<String, String>, Periodical> periodicals;
    public boolean updatePeriodical(Periodical periodical) {
        return updatePeriodical(periodical,true);
    }

    //bReplace:true,update, false,add
    public boolean updatePeriodical(Periodical periodical, Boolean bReplace) {
        HashMap<Pair<String, String>, Periodical> periodMap = readPeriodicalsMap();
        Pair<String, String> periodKey = new Pair(periodical.getTitle(), periodical.getIssueNumber());
        if (bReplace==false) {
            if (periodMap == null) {
                periodMap = new HashMap<Pair<String, String>, Periodical>();
            }
            if (periodMap.containsKey(periodKey)) {
                System.out.println("periodical already exist!");
                return false;
            }
            periodMap.put(periodKey, periodical);
        }
        else {
            if (periodMap == null || periodMap != null && false == periodMap.containsKey(periodKey)) {
                System.out.println("periodical not exist!");
                return false;
            }
            periodMap.replace(periodKey, periodical);
        }
        periodicals = periodMap;
        new DataAccessFacade().saveToStorage(StorageType.PERIODICAL, periodMap);
        System.out.println("save Periodical success!");
        return true;
    }

    public Periodical getPeriodical(String issueNo, String title) {
        HashMap<Pair<String, String>, Periodical> periodMap = readPeriodicalsMap();
        Pair<String, String> periodKey = new Pair(title, issueNo);
        if (periodMap == null || periodMap != null && false == periodMap.containsKey(periodKey)) {
            return null;
        }
        return periodMap.get(periodKey);
    }
    @SuppressWarnings("unchecked")
    public HashMap<Pair<String,String>, Periodical> readPeriodicalsMap() {
        if(periodicals == null) {
            periodicals = (HashMap<Pair<String,String>, Periodical>) new DataAccessFacade().readFromStorage(
                    StorageType.PERIODICAL);
        }
        return periodicals;
    }
    public static void loadPeriodicalsMap(List<Periodical> periodicalList) {
        periodicals = new HashMap<Pair<String, String>, Periodical>();
        periodicalList.forEach(
                p -> periodicals.put(new Pair<String,String>(p.getTitle(), p.getIssueNumber()), p));
        new DataAccessFacade().saveToStorage(StorageType.PERIODICAL, periodicals);
    }

    public boolean saveNewPeriodical(Periodical periodical) {
        return updatePeriodical(periodical,false);
    }
}
