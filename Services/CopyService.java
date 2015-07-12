package Services;

import dataAccess.DataAccessFacade;
import dataAccess.StorageType;
import model.Book;
import model.LendableCopy;
import model.Periodical;
import model.Publication;

import java.util.HashMap;
import java.util.List;

public class CopyService {

	private static HashMap<String, LendableCopy> copys;
	static public String getCopyIDbyPublication(Publication publication)
	{
		String copyNo="";
		if (publication.getClass().getSimpleName().equals("Book")) {
			copyNo = "Book_" + ((Book)publication).getIsbn() + "_" + ((Book)publication).getCurID();
		}
		else if (publication.getClass().getSimpleName().equals("Periodical")) {
			copyNo = "Periodical_" + ((Periodical)publication).getTitle() + "_" + ((Periodical)publication).getIssueNumber() + "_" + ((Periodical)publication).getCurID();
		}
		return copyNo;
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, LendableCopy> readCopyMap() {
		if(copys == null) {
			copys = (HashMap<String, LendableCopy>) new DataAccessFacade().readFromStorage(
					StorageType.COPY);
		}
		return copys;
	}

	private static void loadCopysMap(List<LendableCopy> copyList) {
		copys = new HashMap<String, LendableCopy>();
		copyList.forEach(copy -> copys.put(copy.getCopyNo(), copy));
		new DataAccessFacade().saveToStorage(StorageType.COPY, copys);
	}
}
