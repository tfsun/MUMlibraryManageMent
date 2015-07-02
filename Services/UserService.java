package Services;

import java.util.HashMap;
import java.util.Map;

import dataAccess.DataAccessFacade;
import dataAccess.StorageType;
import model.LibraryMember;

/**
 * Created by rajkumar on 6/30/2015.
 */
public class UserService {

	private static HashMap<String, LibraryMember> members;
	
	////specialized lookup methods
	public LibraryMember searchMember(String memberId) {
		HashMap<String, LibraryMember> mems = getUserMap();
		if(mems.containsKey(memberId)) {
			return mems.get(memberId);
		}
		return null;
	}
	
    public void addUser(LibraryMember libraryMember){
        
        Map<String,LibraryMember>  members=getUserMap();
        members.put(libraryMember.getMemberId(), libraryMember);
        //DataAccessFacade.saveToStorage(StorageType.MEMBERS, members);
        
    }
    
    private HashMap<String, LibraryMember> getUserMap() {
		members = (HashMap<String, LibraryMember>) DataAccessFacade.readFromStorage(StorageType.MEMBERS);
		if(members==null){
			members=new HashMap<>();
		}
		return members;
	}

}
