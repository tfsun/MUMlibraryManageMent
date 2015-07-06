package Services;

import model.Book;
import model.Periodical;
import model.Publication;
//import sun.security.action.GetBooleanAction;

public class CopyService {
	static public String getCopyIDbyPublication(Publication publication)
	{
		String copyNo="";
		if (publication.getClass().getName()=="Book") {
			copyNo = "Book" + ((Book)publication).getIsbn() + ((Book)publication).getCurID();
		}
		else if (publication.getClass().getName()=="Per") {
			copyNo = "Book" + ((Periodical)publication).getTitle() + ((Periodical)publication).getIssueNumber() + ((Periodical)publication).getCurID();
		}
		return copyNo;
	}
}
