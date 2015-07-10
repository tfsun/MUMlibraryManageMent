package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.LibraryMember;
import dataAccess.DataAccessFacade;
import dataAccess.StorageType;

/**
 * Created by rajkumar on 6/30/2015.
 */
public class UserService {

	private static HashMap<Integer, LibraryMember> members;
	
	////specialized lookup methods
	public LibraryMember searchMember(int memberId) {
		HashMap<Integer, LibraryMember> mems = getUserMap();
		if(mems.containsKey(memberId)) {

			return mems.get(memberId);
		}


		return null;
	}
	
    public boolean addUser(LibraryMember libraryMember){
        
        Map<Integer,LibraryMember>  members=getUserMap();
        members.put(libraryMember.getMemberId(), libraryMember);
        DataAccessFacade.saveToStorage(StorageType.MEMBERS, members);
		return true;

    }
    
    public HashMap<Integer, LibraryMember> getUserMap() {
		members = (HashMap<Integer, LibraryMember>) DataAccessFacade.readFromStorage(StorageType.MEMBERS);
		if(members==null){
			members=new HashMap<>();
		}
		return members;
	}

	public static void loadMemberMap(List<LibraryMember> memberList) {
		members = new HashMap<Integer, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		new DataAccessFacade().saveToStorage(StorageType.MEMBERS, members);
	}

	public List<LibraryMember> getAllMembers(){
		List<LibraryMember> libraryMembers=new ArrayList<>();
		members=getUserMap();
		for(LibraryMember member:members.values()){
			libraryMembers.add(member);
		}
		return libraryMembers;
	}

}
