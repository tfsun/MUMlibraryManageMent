package Services;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.LibraryMember;
import dataAccess.DataAccessFacade;
import dataAccess.StorageType;

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
	
    public boolean addUser(LibraryMember libraryMember){
        
        Map<String,LibraryMember>  members=getUserMap();
        members.put(String.valueOf(libraryMember.getMemberId()), libraryMember);
        DataAccessFacade.saveToStorage(StorageType.MEMBERS, members);
		return true;

    }
    
    public HashMap<String, LibraryMember> getUserMap() {
		members = (HashMap<String, LibraryMember>) DataAccessFacade.readFromStorage(StorageType.MEMBERS);
		if(members==null){
			members=new HashMap<>();
		}
		return members;
	}

	public static void loadMemberMap(List<LibraryMember> memberList) {
		members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(String.valueOf(member.getMemberId()), member));
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
